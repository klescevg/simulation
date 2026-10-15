package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinate;
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
        Coordinate start = worldMap.getCoordinate(startEntity)
                .orElseThrow(() -> new IllegalStateException("Start entity is not found on map"));
        Coordinate target = findTarget(start, targetEntityType);

        if (target != null) {
            Map<Coordinate, Coordinate> parents = buildParentsMap(start, target);

            if (parents.containsKey(target)) {
                return Path.fromParents(parents, start, target);
            }
        }

        return Path.empty();
    }

    private Coordinate findTarget(Coordinate start, Class<? extends Entity> targetEntityType) {
        Coordinate target = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Coordinate coordinate : worldMap.getCoordinatesOfType(targetEntityType)) {
            int distance = heuristic(start, coordinate);
            if (distance < closestDistance) {
                closestDistance = distance;
                target = coordinate;
            }
        }
        return target;
    }

    private Map<Coordinate, Coordinate> buildParentsMap(Coordinate start, Coordinate target) {
        Queue<PrioritizedCell> queue = new PriorityQueue<>(Comparator.comparingInt(PrioritizedCell::priority));
        Map<Coordinate, Integer> costFromStart = new HashMap<>();
        Map<Coordinate, Coordinate> parents = new HashMap<>();
        Set<Coordinate> processedCells = new HashSet<>();

        costFromStart.put(start, 0);
        queue.add(new PrioritizedCell(start, heuristic(start, target)));

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
            for (Coordinate neighbor : worldMap.getBorderCells(current)) {
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

    private boolean isWalkable(Coordinate cell, Coordinate target) {
        return worldMap.isCellEmpty(cell) || cell.equals(target);
    }

    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.column() - b.column());
    }

    private record PrioritizedCell(Coordinate coordinate, int priority) {

    }
}
