package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.environment.Grass;
import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.WorldMap;

public class Predator extends Creature {
    private static final int HEALTH_POINTS = 100;
    private static final int SPEED = 2;
    private static final int ATTACK_POWER = 25;
    private static final Class<? extends Entity> FOOD = Herbivore.class;
    private static final int FOOD_HEAL = 70;
    private final int attackPower;

    public Predator() {
        super(HEALTH_POINTS, SPEED, FOOD);
        this.attackPower = ATTACK_POWER;
    }

    @Override
    protected void interactWithTarget(WorldMap map, Path path) {
        Creature prey = (Creature) map.getEntity(path.getTarget()).orElseThrow();
        prey.takeDamage(attackPower);
        if (!prey.isAlive()) {
            map.removeEntity(path.getTarget());
            moveTo(map, path.getTarget());
            heal(FOOD_HEAL);
        }
    }
}
