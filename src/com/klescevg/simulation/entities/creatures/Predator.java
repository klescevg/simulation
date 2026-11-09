package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.WorldMap;

public class Predator extends Creature {
    private static final int PREY_HEAL = 70;
    private final int attackPower;

    public Predator(int healthPoints, int speed, int attackPower) {
        super(healthPoints, speed, Herbivore.class);
        this.attackPower = attackPower;
    }

    @Override
    protected void interactWithTarget(WorldMap map, Path path) {
        Creature prey = (Creature) map.getEntity(path.getTarget()).orElseThrow();
        prey.takeDamage(attackPower);
        if (!prey.isAlive()) {
            map.removeEntity(path.getTarget());
            moveTo(map, path.getTarget());
            heal(PREY_HEAL);
        }
    }
}
