package com.klescevg.simulation;

import com.klescevg.simulation.entities.objects.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Map map = new Map(10);
        Grass grass = new Grass();
        Rock rock = new Rock();
        Tree tree = new Tree();

        Coordinates grassCoordinates = random(10);
        Coordinates rockCoordinates = random(10);
        Coordinates treeCoordinates = random(10);
        map.addEntity(grassCoordinates, grass);
        map.addEntity(rockCoordinates, rock);
        map.addEntity(treeCoordinates, tree);
        map.display();
        //map.clearCell(grassCoordinates1);
        map.moveEntity(grassCoordinates, random(10));
        map.display();
    }

    public static Coordinates random(int bound){
        Random random = new Random();
        return new Coordinates(random.nextInt(10), random.nextInt(10));
    }
}