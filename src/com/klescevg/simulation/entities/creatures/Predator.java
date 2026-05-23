package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Coordinates;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.Symbol;

import java.util.Stack;

public class Predator extends Creature {
    private final int attackPower;

    public Predator(int healthPoints, int speed, int attackPower){
        super(Symbol.P, healthPoints, speed);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(WorldMap worldMap, PathFinder pathFinder) {
        Stack<Coordinates> path = pathFinder.getPathToEntityBFS(this, new Herbivore());

        if (path != null && !path.isEmpty()){
            if (path.size() - 1 <= speed){
                attackPrey(worldMap, path);
            } else {
                moveToPrey(worldMap, path);
            }
        }
    }

    private void attackPrey(WorldMap worldMap, Stack<Coordinates> path){
        Creature prey = (Creature) worldMap.getEntity(path.getLast());
        if (prey.healthPoints <= attackPower){
            worldMap.clearCell(path.getFirst());
            worldMap.moveEntity(path.getLast(), path.getFirst());
        } else {
            prey.setHealthPoints(prey.healthPoints - attackPower);
            worldMap.moveEntity(path.getLast(), path.get(1));
        }
    }

    private void moveToPrey(WorldMap worldMap, Stack<Coordinates> path){
        Coordinates initialPosition = path.pop();
        Coordinates nextPosition = null;

        for (int i = 0; i < speed && !path.isEmpty(); i++) {
            nextPosition = path.pop();
        }

        worldMap.moveEntity(initialPosition, nextPosition);
    }
}
