package evogen;

import java.util.Random;

public class Vector2d {

    public static Vector2d getRandomVector(int width, int height) {
        // returns random Vector2d bounded by width and height
        int rnd_height = new Random().nextInt(height);
        int rnd_width = new Random().nextInt(width);
        return new Vector2d(rnd_width, rnd_height);
    }

    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
