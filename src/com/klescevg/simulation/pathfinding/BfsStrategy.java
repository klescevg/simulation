package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.Entity;

import java.util.*;

public class BfsStrategy implements PathfindingStrategy {
    private final WorldMap worldMap;

    public BfsStrategy(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public Path find(Entity startEntity, Class<? extends Entity> targetEntityType) {
        Coordinate start = worldMap.getCoordinate(startEntity)
                .orElseThrow(() -> new IllegalStateException("Start entity is not found on map"));

        Queue<Coordinate> queue = new ArrayDeque<>();
        Set<Coordinate> visitedCells = new HashSet<>();
        Map<Coordinate, Coordinate> parents = new HashMap<>();

        queue.add(start);
        visitedCells.add(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            if (isTarget(current, targetEntityType)) {
                return Path.reconstruct(parents, start, current);
            }

            for (Coordinate neighbor : worldMap.getBorderCells(current)) {
                if (!visitedCells.contains(neighbor) && isWalkable(neighbor, targetEntityType)) {
                    visitedCells.add(neighbor);
                    parents.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Path.empty();
    }

    private boolean isWalkable(Coordinate cell, Class<? extends Entity> targetEntityType) {
        return worldMap.isCellEmpty(cell) || isTarget(cell, targetEntityType);
    }

    private boolean isTarget(Coordinate cell, Class<? extends Entity> targetEntityType) {
        return worldMap.getEntity(cell).filter(targetEntityType::isInstance).isPresent();
    }
}
