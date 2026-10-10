package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

import static com.klescevg.simulation.world.CoordinatesGenerator.random;

public class WorldMap {
    private int sideLength;
    private final Map<Coordinates, Entity> coordinatesWithEntities = new HashMap<>();

    public WorldMap(int length) {
        this.sideLength = length;
    }

    public int sideLength() {
        return sideLength;
    }

    public void addEntity(Coordinates entityCoordinates, Entity entity) {
        coordinatesWithEntities.putIfAbsent(entityCoordinates, entity);
    }

    public void addEntityToRandomPosition(Entity entity) {
        Coordinates randomCoordinates = random(sideLength);
        while (!isCellEmpty(randomCoordinates)) {
            randomCoordinates = random(sideLength);
        }
        coordinatesWithEntities.put(randomCoordinates, entity);
    }

    public void moveEntity(Coordinates initialPosition, Coordinates newPosition) {
        if (isCellEmpty(newPosition)) {
            addEntity(newPosition, coordinatesWithEntities.get(initialPosition));
            clearCell(initialPosition);
        }
    }

    public Entity getEntity(Coordinates coordinates) {
        return coordinatesWithEntities.get(coordinates);
    }

    public Coordinates getEntityCoordinates(Entity entity) {
        for (Coordinates c : coordinatesWithEntities.keySet()) {
            if (coordinatesWithEntities.get(c) == entity) {
                return c;
            }
        }
        return null;
    }

    public Collection<Entity> getEntities() {
        Collection<Entity> entities = new ArrayList<>();
        for (Entity entity : coordinatesWithEntities.values()){
            if (entity != null){
                entities.add(entity);
            }
        }
        return entities;
    }

    public <T extends Entity> Collection<T> getEntitiesOfType(Class<T> type){
        Collection<T> result = new ArrayList<>();
        for (Entity entity : coordinatesWithEntities.values()){
            if (type.isInstance(entity)){
                result.add(type.cast(entity));
            }
        }
        return result;
    }

    public Set<Coordinates> getCoordinates() {
        return coordinatesWithEntities.keySet();
    }

    public Set<Coordinates> getCoordinatesOfType(Class<?> type){
        Set<Coordinates> result = new HashSet<>();
        for (Coordinates coordinates : coordinatesWithEntities.keySet()){
            if (type.isInstance(coordinatesWithEntities.get(coordinates))){
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

        if (coordinatesWithEntities.containsKey(left)) {
            borderCells.add(left);
        }

        if (coordinatesWithEntities.containsKey(right)) {
            borderCells.add(right);
        }

        if (coordinatesWithEntities.containsKey(top)) {
            borderCells.add(top);
        }

        if (coordinatesWithEntities.containsKey(bottom)) {
            borderCells.add(bottom);
        }

        return borderCells;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return coordinatesWithEntities.get(coordinates) == null;
    }

    public void clearCell(Coordinates coordinates) {
        coordinatesWithEntities.replace(coordinates, null);
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
