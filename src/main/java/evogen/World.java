package evogen;

public class World {
    public static void main(String[] args) {

        int width = 10;
        int height = 10;
        int startEnergy = 10;
        int moveEnergy = 4;
        int plantEnergy = 10;
        double jungleRatio = 0.097; // daje stosunek boków jungli do całosci ok 0.3
        int initialAnimals = 2;

        AbstractWorldMap map = new FoldedMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleRatio, initialAnimals);

        SimulationEngine engine = new SimulationEngine(map, false);

        System.out.println(map);
        engine.run();
    }
}
