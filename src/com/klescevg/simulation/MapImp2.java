package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;

import java.util.HashMap;

public class MapImp2 {
    private int sideLength;
    private final HashMap<Entity, Coordinates> map = new HashMap<>();

    public MapImp2(int sideLength) {
        this.sideLength = sideLength;
    }

    public void addEntity(Entity entity, Coordinates position) {
        if (!map.containsKey(entity) && !map.containsValue(position)){
            map.put(entity, position);
        }
    }

    public void removeEntity(Entity entity) {
        map.remove(entity);
    }

    public void moveEntity(Entity entity, Coordinates newPosition) {
        if (!map.containsValue(newPosition)) {
            map.replace(entity, newPosition);
        }
    }

    public void display() {
        for (int y = 0; y < sideLength; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < sideLength; x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (map.containsValue(coordinates)) {
                    for (Entity e : map.keySet()){
                        if (map.get(e).equals(coordinates)){
                            line.append(" ").append(e.symbol).append(" ");
                        }
                    }
                } else {
                    line.append(" _ ");
                }
            }
            System.out.println(line);
        }

        for (Entity i : map.keySet()) {
            System.out.print(i + " - ");
            System.out.println(map.get(i));
        }
    }
}
