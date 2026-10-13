package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Creature;

public class HungerAction extends Action{
    private static final int HUNGER_DAMAGE = 5;

    @Override
    public void execute(WorldMap map) {
        for (Creature creature : map.getEntitiesOfType(Creature.class)){
            creature.takeDamage(HUNGER_DAMAGE);
        }
    }
}
