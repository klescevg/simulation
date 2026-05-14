package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.objects.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
/*        //MapImp1 map1 = new MapImp1(10);
        MapImp2 map2 = new MapImp2(10);
        Grass grass = new Grass();
        Rock rock = new Rock();
        Tree tree = new Tree();

        Coordinates grassCoordinates = random(10);
        Coordinates rockCoordinates = random(10);
        Coordinates treeCoordinates = random(10);
*//*        map1.addEntity(grassCoordinates, grass);
        map1.addEntity(rockCoordinates, rock);
        map1.addEntity(treeCoordinates, tree);
        map1.display();
        //map1.clearCell(grassCoordinates1);
        map1.moveEntity(grassCoordinates, random(10));
        map1.display();*//*
        map2.addEntity(grass, grassCoordinates);
        map2.addEntity(rock, rockCoordinates);
        map2.addEntity(tree, treeCoordinates);
        map2.display();
        map2.moveEntity(grass, random(10));
        map2.display();

        Entity e = new Tree();
        System.out.println(e.getClass());*/

        MapImp1 map1 = new MapImp1(3);
        Rock rock = new Rock();
        Grass grass = new Grass();
        Grass grass1 = new Grass();
        Grass grass2 = new Grass();
        Herbivore herbivore = new Herbivore(100, 2);

        map1.addEntity(new Coordinates(0,0), herbivore);
        //map1.addEntity(new Coordinates(0,1), rock);
        map1.addEntity(new Coordinates(1,0), rock);
        map1.addEntity(new Coordinates(1,1), rock);
        //map1.addEntity(new Coordinates(1,2), rock);
        // map1.addEntity(new Coordinates(2,2), grass1);
        map1.addEntity(new Coordinates(2,1), grass2);
        PathFinder pathFinder = new PathFinder(map1);
        //System.out.println(pathFinder.isReachableBFS(new Coordinates(0,0), new Coordinates(4,4)));
        //System.out.println(map1.getEntity(new Coordinates(6, 6)));
        //System.out.println(pathFinder.getEntityCoordinatesBFS(rock, grass));
        //System.out.println(pathFinder.getEntityCoordinates2BFS(herbivore, grass));
        System.out.println(pathFinder.getDistanceToEntityBFS(herbivore, grass2));
        map1.display();
    }

    public static Coordinates random(int bound){
        Random random = new Random();
        return new Coordinates(random.nextInt(10), random.nextInt(10));
    }
}