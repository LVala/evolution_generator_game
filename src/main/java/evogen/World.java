package evogen;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {

        int width = 3;
        int height = 3;
        int startEnergy = 10;
        int moveEnergy = 10;
        int plantEnergy = 10;
        double jungleRatio = 0.33333;
        int initialAnimals = 0;

        AbstractWorldMap map = new FoldedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);
        Animal zwierz = new Animal(new Vector2d(0,0), 10, Genotype.generateRandomGenotype(), map);
        map.placeAnimal(zwierz);
        System.out.println(map);
        zwierz.move();
        System.out.println(map);
    }
}
