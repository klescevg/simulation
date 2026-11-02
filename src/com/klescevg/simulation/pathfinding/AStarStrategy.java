package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.entities.Entity;

import java.util.*;

import static com.klescevg.simulation.world.WorldMapUtils.getNeighbors;

public class AStarStrategy implements PathfindingStrategy {
    @Override
    public Path find(WorldMap map, Coordinate start, Class<? extends Entity> targetType) {
        Coordinate target = findClosestTarget(map, start, targetType);

        if (target != null) {
            Map<Coordinate, Coordinate> parents = buildParentsMap(map, start, target);

            if (parents.containsKey(target)) {
                return Path.reconstruct(parents, start, target);
            }
        }

        return Path.empty();
    }

    private Coordinate findClosestTarget(WorldMap map, Coordinate start, Class<? extends Entity> targetType) {
        Coordinate target = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Coordinate coordinate : getCoordinatesOfType(map, targetType)) {
            int distance = calculateManhattanDistance(start, coordinate);
            if (distance < closestDistance) {
                closestDistance = distance;
                target = coordinate;
            }
        }
        return target;
    }

    private Map<Coordinate, Coordinate> buildParentsMap(WorldMap map, Coordinate start, Coordinate target) {
        Queue<PrioritizedCell> queue = new PriorityQueue<>(Comparator.comparingInt(PrioritizedCell::priority));
        Map<Coordinate, Integer> costFromStart = new HashMap<>();
        Map<Coordinate, Coordinate> parents = new HashMap<>();
        Set<Coordinate> processedCells = new HashSet<>();

        costFromStart.put(start, 0);
        queue.add(new PrioritizedCell(start, calculateManhattanDistance(start, target)));

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll().coordinate();

            if (!processedCells.contains(current)) {
                processedCells.add(current);
            } else {
                continue;
            }

            if (current.equals(target)) {
                break;
            }
            for (Coordinate neighbor : getNeighbors(map, current)) {
                if (isWalkable(map, neighbor, target) && !processedCells.contains(neighbor)) {
                    int newCost = costFromStart.get(current) + 1;
                    if (!costFromStart.containsKey(neighbor) || newCost < costFromStart.get(neighbor)) {
                        costFromStart.put(neighbor, newCost);
                        parents.put(neighbor, current);
                        queue.add(new PrioritizedCell(neighbor, newCost + calculateManhattanDistance(neighbor, target)));
                    }
                }
            }
        }

        return parents;
    }

    private boolean isWalkable(WorldMap map, Coordinate cell, Coordinate target) {
        return map.isCellEmpty(cell) || cell.equals(target);
    }

    private int calculateManhattanDistance(Coordinate from, Coordinate to) {
        return Math.abs(from.row() - to.row()) + Math.abs(from.column() - to.column());
    }

    private Set<Coordinate> getCoordinatesOfType(WorldMap map, Class<?> type) {
        Set<Coordinate> result = new HashSet<>();
        for (Map.Entry<Coordinate, Entity> entry : map.getEntities().entrySet()) {
            if (type.isInstance(entry.getValue())) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private record PrioritizedCell(Coordinate coordinate, int priority) {

    }
}
