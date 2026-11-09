package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class WorldMap {
    private final int width;
    private final int height;
    private final Map<Coordinate, Entity> entities = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Map<Coordinate, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public void addEntity(Coordinate coordinate, Entity entity) {
        validate(coordinate);
        entities.put(coordinate, entity);
    }

    public void removeEntity(Coordinate coordinate) {
        validate(coordinate);
        entities.remove(coordinate);
    }

    public Optional<Entity> getEntity(Coordinate coordinate) {
        validate(coordinate);
        return Optional.ofNullable(entities.get(coordinate));
    }

    public Optional<Coordinate> getCoordinate(Entity entity) {
        for (Map.Entry<Coordinate, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == entity) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    public void moveEntity(Coordinate from, Coordinate to) {
        validate(from);
        validate(to);

        Entity entity = getEntity(from)
                .orElseThrow(() -> new IllegalStateException("No entity at " + from));

        if (!isCellEmpty(to)) {
            throw new IllegalStateException("Cell is occupied: " + to);
        }

        removeEntity(from);
        addEntity(to, entity);
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        validate(coordinate);
        return !entities.containsKey(coordinate);
    }

    public boolean isInBounds(Coordinate coordinate) {
        return coordinate.row() >= 0 && coordinate.row() < height &&
                coordinate.column() >= 0 && coordinate.column() < width;
    }

    public boolean isFull() {
        return entities.size() == width * height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void validate(Coordinate coordinate) {
        if (!isInBounds(coordinate)) {
            throw new IllegalArgumentException("Coordinate is not valid: " + coordinate);
        }
    }
}
