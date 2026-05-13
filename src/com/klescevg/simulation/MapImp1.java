package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MapImp1 {
    private int sideLength;
    private final HashMap<Coordinates, Entity> map = new HashMap<>();

    public MapImp1(int length) {
        this.sideLength = length;
    }

    public int sideLength() {
        return sideLength;
    }

    public void addEntity(Coordinates entityCoordinates, Entity entity) {
        if (!map.containsKey(entityCoordinates)) {
            map.put(entityCoordinates, entity);
        } else {
            System.out.println("Cell is already full");
        }
    }

    public void moveEntity(Coordinates initialPosition, Coordinates newPosition) {
        if(!map.containsKey(newPosition)) {
            addEntity(newPosition, map.get(initialPosition));
            clearCell(initialPosition);
        } else {
            System.out.println("New position is already full");
        }
    }

    public Entity getEntity(Coordinates position){
        return map.get(position);
    }

    public Collection<Entity> getEntities(){
        return map.values();
    }

    public Coordinates getEntityCoordinates(Entity entity){
        for (Coordinates c : map.keySet()){
            if (map.get(c) == entity){
                return c;
            }
        }
        return null;
    }

    public void clearCell(Coordinates coordinates) {
        map.remove(coordinates);
    }

    public void display() {
        for (int y = 0; y < sideLength; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < sideLength; x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (map.containsKey(coordinates)) {
                    line.append(" ").append(map.get(coordinates).symbol).append(" ");
                } else {
                    line.append(" _ ");
                }
            }
            System.out.println(line);
        }

        for (Coordinates i : map.keySet()) {
            System.out.print(i + " - ");
            System.out.println(map.get(i));
        }
    }
}
