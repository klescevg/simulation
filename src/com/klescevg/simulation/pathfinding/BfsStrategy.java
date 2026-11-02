package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.entities.Entity;

import java.util.*;

import static com.klescevg.simulation.world.WorldMapUtils.getNeighbors;

public class BfsStrategy implements PathfindingStrategy {
    @Override
    public Path find(WorldMap map, Coordinate start, Class<? extends Entity> targetType) {
        Queue<Coordinate> queue = new ArrayDeque<>();
        Set<Coordinate> visitedCells = new HashSet<>();
        Map<Coordinate, Coordinate> parents = new HashMap<>();

        queue.add(start);
        visitedCells.add(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (isTarget(map, current, targetType)) {
                return Path.reconstruct(parents, start, current);
            }

            for (Coordinate neighbor : getNeighbors(map, current)) {
                if (!visitedCells.contains(neighbor) && isWalkable(map, neighbor, targetType)) {
                    visitedCells.add(neighbor);
                    parents.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Path.empty();
    }

    private boolean isWalkable(WorldMap map, Coordinate cell, Class<? extends Entity> targetType) {
        return map.isCellEmpty(cell) || isTarget(map, cell, targetType);
    }

    private boolean isTarget(WorldMap map, Coordinate cell, Class<? extends Entity> targetType) {
        return map.getEntity(cell).filter(targetType::isInstance).isPresent();
    }
}
