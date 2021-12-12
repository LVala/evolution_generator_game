package evogen;

import java.util.*;

abstract public class AbstractWorldMap implements IMapActionObserver {
    protected final Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final List<Animal> deadAnimals = new ArrayList<>();

    protected final int width;
    protected final int height;

    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;

    protected final int jungleWidth;
    protected final int jungleHeight;
    protected final Vector2d jungleCorner;  // lower left jungle corner, inclusive

    protected int steppePlantNumber;
    protected int junglePlantNumber;

    protected final int maxSteppePlantNumber;
    protected final int maxJunglePlantNumber;

    // CONSTRUCTOR

    public AbstractWorldMap(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio,
                            int initialAnimals) {
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleWidth = (int) Math.round(this.width * jungleRatio);
        this.jungleHeight = (int) Math.round(this.height * jungleRatio);
        this.jungleCorner = new Vector2d(((this.width - this.jungleWidth)/2) + 1, ((this.height - this.jungleWidth)/2) + 1);

        this.steppePlantNumber = 0;
        this.junglePlantNumber = 0;

        this.maxJunglePlantNumber = jungleWidth * jungleHeight;
        this.maxSteppePlantNumber = width * height - this.maxJunglePlantNumber;

        initializeMap(initialAnimals);
    }

    private void initializeMap(int initialAnimals) {
        // creates map with 2 plants and initialAnimals
        // animals can be created one on top of  another
        // plants cannot be created on top of animals

        int i = 0;
        while (i < initialAnimals) {
            Vector2d rndVector = Vector2d.getRandomVector(this.width, this.height);
            if (this.isOccupiedByAnimal(rndVector)) continue;

            Genotype rndGenotype = Genotype.generateRandomGenotype();
            Animal rndAnimal = new Animal(rndVector, startEnergy, rndGenotype, this);
            placeAnimal(rndAnimal);
            i++;
        }
        placePlants();
    }

    // GETTERS AND SETTERS

    public Map<Vector2d, LinkedList<Animal>> getAnimals() {
        return this.animals;
    }

    public Plant getPlantAt(Vector2d position) {
        return this.plants.get(position);
    }

    abstract Vector2d getMovePosition(Vector2d animalPosition, MapDirection direction);
    //implementation depends on the map type

    public List<Animal> getStrongestAnimalsAt(Vector2d position) {
        // returns list with strongest (energywise) animals on the field (multiple animals with the same energy value)

        if (!this.isOccupiedByAnimal(position)) throw new IllegalArgumentException("No animals on this field");

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

        if (this.animals.get(position).size() < 2) throw new IllegalArgumentException("Less than 2 animals on this field");

        List<Animal> animalsOnPos = this.animals.get(position);

        Animal fstAnimal;
        Animal sndAnimal;
        if (animalsOnPos.get(0).getEnergy() > animalsOnPos.get(1).getEnergy()) {
            fstAnimal = animalsOnPos.get(0);
            sndAnimal = animalsOnPos.get(1);
        }
        else {
            fstAnimal = animalsOnPos.get(1);
            sndAnimal = animalsOnPos.get(0);
        }

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

    // IS OCCUPIED

    public boolean isOccupiedByPlant(Vector2d position) {
        return this.plants.containsKey(position);
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return this.animals.containsKey(position);
    }

    public boolean isOccupied(Vector2d position) {
        return isOccupiedByAnimal(position) || isOccupiedByPlant(position);
    }

    // PLACE AND REMOVE OBJECT ON MAP METHODS

    public void placeAnimal(Animal animal) {
        // places animal on the map

        Vector2d animalPosition = animal.getPosition();
        if (!isOccupiedByAnimal(animalPosition)) {
            this.animals.put(animalPosition, new LinkedList<>());
        }
        this.animals.get(animalPosition).add(animal);
    }

    public void placePlants() {
        // places plants on the map, based on random position because all the plants are the same
        // one plant in the jungle, one outside
        // cant place plant on animal or on another plant

        if (this.junglePlantNumber < this.maxJunglePlantNumber) {
            Vector2d rndPosition1 = Vector2d.getRandomVector(jungleWidth, jungleHeight).add(this.jungleCorner);
            while (this.isOccupied(rndPosition1)) {
                rndPosition1= Vector2d.getRandomVector(jungleWidth, jungleHeight).add(this.jungleCorner);
            }
            Plant newPlant = new Plant(rndPosition1, this.plantEnergy);
            this.plants.put(rndPosition1, newPlant);
            this.junglePlantNumber += 1;
        }

        if (this.steppePlantNumber < this.maxSteppePlantNumber) {
            Vector2d rndPosition2 = Vector2d.getRandomVector(width, height);
            while (this.isOccupied(rndPosition2) ||
                    rndPosition2.isInside(this.jungleCorner.x, this.jungleCorner.x + this.jungleWidth,
                            this.jungleCorner.y, this.jungleCorner.y + this.jungleHeight)) {
                rndPosition2 = Vector2d.getRandomVector(jungleWidth, jungleHeight).add(this.jungleCorner);
            }
            Plant newPlant = new Plant(rndPosition2, this.plantEnergy);
            this.plants.put(rndPosition2, newPlant);
            this.steppePlantNumber += 1;
        }
    }

    public void removeDeadAnimal(Animal animal, int deathEra) {
        this.animals.get(animal.getPosition()).remove(animal);
        if (this.animals.get(animal.getPosition()).isEmpty()) this.animals.remove(animal.getPosition());
        animal.setDeathEra(deathEra);
        this.deadAnimals.add(animal);

    }

    // OBSERVER

    public void animalPositionChanged(Animal animal, Vector2d oldPosition) {
        this.animals.get(oldPosition).remove(animal);
        if (this.animals.get(oldPosition).isEmpty()) this.animals.remove(oldPosition);
        this.placeAnimal(animal);
    }

    public void plantEaten(Vector2d position) {
        if (plants.containsKey(position)) {
        this.plants.remove(position);
        }
    }

    // TO STRING

    public String toString() {
        return String.format("""
                        MAP ########
                        MAP WIDTH: %d
                        MAP HEIGHT: %d
                        MAP START ENERGY: %d
                        MAP PLANT ENERGY: %d
                        MAP JUNGLE WIDTH: %d
                        MAP JUNGLE HEIGHT: %d
                        MAP JUNGLE CORNER: %s
                        MAP STEPPE PLANT NUMBER: %d
                        MAP JUNGLE PLANT NUMBER: %d
                        MAP MAX STEPPE PLANT NUMBER: %d
                        MAP MAX JUNGLE PLANT NUMBER %d
                        MAP ANIMALS: %s
                        MAP PLANTS: %s
                        """, width, height, startEnergy, plantEnergy, jungleWidth, jungleHeight, jungleCorner, steppePlantNumber, junglePlantNumber
        , maxSteppePlantNumber, maxJunglePlantNumber, animals, plants);
    }
}