package evogen;

import java.util.*;

abstract public class AbstractWorldMap {
    protected final Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    private final int width;
    private final int height;
    private final int startEnergy;
    private final int moveEnergy;
    private final int plantEnergy;
    private final int jungleWidth;
    private final int jungleHeight;
    private final Vector2d jungleCorner;  // lower left jungle corner, inclusive

    public AbstractWorldMap(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, int jungleRatio,
                            int initialAnimals) {
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleWidth = this.width * jungleRatio;
        this.jungleHeight = this.height * jungleRatio;
        this.jungleCorner = new Vector2d(((this.width - this.jungleWidth)/2) + 1, ((this.height - this.jungleWidth)/2) + 1);

        initializeMap(initialAnimals);
    }

    private void initializeMap(int initialAnimals) {
        // creates map with 2 plants and initialAnimals
        // animals can be created one on top of  another
        // plants cannot be created on top of animals

        for (int i = 0; i < initialAnimals; i++) {
            Vector2d rndVector = Vector2d.getRandomVector(this.width, this.height);
            Genotype rndGenotype = Genotype.generateRandomGenotype();
            Animal rndAnimal = new Animal(rndVector, startEnergy, rndGenotype);
            placeAnimal(rndAnimal);
        }
    }

    public List<Animal> getStrongestAnimalsAt(Vector2d position) {
        // returns list with strongest (energywise) animals on the field (multiple animals with the same energy value)

        List<Animal> animalsOnPos = this.animals.get(position);

        List<Animal> strAnimals = new LinkedList<>();
        strAnimals.add(animalsOnPos.get(0));
        for (int i = 1; i < animalsOnPos.size(); i++) {
            if (animalsOnPos.get(i).getEnergy() > strAnimals.get(0).getEnergy()) {
                strAnimals.clear();
                strAnimals.add(animalsOnPos.get(i));
            }
            else if (animalsOnPos.get(i).getEnergy() == strAnimals.get(0).getEnergy()) {
                strAnimals.add(animalsOnPos.get(i));
            }
        }

        return strAnimals;
    }

    public Animal[] getTwoStrongestAnimalsAt(Vector2d position) {
        // returns two strongest (energywise) animals on the field, for reproduction

        List<Animal> animalsOnPos = this.animals.get(position);

        Animal fstAnimal = animalsOnPos.get(0);
        Animal sndAnimal = animalsOnPos.get(1);

        for (int i = 2; i < animalsOnPos.size(); i++) {
            if (animalsOnPos.get(i).getEnergy() >= fstAnimal.getEnergy()) {
                sndAnimal = fstAnimal;
                fstAnimal = animalsOnPos.get(i);
            }
            else if (animalsOnPos.get(i).getEnergy() > sndAnimal.getEnergy()) {
                sndAnimal = animalsOnPos.get(i);
            }
        }
        return new Animal[]{fstAnimal, sndAnimal};
    }

    public boolean isOccupiedByPlant(Vector2d position) {
        return this.plants.containsKey(position);
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return this.animals.containsKey(position);
    }

    public boolean isOccupied(Vector2d position) {
        return isOccupiedByAnimal(position) || isOccupiedByPlant(position);
    }

    public void placeAnimal(Animal animal) {
        // places animal on the map

        Vector2d animalPosition = animal.getPosition();
        if (!isOccupiedByAnimal(animalPosition)) {
            this.animals.put(animalPosition, new LinkedList<>());
        }
        this.animals.get(animalPosition).add(animal);
    }

    public void placePlant(Vector2d position) {
        // places plant on the map, based on position because all the plants are the same

        Plant newPlant = new Plant(position, this.plantEnergy);
        this.plants.put(position, newPlant);
    }

    abstract Vector2d getMovePosition();
    // implementation depends on the map type
}
