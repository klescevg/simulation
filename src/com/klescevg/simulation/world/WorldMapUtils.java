package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

public final class WorldMapUtils {
    private static final List<Coordinate> NEIGHBOR_OFFSET = List.of(
            new Coordinate(0, -1),
            new Coordinate(0, 1),
            new Coordinate(-1, 0),
            new Coordinate(1, 0));

    private WorldMapUtils() {
    }

    public static <T extends Entity> List<T> getEntitiesOfType(WorldMap map, Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Entity entity : map.getEntities().values()) {
            if (type.isInstance(entity)) {
                result.add(type.cast(entity));
            }
        }
        return result;
    }

    public static Set<Coordinate> getCoordinatesOfType(WorldMap map, Class<? extends Entity> type) {
        Set<Coordinate> result = new HashSet<>();
        for (Map.Entry<Coordinate, Entity> entry : map.getEntities().entrySet()) {
            if (type.isInstance(entry.getValue())) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public static Set<Coordinate> getNeighbors(WorldMap map, Coordinate coordinate) {
        Set<Coordinate> neighbors = new HashSet<>();

        for (Coordinate offset : NEIGHBOR_OFFSET) {
            Coordinate neighbor = new Coordinate(coordinate.row() + offset.row(), coordinate.column() + offset.column());
            if (map.isInBounds(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
}
