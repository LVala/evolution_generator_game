package evogen;

import gui.SimulationBox;

import java.util.*;

public class SimulationEngine implements IEngine{

    private final AbstractWorldMap map;
    private SimulationBox simulationGuiBox;
    private int era = 0;
    private final boolean ifMagic;
    private int magicClonings = 3;

    public final List<Integer> animals = new ArrayList<>();
    public final List<Integer> plants = new ArrayList<>();
    public final List<Integer> energy = new ArrayList<>();
    public final List<Integer> lifespan = new ArrayList<>();
    public final List<Integer> children = new ArrayList<>();

    public SimulationEngine(AbstractWorldMap map, boolean ifMagic) {
        this.map = map;
        this.ifMagic = ifMagic;

        collectStats();
    }

    public void setSimulationGuiBox(SimulationBox guiBox) {
        this.simulationGuiBox = guiBox;
    }

    public AbstractWorldMap getMap() {
        return this.map;
    }

    public int getEra() {
        return this.era;
    }

    private void removeDeadAnimals() {
        List<Animal> toRemove = new ArrayList<>();  // to prevent ConcurrentModificationException

        for (List<Animal> field : this.map.getAnimals().values()) {
            for (Animal animal : field) {
                if (animal.getEnergy() <= 0) {
                    toRemove.add(animal);
                }
            }
        }

        for (Animal animal : toRemove) {
            map.removeDeadAnimal(animal, this.era);
            if (this.ifMagic && this.magicClonings > 0 && this.map.getAnimalNumber() <= 5) magicallyCloneAnimals();
        }
    }

    private void moveAnimals() {
        List<Animal> toMove = new ArrayList<>();  // to prevent ConcurrentModificationException

        for (List<Animal> field : this.map.getAnimals().values()) {
            toMove.addAll(field);
        }

        for (Animal animal : toMove) animal.move(map.moveEnergy);
    }

    private void eatPlants() {
        for (Map.Entry<Vector2d, List<Animal>> entry : this.map.getAnimals().entrySet()) {
            if (map.isOccupiedByPlant(entry.getKey())) {
                List<Animal> strongest = map.getStrongestAnimalsAt(entry.getKey());
                for (Animal animal : strongest) {
                    animal.eatPlant(entry.getKey(), map.plantEnergy, strongest.size());
                }
            }
        }

    }

    private void reproduceAnimals() {
        for (Map.Entry<Vector2d, List<Animal>> entry : this.map.getAnimals().entrySet()) {
            if (map.isOccupiedByAnimal(entry.getKey(), 2)) {
                Animal[] twoStrongest = map.getTwoStrongestAnimalsAt(entry.getKey());
                if (twoStrongest[0].getEnergy() >= 0.5 * map.startEnergy && twoStrongest[1].getEnergy() >= 0.5 * map.startEnergy) {
                    Animal child = twoStrongest[0].reproduce(twoStrongest[1], era);
                    map.placeAnimal(child, true);
                }
            }
        }
    }

    private void collectStats() {
        this.animals.add(this.map.animalNumber);
        this.plants.add(this.map.plantNumber);
        this.energy.add(this.map.getAverageEnergy());
        this.lifespan.add(this.map.getAverageLifespan());
        this.children.add(this.map.getAverageChildrenNumber());
    }

    private void magicallyCloneAnimals() {
        List<Animal> toAdd = new ArrayList<>();

        for (List<Animal> field : this.map.getAnimals().values()) {
            for (Animal animal : field) {
                Vector2d newPosition = Vector2d.getRandomVector(this.map.width, this.map.height);
                // losuję tutaj w nieskończoność, bo albo jest dużo wolnych miejsc(bo 5 zwierzat) albo mała mapa
                while (this.map.isOccupied(newPosition)) newPosition = Vector2d.getRandomVector(this.map.width, this.map.height);
                Animal newAnimal = new Animal(newPosition, this.map.startEnergy, animal.getGenotype(), this.map, this.era);
                toAdd.add(newAnimal);
            }
        }
        for (Animal animal : toAdd) map.placeAnimal(animal, true);
        this.magicClonings--;
    }

    public void run() {
        //TODO to na brudno
        while (era < 2 && map.getAnimalNumber() > 0) {
            era++;
            removeDeadAnimals();
            moveAnimals();
            eatPlants();
            reproduceAnimals();
            map.placePlants();
            collectStats();
            System.out.println(this.map.genotypeCounter);
            //TODO inform gui that map changed
        }

    }
}
