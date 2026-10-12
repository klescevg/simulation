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
    public Stack<Coordinates> findPath(Entity startEntity, Class<? extends Entity> targetEntityType) {
        Coordinates start = worldMap.getEntityCoordinates(startEntity);

        Queue<Coordinates> queue = new ArrayDeque<>();
        Set<Coordinates> visitedCells = new HashSet<>();
        Map<Coordinates, Coordinates> parents = new HashMap<>();

        queue.add(start);
        visitedCells.add(start);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (isTarget(current, targetEntityType)) {
                return reconstructPath(parents, start, current);
            }

            for (Coordinates neighbor : worldMap.getBorderCells(current)) {
                if (!visitedCells.contains(neighbor) && isWalkable(neighbor, targetEntityType)) {
                    visitedCells.add(neighbor);
                    parents.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return new Stack<>();
    }

    private Stack<Coordinates> reconstructPath(Map<Coordinates, Coordinates> parents, Coordinates start, Coordinates target) {
        Stack<Coordinates> path = new Stack<>();
        Coordinates current = target;

        while (current != null && !current.equals(start)) {
            path.push(current);
            current = parents.get(current);
        }

        path.push(start);
        return path;
    }

    private boolean isWalkable(Coordinates cell, Class<? extends Entity> targetEntityType) {
        return worldMap.isCellEmpty(cell) || isTarget(cell, targetEntityType);
    }

    private boolean isTarget(Coordinates cell, Class<? extends Entity> targetEntityType) {
        return targetEntityType.isInstance(worldMap.getEntity(cell));
    }
}
