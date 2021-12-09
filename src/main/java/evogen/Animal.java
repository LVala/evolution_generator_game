package evogen;

import java.util.Arrays;
import java.util.Random;

public class Animal {
    private Vector2d position;
    private MapDirection orientation = MapDirection.getRandomDirection();
    private int energy;
    private final Genotype genotype;

    public Animal(Vector2d position, int energy, Genotype genotype) {
        this.position = position;
        this.energy = energy;
        this.genotype = genotype;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public String toString() {
        return String.format("Position: %s\nOrientation: %s\n" +
                "Energy: %d\nGenotype: %s\n", this.position, this.orientation, this.energy, this.genotype);
    }

    public void eatPlant(Plant plant, int splitBetween) {
        this.energy += (int) (plant.getPlantEnergy()/splitBetween);
    }

    public Animal reproduce(Animal other) {
        // creates new animal based on parents energy and genotypes

        boolean biggerTakesLeft = new Random().nextBoolean();

        Animal bigger, smaller;
        if (this.energy >= other.energy) {
            bigger = this;
            smaller = other;
        }
        else {
            bigger = other;
            smaller = this;
        }

        int[] leftGenes, rightGenes;
        double splitAt;
        if (biggerTakesLeft) {
            splitAt = (double) bigger.energy/(bigger.energy + smaller.energy);
            leftGenes = bigger.genotype.getPartOfGenotype(splitAt, true);
            rightGenes = smaller.genotype.getPartOfGenotype(splitAt, false);
        }
        else {
            splitAt = (double) smaller.energy/(bigger.energy + smaller.energy);
            leftGenes = smaller.genotype.getPartOfGenotype(splitAt, true);
            rightGenes = bigger.genotype.getPartOfGenotype(splitAt, false);
        }

        int[] newGenes = Arrays.copyOf(leftGenes, leftGenes.length + rightGenes.length);
        System.arraycopy(rightGenes, 0, newGenes, leftGenes.length, rightGenes.length);
        Genotype newGenotype = new Genotype(newGenes);

        int newEnergy = (int) (0.25 * this.energy) + (int) (0.25 * other.energy);
        this.energy -= (int) (0.25 * this.energy);
        other.energy -= (int) (0.25 * other.energy);

        return new Animal(this.position, newEnergy, newGenotype);
    }
}