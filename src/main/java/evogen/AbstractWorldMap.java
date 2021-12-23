package evogen;

import java.util.*;

abstract public class AbstractWorldMap implements IMapActionObserver {
    protected final Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Plant> plants = new HashMap<>();
    protected final List<Animal> deadAnimals = new ArrayList<>(); // TODO nie wiem czy to potrzebne

    public final int width;
    public final int height;

    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;

    protected final int jungleSide;
    protected final Vector2d jungleCorner;  // lower left jungle corner, inclusive

    protected final Map<Genotype, Integer> genotypeCounter = new HashMap<>();
    protected int plantNumber = 0;
    protected int animalNumber = 0;
    protected int sumEnergy;
    protected int sumLifespan = 0;
    protected int sumChildrenNumber = 0; // TODO te rzeczy do przetestowania

    // CONSTRUCTOR

    public AbstractWorldMap(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio,
                            int initialAnimals) {
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleSide = (int) Math.round(Math.sqrt((jungleRatio / (1 + jungleRatio)) * (this.width * this.height)));
        this.jungleCorner = new Vector2d(((this.width - this.jungleSide)/2), ((this.height - this.jungleSide)/2));

        initializeAnimals(initialAnimals);
    }

    private void initializeAnimals(int initialAnimals) { // TODO w tych rzeczach można by na końcu petle, która stawai na pierwszym wolnym  miejscu
        int i = 0;
        int iterator = 0;
        while (i < initialAnimals && iterator < 2 * this.height*this.width) {
            Vector2d rndVector = Vector2d.getRandomVector(this.width, this.height);
            if (this.isOccupiedByAnimal(rndVector, 1)) {
                iterator++;
                continue;
            }
            Genotype rndGenotype = Genotype.generateRandomGenotype();
            Animal rndAnimal = new Animal(rndVector, startEnergy, rndGenotype, this, 0);
            placeAnimal(rndAnimal);
            i++;
        }
    }

    // GETTERS AND SETTERS

    abstract Vector2d getMovePosition(Vector2d animalPosition, MapDirection direction);

    public Map<Vector2d, TreeSet<Animal>> getAnimals() {
        return this.animals;
    }

    public Plant getPlantAt(Vector2d position) {
        return this.plants.get(position);
    }

    public int getAverageLifespan() {
        if (this.deadAnimals.size() == 0) return -1;
        return this.sumLifespan/this.deadAnimals.size();
    }

    public int getAverageEnergy() {
        if (this.animalNumber == 0) return -1;
        return this.sumEnergy/this.animalNumber;
    }

    public int getAverageChildrenNumber() {
        if (this.animalNumber == 0) return -1;
        return this.sumChildrenNumber/this.animalNumber;
    }

    public int getAnimalNumber() {
        return this.animalNumber;
    }

    public int getPlantNumber() {
        return this.plantNumber;
    }

    public List<Genotype> getMostCommonGenotype() {
        List<Genotype> mostCommonGenotypes = new ArrayList<>();
        int frequency = 0;
        for (var entry: this.genotypeCounter.entrySet()) {
            if ( entry.getValue() > frequency || mostCommonGenotypes.size() == 0) {
                mostCommonGenotypes.clear();
                mostCommonGenotypes.add(entry.getKey());
                frequency = entry.getValue();
            }
            else if (entry.getValue() == frequency) {
                mostCommonGenotypes.add(entry.getKey());
            }
        }

        return mostCommonGenotypes;
    }

    public List<Animal> getStrongestAnimalsAt(Vector2d position) {
        if (!this.isOccupiedByAnimal(position, 1)) throw new IllegalArgumentException("No animals on this field");

        TreeSet<Animal> animalsOnPos = this.animals.get(position);
        List<Animal> strongestAnimals = new ArrayList<>();

        for (Animal animal : animalsOnPos.descendingSet()) {
            if (animal.getEnergy() == animalsOnPos.last().getEnergy()) {
                strongestAnimals.add(animal);
            } else break;
        }

         return strongestAnimals;
    }

    public Animal[] getTwoStrongestAnimalsAt(Vector2d position) {
        if (this.isOccupiedByAnimal(position, 2)) throw new IllegalArgumentException("Less than 2 animals on this field");

        Iterator<Animal> animalsOnPos = this.animals.get(position).descendingIterator();
        Animal[] twoStrongestAnimals = new Animal[2];
        twoStrongestAnimals[0] = animalsOnPos.next();
        twoStrongestAnimals[1] = animalsOnPos.next();

        return twoStrongestAnimals;
    }

    public void changeSumEnergy(int energy) {
        this.sumEnergy += energy;
    }

    public void changeSumChildrenNumber(int children) {
        this.sumChildrenNumber += children;
    }

    // IS OCCUPIED

    public boolean isOccupiedByPlant(Vector2d position) {
        return this.plants.containsKey(position);
    }

    public boolean isOccupiedByAnimal(Vector2d position, int size) {
        return (this.animals.containsKey(position) && this.animals.get(position).size() >= size);
    }

    public boolean isOccupied(Vector2d position) {
        return isOccupiedByAnimal(position, 1) || isOccupiedByPlant(position);
    }

    public boolean isInJungle(Vector2d position) {
        return position.isInside(this.jungleCorner.x, this.jungleCorner.x + this.jungleSide,
                this.jungleCorner.y, this.jungleCorner.y + this.jungleSide);
    }

    // PLACE AND REMOVE OBJECT ON MAP METHODS

    public void placeAnimal(Animal animal) {
        Vector2d animalPosition = animal.getPosition();
        if (!isOccupiedByAnimal(animalPosition, 1)) {
            this.animals.put(animalPosition, new TreeSet<>((o1, o2) -> {
                if (o1 == o2) return 0;
                else if (o1.getEnergy() != o2.getEnergy()) return Integer.compare(o1.getEnergy(), o2.getEnergy());
                else if (o1.getBornEra() != o2.getBornEra()) return Integer.compare(o1.getBornEra(), o2.getBornEra());
                else return Integer.compare(o1.getGenotype().hashCode(), o2.getGenotype().hashCode());
            }));
        }
        this.animals.get(animalPosition).add(animal);

        this.animalNumber++;
        this.sumEnergy += animal.getEnergy();

        if (this.genotypeCounter.get(animal.getGenotype()) == null) {
            this.genotypeCounter.put(animal.getGenotype(), 1);
        }
        else {
            this.genotypeCounter.merge(animal.getGenotype(), 1, Integer::sum);
        }
    }

    public void placePlants() {
        int iterations = 0;
        Vector2d rndPosition1 = Vector2d.getRandomVector(jungleSide, jungleSide).add(this.jungleCorner);
        while (this.isOccupied(rndPosition1) && iterations < 2 * this.jungleSide * this.jungleSide) {
            rndPosition1= Vector2d.getRandomVector(jungleSide, jungleSide).add(this.jungleCorner);
            iterations++;
        }
        if (iterations < this.jungleSide * this.jungleSide) {
            Plant newPlant = new Plant(rndPosition1, this.plantEnergy);
            this.plants.put(rndPosition1, newPlant);
            this.plantNumber++;
        }

        iterations = 0;
        Vector2d rndPosition2 = Vector2d.getRandomVector(width, height);
        while ((this.isOccupied(rndPosition2) || isInJungle(rndPosition2)) && iterations < 2 * this.width * this.height) {
            rndPosition2 = Vector2d.getRandomVector(jungleSide, jungleSide).add(this.jungleCorner);
            iterations++;
        }
        if (iterations < this.jungleSide * this.jungleSide) {
            Plant newPlant = new Plant(rndPosition2, this.plantEnergy);
            this.plants.put(rndPosition2, newPlant);
            this.plantNumber++;
        }
    }

    public void removeDeadAnimal(Animal animal, int era) {
        this.animals.get(animal.getPosition()).remove(animal);
        if (this.animals.get(animal.getPosition()).isEmpty()) this.animals.remove(animal.getPosition());
        animal.setDeathEra(era);
        this.deadAnimals.add(animal);

        this.sumLifespan += animal.getDeathEra() - animal.getBornEra();
        this.sumChildrenNumber -= animal.getChildrenNumber();
        this.animalNumber--;
        this.genotypeCounter.merge(animal.getGenotype(), 1, (a, b) -> a - b);
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
        this.plantNumber--;
        }
    }

    // TO STRING
    // TODO do usuniecia
    public String toString() {
        return String.format("""
                        MAP #############################################
                        MAP WIDTH: %d
                        MAP HEIGHT: %d
                        MAP START ENERGY: %d
                        MAP PLANT ENERGY: %d
                        MAP JUNGLE SIDE: %d
                        MAP JUNGLE CORNER: %s
                        MAP PLANT NUMBER: %d
                        MAP ANIMAL NUMBER: %d
                        MAP AVERAGE ENERGY: %d
                        MAP AVERAGE LIFESPAN: %d
                        MAP AVERAGE CHILDREN NUMBER: %d
                        MAP GENOTYPE COUNTER: %s
                        MAP ANIMALS: %s
                        MAP PLANTS: %s
                        #################################################
                        """, width, height, startEnergy, plantEnergy, jungleSide,
                jungleCorner, plantNumber, animalNumber, this.getAverageEnergy(), this.getAverageLifespan(),
                this.getAverageChildrenNumber(), this.getMostCommonGenotype(), animals, plants);
    }
}