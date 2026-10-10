package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.world.*;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;

import java.util.Stack;

public class Predator extends Creature {
    private static final int PREY_HEAL = 70;
    private final int attackPower;

    public Predator(int healthPoints, int speed, int attackPower) {
        super(Symbol.P, healthPoints, speed);
        this.attackPower = attackPower;
    }

    @Override
    protected Class<? extends Entity> getTargetType() {
        return Herbivore.class;
    }

    @Override
    protected void consumeTarget(WorldMap worldMap, Stack<Coordinates> path) {
        Creature prey = (Creature) worldMap.getEntity(path.getFirst());
        prey.takeDamage(attackPower);
        if (!prey.isAlive()) {
            worldMap.clearCell(path.getFirst());
            worldMap.moveEntity(path.getLast(), path.getFirst());
            heal(PREY_HEAL);
        }
    }
}
