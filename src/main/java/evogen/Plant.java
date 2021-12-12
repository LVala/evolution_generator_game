package evogen;

public class Plant {
    private final Vector2d position;
    private final int plantEnergy;

    public Plant(Vector2d position, int plantEnergy) {
        this.position = position;
        this.plantEnergy = plantEnergy;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getPlantEnergy() {
        return this.plantEnergy;
    }

    public String toString() {
        return String.format("|Position: %s|", this.position);
    }
}
