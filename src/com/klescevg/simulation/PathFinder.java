package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Herbivore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PathFinder {
    MapImp1 map;

    public PathFinder(MapImp1 map){
        this.map = map;
    }
    //public Entity findClosest(Coordinates c, EntityType)
    //public int calculateDistance(Coordinate c1, Coordinate c2);
    //public int calculateDistance(Entity e1, Coordinate c2);
    //public int calculateDistance(Entity e1, Entity e2);
    //public Coordinates getNextPosition(Coordinate c1, Coordinate c2);

    public Entity findClosest(Entity entity){
        Coordinates entityPosition = map.getEntityCoordinates(entity);
        if (entity.getClass() == Herbivore.class){

        }
        return null;
    }

    //По сути проверяет если нужная координата в принципе имеется на карте
    public boolean isReachableBFS(Coordinates startCoordinates, Coordinates goalCoordinates){
        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()){
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while(!queue.isEmpty()){
            Coordinates nodeCoordinates = queue.poll();

            if(nodeCoordinates.equals(goalCoordinates)){
                return true;
            }

            visitedNodes.put(nodeCoordinates, true);

            for (Coordinates child : map.getBorderCells(nodeCoordinates)){
                if (visitedNodes.get(child).equals(false)){
                    queue.add(child);
                    visitedNodes.put(child, true);
                }
            }
        }
        return false;
    }

    //Находит координаты ближайшей цели, игнорирует остальные предметы на пути
    public Coordinates getEntityCoordinatesBFS(Entity startEntity, Entity goalEntity){
        Coordinates startCoordinates = map.getEntityCoordinates(startEntity);

        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()){
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while(!queue.isEmpty()){
            Coordinates nodeCoordinates = queue.poll();

            if (!map.isCellEmpty(nodeCoordinates) && map.getEntity(nodeCoordinates).equals(goalEntity)){
                return nodeCoordinates;
            }

            visitedNodes.put(nodeCoordinates, true);

            for (Coordinates child : map.getBorderCells(nodeCoordinates)){
                if (visitedNodes.get(child).equals(false)){
                    queue.add(child);
                    visitedNodes.put(child, true);
                }
            }
        }
        return null;
    }

    //Находит координаты ближайшей цели учитывая остальные предметы на пути
    public Coordinates getEntityCoordinates2BFS(Entity startEntity, Entity goalEntity){
        Coordinates startCoordinates = map.getEntityCoordinates(startEntity);

        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()){
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while(!queue.isEmpty()){
            Coordinates nodeCoordinates = queue.poll();

            if (!map.isCellEmpty(nodeCoordinates) && map.getEntity(nodeCoordinates).equals(goalEntity)){
                return nodeCoordinates;
            }

            visitedNodes.put(nodeCoordinates, true);

            for (Coordinates child : map.getBorderCells(nodeCoordinates)){
                if (visitedNodes.get(child).equals(false) &&
                        (map.isCellEmpty(child) || map.getEntity(child).equals(goalEntity))){
                    queue.add(child);
                    visitedNodes.put(child, true);
                }
            }
        }
        return null;
    }

    //Возвращает длину пути до ближайшей цели учитывая препятствия
    public int getDistanceToEntityBFS(Entity startEntity, Entity goalEntity){
        Coordinates startCoordinates = map.getEntityCoordinates(startEntity);
        //int distanceToGoal = 0;
        Map<Coordinates, Integer> dist = new HashMap<>();
        dist.put(startCoordinates, 0);

        Map<Coordinates, Boolean> visitedNodes = new HashMap<>();
        for (Coordinates node : map.getCoordinates()){
            visitedNodes.put(node, false);
        }

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(startCoordinates);

        while(!queue.isEmpty()){
            Coordinates nodeCoordinates = queue.poll();

            if (!map.isCellEmpty(nodeCoordinates) && map.getEntity(nodeCoordinates).equals(goalEntity)){
                return dist.get(nodeCoordinates);
            }

            visitedNodes.put(nodeCoordinates, true);

            for (Coordinates child : map.getBorderCells(nodeCoordinates)){
                if (visitedNodes.get(child).equals(false) &&
                        (map.isCellEmpty(child) || map.getEntity(child).equals(goalEntity))){
                    queue.add(child);
                    visitedNodes.put(child, true);
                    dist.put(child, dist.get(nodeCoordinates) + 1);
                }
            }
        }
        return 0;
    }
}
