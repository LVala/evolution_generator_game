package evogen;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {

        int width = 10;
        int height = 10;
        int startEnergy = 10;
        int moveEnergy = 4;
        int plantEnergy = 10;
        double jungleRatio = 0.097; // daje stosunek boków jungli do całosci ok 0.3
        int initialAnimals = 2;
        String[] a = Arrays.stream(new Integer[]{1,2,3,4,5}).map(String::valueOf).toArray(String[]::new);
        System.out.println(String.join(",", Arrays.stream(new Integer[]{1,2,3,4,5}).map(String::valueOf).toArray(String[]::new)));
    }
}
