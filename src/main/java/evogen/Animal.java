package evogen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal {
    private Vector2d position;
    private MapDirection orientation = MapDirection.getRandomDirection();
    private int energy;
    private final Genotype genotype;
    private final AbstractWorldMap map;
    protected int deathEra;

    private final List<IMapActionObserver> observers = new ArrayList<>();

    // CONSTRUCTOR

    public Animal(Vector2d position, int energy, Genotype genotype, AbstractWorldMap map) {
        this.position = position;
        this.energy = energy;
        this.genotype = genotype;
        this.map = map;

        this.addObserver(map);
    }

    // GETTERS AND SETTERS

    public void setDeathEra(int deathEra) {
        this.deathEra = deathEra;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }

    // OBSERVER

    void addObserver(IMapActionObserver observer) {
        this.observers.add(observer);
    }

    void plantEaten(Vector2d position) {
        for (IMapActionObserver observer : this.observers) {
            observer.plantEaten(position);
        }
    }

    public void animalPositionChanged(Animal animal, Vector2d oldPosition) {
        for (IMapActionObserver observer : this.observers) {
            observer.animalPositionChanged(animal, oldPosition);
        }
    }

    // TO STRING

    public String toString() {
        return String.format("|Position: %s, Orientation: %s, " +
                "Energy: %d|\n", this.position, this.orientation, this.energy);
    }

    // MAP ACTION METHODS

    public void eatPlant(Vector2d position, int plantEnergy, int splitBetween) {
        this.energy += (plantEnergy/splitBetween);
        plantEaten(position);
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

        return new Animal(this.position, newEnergy, newGenotype, this.map);
    }

    public void move() {
        // responsible for moving animal on the map

        Vector2d oldPosition = this.getPosition();
        int rndGene = this.genotype.getRandomGene();
        this.orientation = this.orientation.next(rndGene);

        if (rndGene == 0 || rndGene == 4) {
            this.position = this.map.getMovePosition(this.getPosition(), this.orientation);
            animalPositionChanged(this, oldPosition);
        }

    }
}