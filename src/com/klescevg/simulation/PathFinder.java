package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.*;

import java.util.*;

public class PathFinder {
    private WorldMap worldMap;

    public PathFinder(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Stack<Coordinates> getPathToEntityBFS(Entity startEntity, Entity goalEntity) {
        Coordinates start = worldMap.getEntityCoordinates(startEntity);
        Coordinates goal = null;

        Set<Coordinates> visitedNodes = new HashSet<>();
        java.util.Map<Coordinates, Coordinates> parentNodes = new HashMap<>();

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(start);
        visitedNodes.add(start);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (!worldMap.isCellEmpty(current) && worldMap.getEntity(current).equals(goalEntity)) {
                goal = current;
                break;
            }

            for (Coordinates neighbor : worldMap.getBorderCells(current)) {
                if (!visitedNodes.contains(neighbor)
                        && (worldMap.isCellEmpty(neighbor) || worldMap.getEntity(neighbor).equals(goalEntity))) {
                    visitedNodes.add(neighbor);
                    parentNodes.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        if (goal == null){
            return new Stack<>();
        }

        Stack<Coordinates> path = new Stack<>();
        Coordinates current = goal;

        while (current != null && !current.equals(start)) {
            path.push(current);
            current = parentNodes.get(current);
        }

        path.push(start);
        return path;
    }

    public Coordinates getEntityCoordinates(Entity startEntity, Entity goalEntity) {
        Stack<Coordinates> path = getPathToEntityBFS(startEntity, goalEntity);

        if (path == null || path.isEmpty()) {
            return null;
        }

        return path.firstElement();
    }

    public int getDistanceToEntity(Entity startEntity, Entity goalEntity) {
        Stack<Coordinates> path = getPathToEntityBFS(startEntity, goalEntity);

        if (path == null || path.isEmpty()) {
            return 0;
        }

        return path.size() - 1;
    }

    public Coordinates getNextPosition(Creature startCreature, Entity goalEntity) {
        Stack<Coordinates> path = getPathToEntityBFS(startCreature, goalEntity);

        if (path == null || path.isEmpty()) {
            return null;
        }

        Coordinates nextPosition = path.pop();

        for (int i = 0; i < startCreature.getSpeed() && !path.isEmpty(); i++) {
            nextPosition = path.pop();
        }
        return nextPosition;
    }
}