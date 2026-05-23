package com.klescevg.simulation;

import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;
import com.klescevg.simulation.entities.objects.*;

public class Main {
    public static void main(String[] args) {
        WorldMap worldMap1 = new WorldMap(10);
        WorldMapConsoleRenderer worldMapConsoleRenderer = new WorldMapConsoleRenderer(worldMap1);
        PathFinder pathFinder = new PathFinder(worldMap1);
        Herbivore herbivore1 = new Herbivore(100, 2);
        Herbivore herbivore2 = new Herbivore(100, 2);
        Predator predator = new Predator(100, 3, 50);
        worldMap1.addEntityToRandomPosition(herbivore1);
        worldMap1.addEntityToRandomPosition(herbivore2);
        worldMap1.addEntityToRandomPosition(predator);
        //worldMap1.addEntity(new Coordinates(0,0), herbivore);

        for (int i = 0; i < 5; i++) {
            worldMap1.addEntityToRandomPosition(new Rock());

            worldMap1.addEntityToRandomPosition(new Tree());

            worldMap1.addEntityToRandomPosition(new Grass());
        }

        //worldMap1.addEntity(new Coordinates(3,2), new Grass());
        //worldMap1.addEntityToRandomPosition(new Grass());
        //worldMap1.addEntityToRandomPosition(new Grass());

        worldMapConsoleRenderer.render();
        //Coordinates goal = pathFinder.getEntityCoordinates(herbivore, new Grass());
        //System.out.println(goal);
        //System.out.println(pathFinder.getDistanceToEntity(herbivore, new Grass()));
        //System.out.println(pathFinder.getPrevNode(worldMap1.getEntityCoordinates(herbivore), goal));
        //System.out.println(pathFinder.getPathToEntityBFS(herbivore, new Grass()));
        //System.out.println(pathFinder.getNextPosition(herbivore, new Grass()));

/*        while (worldMap1.getEntities().contains(new Grass())) {
            herbivore.makeMove(worldMap1, pathFinder);
            predator.makeMove(worldMap1, pathFinder);
            worldMap1.display();
        }*/
        for (int i = 1; i <= 5; i++) {
            System.out.println("Day " + i + ":");
            if (worldMap1.getEntityCoordinates(herbivore1) != null) {
                herbivore1.makeMove(worldMap1, pathFinder);
            }

            if (worldMap1.getEntityCoordinates(herbivore2) != null) {
                herbivore2.makeMove(worldMap1, pathFinder);
            }
            predator.makeMove(worldMap1, pathFinder);
            worldMapConsoleRenderer.render();
        }
    }
}