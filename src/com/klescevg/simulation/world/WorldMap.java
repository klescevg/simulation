package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

public class WorldMap {
    private final int sideLength;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMap(int length) {
        this.sideLength = length;
    }

    public Map<Coordinates, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        validate(coordinates);
        entities.put(coordinates, entity);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        validate(coordinates);
        return Optional.ofNullable(entities.get(coordinates));
    }

    public void removeEntity(Coordinates coordinates) {
        validate(coordinates);
        entities.remove(coordinates);
    }

    public void moveEntity(Coordinates initialPosition, Coordinates newPosition) {
        validate(initialPosition);
        validate(newPosition);

        Entity entity = getEntity(initialPosition).orElseThrow(() -> new IllegalStateException("No entity at " + initialPosition));

        if (!isCellEmpty(newPosition)) {
            throw new IllegalStateException("Cell is occupied: " + newPosition);
        }

        removeEntity(initialPosition);
        addEntity(newPosition, entity);
    }

    public Optional<Coordinates> getCoordinates(Entity entity) {
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
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

    public Set<Coordinates> getCoordinatesOfType(Class<?> type) {
        Set<Coordinates> result = new HashSet<>();
        for (Coordinates coordinates : entities.keySet()) {
            if (type.isInstance(entities.get(coordinates))) {
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

        List<Coordinates> neighbors = List.of(left, right, top, bottom);

        for (Coordinates neighbor : neighbors) {
            if (isValidCoordinate(neighbor)) {
                borderCells.add(neighbor);
            }
        }

        return borderCells;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        validate(coordinates);
        return !entities.containsKey(coordinates);
    }

    private void validate(Coordinates coordinates) {
        if (!isValidCoordinate(coordinates)) {
            throw new IllegalArgumentException("Coordinate is not valid: " + coordinates);
        }
    }

    public boolean isValidCoordinate(Coordinates coordinates) {
        return coordinates.x() >= 0 && coordinates.x() < sideLength &&
                coordinates.y() >= 0 && coordinates.y() < sideLength;
    }

    public int sideLength() {
        return sideLength;
    }
}
