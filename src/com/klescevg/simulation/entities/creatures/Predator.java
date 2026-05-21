package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Coordinates;
import com.klescevg.simulation.MapImp1;
import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.objects.Grass;

import java.util.Stack;

public class Predator extends Creature {
    private int attackPower;

    public Predator(int healthPoints, int speed, int attackPower){
        super(Symbol.P, healthPoints, speed);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(MapImp1 map, PathFinder pathFinder) {
        Stack<Coordinates> path = pathFinder.getPathToEntityBFS(this, new Herbivore());

        if (path != null && !path.isEmpty()){
            if (path.size() - 1 <= speed){
                attackPrey(map, path);
            } else {
                moveToPrey(map, path);
            }
        }
    }

    private void attackPrey(MapImp1 map, Stack<Coordinates> path){
        Creature prey = (Creature) map.getEntity(path.getLast());
        if (prey.healthPoints <= attackPower){
            map.clearCell(path.getFirst());
            map.moveEntity(path.getLast(), path.getFirst());
        } else {
            prey.setHealthPoints(prey.healthPoints - attackPower);
            map.moveEntity(path.getLast(), path.get(1));
        }
    }

    private void moveToPrey(MapImp1 map, Stack<Coordinates> path){
        Coordinates initialPosition = path.pop();
        Coordinates nextPosition = null;

        for (int i = 0; i < speed && !path.isEmpty(); i++) {
            nextPosition = path.pop();
        }

        map.moveEntity(initialPosition, nextPosition);
    }
}
