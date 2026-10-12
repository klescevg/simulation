package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinates;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.Entity;

import java.util.*;

public class AStarStrategy implements PathfindingStrategy {
    private final WorldMap worldMap;

    public AStarStrategy(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public Path findPath(Entity startEntity, Class<? extends Entity> targetEntityType) {
        Coordinates start = worldMap.getEntityCoordinates(startEntity);
        Coordinates target = findNearestTarget(start, targetEntityType);

        if (target != null) {
            Map<Coordinates, Coordinates> parents = buildParentsMap(start, target);

            if (parents.containsKey(target)) {
                return Path.fromParents(parents, start, target);
            }
        }

        return Path.empty();
    }

    private Coordinates findNearestTarget(Coordinates start, Class<? extends Entity> targetEntityType) {
        Coordinates target = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Coordinates coordinates : worldMap.getCoordinatesOfType(targetEntityType)) {
            int distance = heuristic(start, coordinates);
            if (distance < closestDistance) {
                closestDistance = distance;
                target = coordinates;
            }
        }
        return target;
    }

    private Map<Coordinates, Coordinates> buildParentsMap(Coordinates start, Coordinates target) {
        Queue<PrioritizedCell> queue = new PriorityQueue<>(Comparator.comparingInt(PrioritizedCell::priority));
        Map<Coordinates, Integer> costFromStart = new HashMap<>();
        Map<Coordinates, Coordinates> parents = new HashMap<>();
        Set<Coordinates> processedCells = new HashSet<>();

        costFromStart.put(start, 0);
        queue.add(new PrioritizedCell(start, heuristic(start, target)));

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll().coordinates();

            if (!processedCells.contains(current)) {
                processedCells.add(current);
            } else {
                continue;
            }

            if (current.equals(target)) {
                break;
            }
            for (Coordinates neighbor : worldMap.getBorderCells(current)) {
                if (isWalkable(neighbor, target) && !processedCells.contains(neighbor)) {
                    int newCost = costFromStart.get(current) + 1;
                    if (!costFromStart.containsKey(neighbor) || newCost < costFromStart.get(neighbor)) {
                        costFromStart.put(neighbor, newCost);
                        parents.put(neighbor, current);
                        queue.add(new PrioritizedCell(neighbor, newCost + heuristic(neighbor, target)));
                    }
                }
            }
        }

        return parents;
    }

    private boolean isWalkable(Coordinates cell, Coordinates target) {
        return worldMap.isCellEmpty(cell) || cell.equals(target);
    }

    private int heuristic(Coordinates a, Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private record PrioritizedCell(Coordinates coordinates, int priority) {

    }
}
