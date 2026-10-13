package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

import static com.klescevg.simulation.world.CoordinatesGenerator.random;

public class WorldMap {
    private int sideLength;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMap(int length) {
        this.sideLength = length;
    }

    public int sideLength() {
        return sideLength;
    }

    public void addEntity(Coordinates entityCoordinates, Entity entity) {
        entities.putIfAbsent(entityCoordinates, entity);
    }

    public void addEntityToRandomPosition(Entity entity) {
        Coordinates randomCoordinates = random(sideLength);
        while (!isCellEmpty(randomCoordinates)) {
            randomCoordinates = random(sideLength);
        }
        entities.put(randomCoordinates, entity);
    }

    public void moveEntity(Coordinates initialPosition, Coordinates newPosition) {
        if (isCellEmpty(newPosition)) {
            addEntity(newPosition, entities.get(initialPosition));
            clearCell(initialPosition);
        }
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entities.get(coordinates));
    }

    public Coordinates getEntityCoordinates(Entity entity) {
        for (Coordinates c : entities.keySet()) {
            if (entities.get(c) == entity) {
                return c;
            }
        }
        return null;
    }

    public Map<Coordinates, Entity> getEntities() {
        return new HashMap(entities);
    }

    public <T extends Entity> Collection<T> getEntitiesOfType(Class<T> type){
        Collection<T> result = new ArrayList<>();
        for (Entity entity : entities.values()){
            if (type.isInstance(entity)){
                result.add(type.cast(entity));
            }
        }
        return result;
    }

    public Set<Coordinates> getCoordinatesOfType(Class<?> type){
        Set<Coordinates> result = new HashSet<>();
        for (Coordinates coordinates : entities.keySet()){
            if (type.isInstance(entities.get(coordinates))){
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

        if (entities.containsKey(left)) {
            borderCells.add(left);
        }

        if (entities.containsKey(right)) {
            borderCells.add(right);
        }

        if (entities.containsKey(top)) {
            borderCells.add(top);
        }

        if (entities.containsKey(bottom)) {
            borderCells.add(bottom);
        }

        return borderCells;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return entities.get(coordinates) == null;
    }

    public void clearCell(Coordinates coordinates) {
        entities.replace(coordinates, null);
    }

    public boolean isEntityTypePresent(Class<?> entityType){
        for (Entity mapEntity : getEntities().values()){
            if (entityType.isInstance(mapEntity)){
                return true;
            }
        }
        return false;
    }
}
