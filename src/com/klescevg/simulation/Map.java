package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.klescevg.simulation.CoordinatesGenerator.random;

public class Map {
    private int sideLength;
    private final HashMap<Coordinates, Entity> map = new HashMap<>();

    public Map(int length) {
        this.sideLength = length;

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                map.put(new Coordinates(i, j), null);
            }
        }
    }

    public int sideLength() {
        return sideLength;
    }

    public void addEntity(Coordinates entityCoordinates, Entity entity) {
        map.putIfAbsent(entityCoordinates, entity);
    }

    public void addEntityToRandomPosition(Entity entity) {
        Coordinates randomCoordinates = random(sideLength);
        while (!isCellEmpty(randomCoordinates)) {
            randomCoordinates = random(sideLength);
        }
        map.put(randomCoordinates, entity);
    }

    public void moveEntity(Coordinates initialPosition, Coordinates newPosition) {
        if (isCellEmpty(newPosition)) {
            addEntity(newPosition, map.get(initialPosition));
            clearCell(initialPosition);
        } else {
            System.out.println("New position is already full");
        }
    }

    //работает даже при вложении нового объекта координат
    public Entity getEntity(Coordinates coordinates) {
        return map.get(coordinates);
    }

    public Coordinates getEntityCoordinates(Entity entity) {
        for (Coordinates c : map.keySet()) {
            if (map.get(c) == entity) {
                return c;
            }
        }
        return null;
    }

    public Collection<Entity> getEntities() {
        return map.values();
    }

    public Set<Coordinates> getCoordinates() {
        return map.keySet();
    }

    public Set<Coordinates> getBorderCells(Coordinates coordinates) {
        Set<Coordinates> borderCells = new HashSet<>();

        Coordinates left = new Coordinates(coordinates.x() - 1, coordinates.y());
        Coordinates right = new Coordinates(coordinates.x() + 1, coordinates.y());
        Coordinates top = new Coordinates(coordinates.x(), coordinates.y() - 1);
        Coordinates bottom = new Coordinates(coordinates.x(), coordinates.y() + 1);

        if (map.containsKey(left)) {
            borderCells.add(left);
        }

        if (map.containsKey(right)) {
            borderCells.add(right);
        }

        if (map.containsKey(top)) {
            borderCells.add(top);
        }

        if (map.containsKey(bottom)) {
            borderCells.add(bottom);
        }

        return borderCells;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return map.get(coordinates) == null;
    }

    public void clearCell(Coordinates coordinates) {
        map.replace(coordinates, null);
    }
}
