package com.klescevg.simulation.actions;

import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Creature;

public class HungerAction extends Action{
    private static final int HUNGER_DAMAGE = 5;

    @Override
    public void execute(WorldMap worldMap) {
        for (Entity entity : worldMap.getEntities()){
            if (worldMap.getEntityCoordinates(entity) != null) {
                if (entity instanceof Creature) {
                    ((Creature) entity).takeDamage(HUNGER_DAMAGE);
                }
            }
        }
    }
}
