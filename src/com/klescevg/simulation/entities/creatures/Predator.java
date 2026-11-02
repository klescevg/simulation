package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.Entity;

public class Predator extends Creature {
    private static final int PREY_HEAL = 70;
    private final int attackPower;

    public Predator(int healthPoints, int speed, int attackPower) {
        super(healthPoints, speed);
        this.attackPower = attackPower;
    }

    @Override
    protected Class<? extends Entity> getTargetType() {
        return Herbivore.class;
    }

    @Override
    protected void interactWithTarget(WorldMap map, Path path) {
        Creature prey = (Creature) map.getEntity(path.getTarget()).orElseThrow();
        prey.takeDamage(attackPower);
        if (!prey.isAlive()) {
            map.removeEntity(path.getTarget());
            map.moveEntity(path.getStart(), path.getTarget());
            heal(PREY_HEAL);
        }
    }
}
