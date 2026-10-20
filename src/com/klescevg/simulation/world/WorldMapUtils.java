package com.klescevg.simulation.world;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Creature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static Set<Coordinate> getBorderCells(WorldMap map, Coordinate coordinate) {
        Set<Coordinate> borderCells = new HashSet<>();

        Coordinate left = new Coordinate(coordinate.row(), coordinate.column() - 1);
        Coordinate right = new Coordinate(coordinate.row(), coordinate.column() + 1);
        Coordinate top = new Coordinate(coordinate.row() - 1, coordinate.column());
        Coordinate bottom = new Coordinate(coordinate.row() + 1, coordinate.column());

        List<Coordinate> neighbors = List.of(left, right, top, bottom);

        for (Coordinate neighbor : neighbors) {
            if (map.isInBounds(neighbor)) {
                borderCells.add(neighbor);
            }
        }
        return borderCells;
    }
}
