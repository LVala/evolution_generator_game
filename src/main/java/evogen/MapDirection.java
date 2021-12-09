package evogen;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public static MapDirection getRandomDirection() {
        // returns random MapDirection
        MapDirection[] directionList = MapDirection.values();
        int rnd = new Random().nextInt(directionList.length);
        return directionList[rnd];
    }

    public MapDirection next() {
        // returns next MapDirection clockwise
        MapDirection[] directionList = MapDirection.values();

        for (int i = 0; i< directionList.length; i++) {
            if (directionList[i] == this) return directionList[(i+1)%directionList.length];
        }
        // this will never occur but intellij throws an error without this return, TODO how to make it nicer
        return null;
    }

    public Vector2d getDirectionVector() {
        // returns Vector2d corresponding to current direction
        return switch(this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }
}
