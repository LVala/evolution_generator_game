package evogen;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {

        int width = 100;
        int height = 100;
        int startEnergy = 10;
        int moveEnergy = 10;
        int plantEnergy = 10;
        double jungleRatio = 0.097; // daje stosunek boków jungli do całosci ok 0.3
        int initialAnimals = 0;

        AbstractWorldMap map = new FoldedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);
        Animal zwierz = new Animal(new Vector2d(0,0), 10, Genotype.generateRandomGenotype(), map, 0);
        Animal zwierz2 = new Animal(new Vector2d(0,0), 10, Genotype.generateRandomGenotype(), map, 0);
        Animal zwierz3 = new Animal(new Vector2d(0,0), 30, Genotype.generateRandomGenotype(), map, 0);

        map.placeAnimal(zwierz);
        map.placeAnimal(zwierz2);
        map.placeAnimal(zwierz3);
        map.placePlants();
        System.out.println(map);
        System.out.println(Arrays.toString(map.getTwoStrongestAnimalsAt(new Vector2d(0,0))));
    }
}
