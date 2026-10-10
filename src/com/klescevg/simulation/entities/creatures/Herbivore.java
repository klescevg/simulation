package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Coordinates;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.objects.Grass;

import java.util.Stack;

public class Herbivore extends Creature {
    private static final int GRASS_HEAL = 30;

    public Herbivore(int healthPoints, int speed) {
        super(Symbol.H, healthPoints, speed);
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Grass.class;
    }

    @Override
    protected void consumeTarget(WorldMap worldMap, Stack<Coordinates> path) {
        worldMap.clearCell(path.getFirst());
        worldMap.moveEntity(path.getLast(), path.getFirst());
        heal(GRASS_HEAL);
    }
}
