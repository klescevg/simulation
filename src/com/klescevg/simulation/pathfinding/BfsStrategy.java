package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinates;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.Entity;

import java.util.*;

public class BfsStrategy implements PathfindingStrategy{
    private final WorldMap worldMap;

    public BfsStrategy(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Stack<Coordinates> getPathToTarget(Entity startEntity, Class<? extends Entity> targetEntityType) {
        Coordinates start = worldMap.getEntityCoordinates(startEntity);
        Coordinates target = null;

        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> processedCells = new HashSet<>();
        Map<Coordinates, Coordinates> parents = new HashMap<>();

        queue.add(start);
        processedCells.add(start);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (targetEntityType.isInstance(worldMap.getEntity(current))) {
                target = current;
                break;
            }

            for (Coordinates neighbor : worldMap.getBorderCells(current)) {
                if (!processedCells.contains(neighbor)
                        && (worldMap.isCellEmpty(neighbor) || targetEntityType.isInstance(worldMap.getEntity(neighbor)))) {
                    processedCells.add(neighbor);
                    parents.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (target == null){
            return new Stack<>();
        }

        Stack<Coordinates> path = new Stack<>();
        Coordinates current = target;

        while (current != null && !current.equals(start)) {
            path.push(current);
            current = parents.get(current);
        }

        path.push(start);
        return path;
    }

}
