package evogen;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {

        int width = 10;
        int height = 10;
        int startEnergy = 100000;
        int moveEnergy = 4;
        int plantEnergy = 10;
        double jungleRatio = 0.097; // daje stosunek boków jungli do całosci ok 0.3
        int initialAnimals = 2;
        AbstractWorldMap foldedMap = new FoldedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);
        AbstractWorldMap boundedMap = new BoundedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);

        SimulationEngine foldedEngine = new SimulationEngine(foldedMap, false, 500);
        SimulationEngine boundedEngine = new SimulationEngine(boundedMap, false, 500);
        Thread engine = new Thread(foldedEngine::run);
        engine.start();
    }
}
