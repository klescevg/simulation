package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.*;

import java.util.*;

public class PathFinder {
    MapImp1 map;

    public PathFinder(MapImp1 map) {
        this.map = map;
    }

    //Проверяет если нужная координата в принципе имеется на карте
    public boolean isReachableBFS(Coordinates startCoordinates, Coordinates goalCoordinates) {
        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()) {
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while (!queue.isEmpty()) {
            Coordinates nodeCoordinates = queue.poll();

            if (nodeCoordinates.equals(goalCoordinates)) {
                return true;
            }

            for (Coordinates child : map.getBorderCells(nodeCoordinates)) {
                if (visitedNodes.get(child).equals(false)) {
                    visitedNodes.put(child, true);
                    queue.add(child);
                }
            }
        }
        return false;
    }

    //Находит координаты ближайшей цели учитывая остальные предметы на пути
    public Coordinates getEntityCoordinatesBFS(Entity startEntity, Entity goalEntity) {
        Coordinates startCoordinates = map.getEntityCoordinates(startEntity);

        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()) {
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while (!queue.isEmpty()) {
            Coordinates nodeCoordinates = queue.poll();

            if (!map.isCellEmpty(nodeCoordinates) && map.getEntity(nodeCoordinates).equals(goalEntity)) {
                return nodeCoordinates;
            }

            for (Coordinates child : map.getBorderCells(nodeCoordinates)) {
                if (visitedNodes.get(child).equals(false) &&
                        (map.isCellEmpty(child) || map.getEntity(child).equals(goalEntity))) {
                    visitedNodes.put(child, true);
                    queue.add(child);
                }
            }
        }
        return null;
    }

    //Возвращает длину пути до ближайшей цели учитывая препятствия
    public int getDistanceToEntityBFS(Entity startEntity, Entity goalEntity) {
        Coordinates startCoordinates = map.getEntityCoordinates(startEntity);
        Map<Coordinates, Integer> dist = new HashMap<>();
        dist.put(startCoordinates, 0);

        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()) {
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while (!queue.isEmpty()) {
            Coordinates nodeCoordinates = queue.poll();

            if (!map.isCellEmpty(nodeCoordinates) && map.getEntity(nodeCoordinates).equals(goalEntity)) {
                return dist.get(nodeCoordinates);
            }

            visitedNodes.put(nodeCoordinates, true);

            for (Coordinates child : map.getBorderCells(nodeCoordinates)) {
                if (visitedNodes.get(child).equals(false) &&
                        (map.isCellEmpty(child) || map.getEntity(child).equals(goalEntity))) {
                    queue.add(child);
                    dist.put(child, dist.get(nodeCoordinates) + 1);
                }
            }
        }
        return -1;
    }

    public Coordinates getNextPosition(Creature startCreature, Entity goalEntity) {
        Stack<Coordinates> path = getPathToEntityBFS(startCreature, goalEntity);

        if (path == null || path.isEmpty()) {
            return null;
        }

        Coordinates nextPosition = path.pop();

        for (int i = 0; i < startCreature.getSpeed(); i++) {

            if (path.isEmpty()) {
                break;
            }

            nextPosition = path.pop();
        }

        return nextPosition;
    }

    public Stack<Coordinates> getPathToEntityBFS(Entity startEntity, Entity goalEntity) {
        Coordinates start = map.getEntityCoordinates(startEntity);
        Coordinates goal = getEntityCoordinatesBFS(startEntity, goalEntity);

        if (goal == null) {
            return null;
        }

        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        Map<Coordinates, Coordinates> parentNodes = new HashMap<>();

        for (Coordinates node : map.getCoordinates()) {
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(start);
        visitedNodes.put(start, true);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (current.equals(goal)) {
                break;
            }

            for (Coordinates neighbor : map.getBorderCells(current)) {
                if (!visitedNodes.get(neighbor) &&  (map.isCellEmpty(neighbor) || neighbor.equals(goal))) {
                    visitedNodes.put(neighbor, true);
                    parentNodes.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
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
}