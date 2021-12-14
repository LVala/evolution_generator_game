package evogen;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NormalSimulationEngine implements IEngine{

    private final FoldedMap foldedMap;
    private final BoundedMap boundedMap;
    private int era;

    public NormalSimulationEngine(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int initialAnimals) {
        this.foldedMap = new FoldedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);
        this.boundedMap = new BoundedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);
    }

    public void removeDeadAnimals(AbstractWorldMap map) {
        // removes dead animals from the map
        //TODO nietestowane (nic w tej klasie wlasciwie)
        Map<Vector2d, LinkedList<Animal>> animals = map.getAnimals();

        for (LinkedList<Animal> field : animals.values()) {
            for (Animal animal : field) {
                if (animal.getEnergy() <= 0) {
                    map.removeDeadAnimal(animal, era);
                }
            }
        }
    }

    public void moveAnimals(AbstractWorldMap map) {
        Map<Vector2d, LinkedList<Animal>> animals = map.getAnimals();

        for (LinkedList<Animal> field : animals.values()) {
            for (Animal animal : field) {
                animal.move();
            }
        }
    }

    public void eatPlants(AbstractWorldMap map) {
        Map<Vector2d, LinkedList<Animal>> animals = map.getAnimals();

        for (Map.Entry<Vector2d, LinkedList<Animal>> entry : animals.entrySet()) {
            if (map.isOccupiedByPlant(entry.getKey())) {
                List<Animal> strongest = map.getStrongestAnimalsAt(entry.getKey());
                for (Animal animal : strongest) {
                    animal.eatPlant(entry.getKey(), map.plantEnergy, strongest.size());
                }
            }
        }

    }

    public void reproduceAnimals(AbstractWorldMap map) {
        Map<Vector2d, LinkedList<Animal>> animals = map.getAnimals();

        for (Map.Entry<Vector2d, LinkedList<Animal>> entry : animals.entrySet()) {
            if (entry.getValue().size() >= 2) {
                Animal[] twoStrongest = map.getTwoStrongestAnimalsAt(entry.getKey());
                Animal child = twoStrongest[0].reproduce(twoStrongest[1]);
                map.placeAnimal(child);
            }
        }
    }

    public void run() {
        //TODO to na brudno

        while (true) {
            removeDeadAnimals(foldedMap);
            moveAnimals(foldedMap);
            eatPlants(foldedMap);
            reproduceAnimals(foldedMap);
            foldedMap.placePlants();
            //TODO inform gui that map changed
        }

    }
}
