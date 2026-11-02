package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.environment.Grass;

public class Herbivore extends Creature {
    private static final int GRASS_HEAL = 30;

    public Herbivore(int healthPoints, int speed) {
        super(healthPoints, speed);
    }

    @Override
    protected Class<? extends Entity> getTargetType(){
        return Grass.class;
    }

    @Override
    protected void interactWithTarget(WorldMap map, Path path) {
        map.removeEntity(path.getTarget());
        map.moveEntity(path.getStart(), path.getTarget());
        heal(GRASS_HEAL);
    }
}
