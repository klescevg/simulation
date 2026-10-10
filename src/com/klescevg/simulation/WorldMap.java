package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

import static com.klescevg.simulation.CoordinatesGenerator.random;

public class WorldMap {
    private int sideLength;
    private final HashMap<Coordinates, Entity> map = new HashMap<>();

    public WorldMap(int length) {
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
        }
    }

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
        Collection<Entity> entities = new ArrayList<>();
        for (Entity entity : map.values()){
            if (entity != null){
                entities.add(entity);
            }
        }
        return entities;
    }

    public <T extends Entity> Collection<T> getEntitiesOfType(Class<T> type){
        Collection<T> result = new ArrayList<>();
        for (Entity entity : map.values()){
            if (type.isInstance(entity)){
                result.add(type.cast(entity));
            }
        }
        return result;
    }

    public Set<Coordinates> getCoordinates() {
        return map.keySet();
    }

    public Set<Coordinates> getCoordinatesOfType(Class<?> type){
        Set<Coordinates> result = new HashSet<>();
        for (Coordinates coordinates : map.keySet()){
            if (type.isInstance(map.get(coordinates))){
                result.add(coordinates);
            }
        }
        return result;
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

    public boolean isEntityTypePresent(Class<?> entityType){
        for (Entity mapEntity : getEntities()){
            if (entityType.isInstance(mapEntity)){
                return true;
            }
        }
        return false;
    }
}
