package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Coordinates;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.objects.Grass;

import java.util.Stack;

public class Herbivore extends Creature {
    public Herbivore(){
        super(Symbol.H, 0, 0);
    }

    public Herbivore(int healthPoints, int speed){
        super(Symbol.H, healthPoints, speed);
    }

    @Override
    public void makeMove(WorldMap worldMap, PathFinder pathFinder) {
        Stack<Coordinates> path = pathFinder.getPathToEntityBFS(this, Grass.class);

        if (path != null && !path.isEmpty()){
            if (path.size() - 1 <= speed){
                eatGrass(worldMap, path);
            } else {
                moveToGrass(worldMap, path);
            }
        }
    }

    private void eatGrass(WorldMap worldMap, Stack<Coordinates> path){
        worldMap.clearCell(path.getFirst());
        worldMap.moveEntity(path.getLast(), path.getFirst());
    }

    private void moveToGrass(WorldMap worldMap, Stack<Coordinates> path){
        Coordinates initialPosition = path.pop();
        Coordinates nextPosition = null;

        for (int i = 0; i < speed && !path.isEmpty(); i++) {
            nextPosition = path.pop();
        }

        worldMap.moveEntity(initialPosition, nextPosition);
    }
}
