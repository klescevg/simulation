package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;

import java.util.*;

public final class WorldMapUtils {
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
        Coordinate left = new Coordinate(coordinate.row(), coordinate.column() - 1);
        Coordinate right = new Coordinate(coordinate.row(), coordinate.column() + 1);
        Coordinate top = new Coordinate(coordinate.row() - 1, coordinate.column());
        Coordinate bottom = new Coordinate(coordinate.row() + 1, coordinate.column());

        List<Coordinate> allNeighbors = List.of(left, right, top, bottom);
        Set<Coordinate> neighborsInBounds = new HashSet<>();

        for (Coordinate neighbor : allNeighbors) {
            if (map.isInBounds(neighbor)) {
                neighborsInBounds.add(neighbor);
            }
        }
        return neighborsInBounds;
    }
}
