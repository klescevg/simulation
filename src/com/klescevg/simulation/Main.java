package com.klescevg.simulation;

import com.klescevg.simulation.entities.objects.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        MapImp1 map1 = new MapImp1(10);
        Grass grass = new Grass();
        Rock rock = new Rock();
        Tree tree = new Tree();

        Coordinates grassCoordinates = random(10);
        Coordinates rockCoordinates = random(10);
        Coordinates treeCoordinates = random(10);
        map1.addEntity(grassCoordinates, grass);
        map1.addEntity(rockCoordinates, rock);
        map1.addEntity(treeCoordinates, tree);
        map1.display();
        //map1.clearCell(grassCoordinates1);
        map1.moveEntity(grassCoordinates, random(10));
        map1.display();
    }

    public static Coordinates random(int bound){
        Random random = new Random();
        return new Coordinates(random.nextInt(10), random.nextInt(10));
    }
}