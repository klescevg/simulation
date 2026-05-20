package com.klescevg.simulation;

import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.objects.*;

public class Main {
    public static void main(String[] args) {
        MapImp1 map1 = new MapImp1(5);
        PathFinder pathFinder = new PathFinder(map1);
        Herbivore herbivore = new Herbivore(100, 2);
        map1.addEntityToRandomPosition(herbivore);
        //map1.addEntity(new Coordinates(0,0), herbivore);

        for (int i = 0; i < 4; i++) {
            map1.addEntityToRandomPosition(new Rock());

            map1.addEntityToRandomPosition(new Tree());

            //map1.addEntityToRandomPosition(new Grass());
        }

        //map1.addEntity(new Coordinates(3,2), new Grass());
        map1.addEntityToRandomPosition(new Grass());
        map1.addEntityToRandomPosition(new Grass());

        map1.display();
        Coordinates goal = pathFinder.getEntityCoordinates(herbivore, new Grass());
        System.out.println(goal);
        System.out.println(pathFinder.getDistanceToEntity(herbivore, new Grass()));
        //System.out.println(pathFinder.getPrevNode(map1.getEntityCoordinates(herbivore), goal));
        System.out.println(pathFinder.getPathToEntityBFS(herbivore, new Grass()));
        System.out.println(pathFinder.getNextPosition(herbivore, new Grass()));
    }
}