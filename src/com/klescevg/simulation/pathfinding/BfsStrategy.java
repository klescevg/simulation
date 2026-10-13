package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinates;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.Entity;

import java.util.*;

public class BfsStrategy implements PathfindingStrategy {
    private final WorldMap worldMap;

    public BfsStrategy(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public Path findPath(Entity startEntity, Class<? extends Entity> targetEntityType) {
        Coordinates start = worldMap.getEntityCoordinates(startEntity);

        Queue<Coordinates> queue = new ArrayDeque<>();
        Set<Coordinates> visitedCells = new HashSet<>();
        Map<Coordinates, Coordinates> parents = new HashMap<>();

        queue.add(start);
        visitedCells.add(start);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (isTarget(current, targetEntityType)) {
                return Path.fromParents(parents, start, current);
            }

            for (Coordinates neighbor : worldMap.getBorderCells(current)) {
                if (!visitedCells.contains(neighbor) && isWalkable(neighbor, targetEntityType)) {
                    visitedCells.add(neighbor);
                    parents.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Path.empty();
    }

    private boolean isWalkable(Coordinates cell, Class<? extends Entity> targetEntityType) {
        return worldMap.isCellEmpty(cell) || isTarget(cell, targetEntityType);
    }

    private boolean isTarget(Coordinates cell, Class<? extends Entity> targetEntityType) {
        return worldMap.getEntity(cell).filter(targetEntityType::isInstance).isPresent();
    }
}
