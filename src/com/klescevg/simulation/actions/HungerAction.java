package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Creature;

import static com.klescevg.simulation.world.WorldMapUtils.getCreatures;

public class HungerAction implements Action {
    private static final int HUNGER_DAMAGE = 5;

    @Override
    public void execute(WorldMap map) {
        for (Creature creature : getCreatures(map)) {
            creature.takeDamage(HUNGER_DAMAGE);
        }
    }
}
