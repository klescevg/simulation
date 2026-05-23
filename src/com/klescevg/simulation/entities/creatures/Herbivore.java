package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Coordinates;
import com.klescevg.simulation.Map;
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
    public void makeMove(Map map, PathFinder pathFinder) {
        Stack<Coordinates> path = pathFinder.getPathToEntityBFS(this, new Grass());

        if (path != null && !path.isEmpty()){
            if (path.size() - 1 <= speed){
                eatGrass(map, path);
            } else {
                moveToGrass(map, path);
            }
        }
    }

    private void eatGrass(Map map, Stack<Coordinates> path){
        map.clearCell(path.getFirst());
        map.moveEntity(path.getLast(), path.getFirst());
    }

    private void moveToGrass(Map map, Stack<Coordinates> path){
        Coordinates initialPosition = path.pop();
        Coordinates nextPosition = null;

        for (int i = 0; i < speed && !path.isEmpty(); i++) {
            nextPosition = path.pop();
        }

        map.moveEntity(initialPosition, nextPosition);
    }
}
