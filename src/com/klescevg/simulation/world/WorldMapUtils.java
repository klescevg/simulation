package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Creature;

import java.util.*;

public final class WorldMapUtils {
    private WorldMapUtils() {
    }

    public static List<Creature> getCreatures(WorldMap map) {
        List<Creature> result = new ArrayList<>();
        for (Entity entity : map.getEntities().values()) {
            if (entity instanceof Creature) {
                result.add((Creature) entity);
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
