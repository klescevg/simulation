package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.*;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;

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
    protected void consumeTarget(WorldMap worldMap, Path path) {
        Creature prey = (Creature) worldMap.getEntity(path.target()).orElseThrow();
        prey.takeDamage(attackPower);
        if (!prey.isAlive()) {
            worldMap.removeEntity(path.target());
            worldMap.moveEntity(path.start(), path.target());
            heal(PREY_HEAL);
        }
    }
}
