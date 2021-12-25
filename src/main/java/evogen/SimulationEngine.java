package evogen;

import gui.SimulationBox;
import javafx.application.Platform;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable{

    private final AbstractWorldMap map;
    private SimulationBox simulationGuiBox;
    private final int delay;
    private int era = 0;
    private final boolean ifMagic;
    private int magicClonings = 3;

    public final List<Integer> animals = new LinkedList<>();  // stats tracking lists
    public final List<Integer> plants = new LinkedList<>();
    public final List<Integer> energy = new LinkedList<>();
    public final List<Integer> lifespan = new LinkedList<>();
    public final List<Integer> children = new LinkedList<>();

    public SimulationEngine(AbstractWorldMap map, boolean ifMagic, int delay) {
        this.map = map;
        this.ifMagic = ifMagic;
        this.delay = delay;

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

    public int getDelay() {
        return this.delay;
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
        this.animals.add(this.map.getAnimalNumber());
        this.plants.add(this.map.getPlantNumber());
        this.energy.add(this.map.getAverageEnergy());
        this.lifespan.add(this.map.getAverageLifespan());
        this.children.add(this.map.getAverageChildrenNumber());
    }

    private void magicallyCloneAnimals() {
        List<Animal> toAdd = new ArrayList<>();  // to prevent ConcurrentModificationException

        for (List<Animal> field : this.map.getAnimals().values()) {
            for (Animal animal : field) {
                Vector2d newPosition = Vector2d.getRandomVector(this.map.width, this.map.height);

                // potentially infinite loop, but happens when animals number << map area, so should find free field
                while (this.map.isOccupied(newPosition)) newPosition = Vector2d.getRandomVector(this.map.width, this.map.height);
                Animal newAnimal = new Animal(newPosition, this.map.startEnergy, animal.getGenotype(), this.map, this.era);
                toAdd.add(newAnimal);
            }
        }
        for (Animal animal : toAdd) map.placeAnimal(animal, true);
        this.magicClonings--;
        Platform.runLater(() -> simulationGuiBox.magicSimPopup(this.magicClonings));  // call to show popup in GUI
    }

    public void run() {
        era++;
        removeDeadAnimals();
        moveAnimals();
        eatPlants();
        reproduceAnimals();
        map.placePlants();
        collectStats();
        Platform.runLater(() -> simulationGuiBox.reloadSimulationBox());  // call to reload GUI
    }
}
