import entities.Entity;

import java.util.HashMap;

public class Map {
    private int sideLength;
    private final HashMap<Coordinates, Entity> map;

    public Map(int sideLength) {
        this.sideLength = sideLength;
        this.map = new HashMap<>();
    }
}
