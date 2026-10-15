package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

public class WorldMap {
    private final int sideLength;
    private final Map<Coordinate, Entity> entities = new HashMap<>();

    public WorldMap(int length) {
        this.sideLength = length;
    }

    public Map<Coordinate, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public void addEntity(Coordinate coordinate, Entity entity) {
        validate(coordinate);
        entities.put(coordinate, entity);
    }

    public Optional<Entity> getEntity(Coordinate coordinate) {
        validate(coordinate);
        return Optional.ofNullable(entities.get(coordinate));
    }

    public void removeEntity(Coordinate coordinate) {
        validate(coordinate);
        entities.remove(coordinate);
    }

    public void moveEntity(Coordinate initialPosition, Coordinate newPosition) {
        validate(initialPosition);
        validate(newPosition);

        Entity entity = getEntity(initialPosition)
                .orElseThrow(() -> new IllegalStateException("No entity at " + initialPosition));

        if (!isCellEmpty(newPosition)) {
            throw new IllegalStateException("Cell is occupied: " + newPosition);
        }

        removeEntity(initialPosition);
        addEntity(newPosition, entity);
    }

    public Optional<Coordinate> getCoordinate(Entity entity) {
        for (Map.Entry<Coordinate, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == entity) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    public <T extends Entity> Collection<T> getEntitiesOfType(Class<T> type) {
        Collection<T> result = new ArrayList<>();
        for (Entity entity : entities.values()) {
            if (type.isInstance(entity)) {
                result.add(type.cast(entity));
            }
        }
        return result;
    }

    public Set<Coordinate> getCoordinatesOfType(Class<?> type) {
        Set<Coordinate> result = new HashSet<>();
        for (Coordinate coordinate : entities.keySet()) {
            if (type.isInstance(entities.get(coordinate))) {
                result.add(coordinate);
            }
        }
        return result;
    }

    public Set<Coordinate> getBorderCells(Coordinate coordinate) {
        Set<Coordinate> borderCells = new HashSet<>();

        Coordinate left = new Coordinate(coordinate.row(), coordinate.column() - 1);
        Coordinate right = new Coordinate(coordinate.row(), coordinate.column() + 1);
        Coordinate top = new Coordinate(coordinate.row() - 1, coordinate.column());
        Coordinate bottom = new Coordinate(coordinate.row() + 1, coordinate.column());

        List<Coordinate> neighbors = List.of(left, right, top, bottom);

        for (Coordinate neighbor : neighbors) {
            if (isInBounds(neighbor)) {
                borderCells.add(neighbor);
            }
        }

        return borderCells;
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        validate(coordinate);
        return !entities.containsKey(coordinate);
    }

    private void validate(Coordinate coordinate) {
        if (!isInBounds(coordinate)) {
            throw new IllegalArgumentException("Coordinate is not valid: " + coordinate);
        }
    }

    public boolean isInBounds(Coordinate coordinate) {
        return coordinate.row() >= 0 && coordinate.row() < sideLength &&
                coordinate.column() >= 0 && coordinate.column() < sideLength;
    }

    public boolean isFull(){
        return entities.size() == sideLength*sideLength;
    }

    public int sideLength() {
        return sideLength;
    }
}
