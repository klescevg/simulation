package com.klescevg.simulation;

import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;
import com.klescevg.simulation.entities.objects.*;

public class Main {
    public static void main(String[] args) {
        Map map1 = new Map(10);
        PathFinder pathFinder = new PathFinder(map1);
        Herbivore herbivore1 = new Herbivore(100, 2);
        Herbivore herbivore2 = new Herbivore(100, 2);
        Predator predator = new Predator(100, 3, 50);
        map1.addEntityToRandomPosition(herbivore1);
        map1.addEntityToRandomPosition(herbivore2);
        map1.addEntityToRandomPosition(predator);
        //map1.addEntity(new Coordinates(0,0), herbivore);

        for (int i = 0; i < 5; i++) {
            map1.addEntityToRandomPosition(new Rock());

            map1.addEntityToRandomPosition(new Tree());

            map1.addEntityToRandomPosition(new Grass());
        }

        //map1.addEntity(new Coordinates(3,2), new Grass());
        //map1.addEntityToRandomPosition(new Grass());
        //map1.addEntityToRandomPosition(new Grass());

        map1.display();
        //Coordinates goal = pathFinder.getEntityCoordinates(herbivore, new Grass());
        //System.out.println(goal);
        //System.out.println(pathFinder.getDistanceToEntity(herbivore, new Grass()));
        //System.out.println(pathFinder.getPrevNode(map1.getEntityCoordinates(herbivore), goal));
        //System.out.println(pathFinder.getPathToEntityBFS(herbivore, new Grass()));
        //System.out.println(pathFinder.getNextPosition(herbivore, new Grass()));

/*        while (map1.getEntities().contains(new Grass())) {
            herbivore.makeMove(map1, pathFinder);
            predator.makeMove(map1, pathFinder);
            map1.display();
        }*/
        for (int i = 1; i <= 5; i++) {
            System.out.println("Day " + i + ":");
            if (map1.getEntityCoordinates(herbivore1) != null) {
                herbivore1.makeMove(map1, pathFinder);
            }

            if (map1.getEntityCoordinates(herbivore2) != null) {
                herbivore2.makeMove(map1, pathFinder);
            }
            predator.makeMove(map1, pathFinder);
            map1.display();
        }
    }
}