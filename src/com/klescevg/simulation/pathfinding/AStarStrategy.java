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

    public Stack<Coordinates> getPathToTarget(Entity startEntity, Class<? extends Entity> targetEntityType) {
        Coordinates start = worldMap.getEntityCoordinates(startEntity);
        Coordinates target = getClosestTarget(start, targetEntityType);

        if (target != null) {
            Map<Coordinates, Coordinates> parents = searchFromStart(start, target);

            if (parents.containsKey(target)) {
                return reconstructPath(parents, start, target);
            }
        }

        return new Stack<>();
    }

    private Coordinates getClosestTarget(Coordinates start, Class<? extends Entity> targetEntityType) {
        Coordinates target = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Coordinates coordinates : worldMap.getCoordinatesOfType(targetEntityType)) {
            int h = heuristic(start, coordinates);
            if (h < closestDistance) {
                closestDistance = h;
                target = coordinates;
            }
        }
        return target;
    }

    private Map<Coordinates, Coordinates> searchFromStart(Coordinates start, Coordinates target) {
        Queue<PrioritizedCell> queue = new PriorityQueue<>(Comparator.comparingInt(PrioritizedCell::priority));
        Map<Coordinates, Integer> costs = new HashMap<>();
        Map<Coordinates, Coordinates> parents = new HashMap<>();
        Set<Coordinates> processedCells = new HashSet<>();

        costs.put(start, 0);
        queue.add(new PrioritizedCell(start, heuristic(start, target)));

        while (!queue.isEmpty()) {
            PrioritizedCell currentCell = queue.poll();
            Coordinates current = currentCell.coordinates();

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
                    int newCost = costs.get(current) + 1;
                    if (!costs.containsKey(neighbor) || newCost < costs.get(neighbor)) {
                        costs.put(neighbor, newCost);
                        parents.put(neighbor, current);
                        queue.add(new PrioritizedCell(neighbor, newCost + heuristic(neighbor, target)));
                    }
                }
            }
        }

        return parents;
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

    private boolean isWalkable(Coordinates cell, Coordinates target) {
        return worldMap.isCellEmpty(cell) || cell.equals(target);
    }

    private int heuristic(Coordinates a, Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private record PrioritizedCell(Coordinates coordinates, int priority) {

    }
}
