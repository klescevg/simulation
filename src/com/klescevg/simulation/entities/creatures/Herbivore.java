package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.environment.Grass;

public class Herbivore extends Creature {
    private static final int HEALTH_POINTS = 100;
    private static final int SPEED = 1;
    private static final Class<? extends Entity> FOOD = Grass.class;
    private static final int FOOD_HEAL = 30;

    public Herbivore() {
        super(HEALTH_POINTS, SPEED, FOOD);
    }

    @Override
    protected void interactWithTarget(WorldMap map, Path path) {
        map.removeEntity(path.getTarget());
        moveTo(map, path.getTarget());
        heal(FOOD_HEAL);
    }
}
