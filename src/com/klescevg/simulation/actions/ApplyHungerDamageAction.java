package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.WorldMapUtils;
import com.klescevg.simulation.entities.creatures.Creature;

public class ApplyHungerDamageAction implements Action {
    private static final int HUNGER_DAMAGE = 5;

    @Override
    public void execute(WorldMap map) {
        for (Creature creature : WorldMapUtils.getEntitiesOfType(map, Creature.class)) {
            creature.takeDamage(HUNGER_DAMAGE);
        }
    }
}
