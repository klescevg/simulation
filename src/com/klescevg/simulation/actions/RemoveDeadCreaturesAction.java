package com.klescevg.simulation.actions;

import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Creature;

public class RemoveDeadCreaturesAction extends Action {
    @Override
    public void execute(WorldMap worldMap) {
        for (Entity entity : worldMap.getEntities()){
            if (worldMap.getEntityCoordinates(entity) != null) {
                if ((entity instanceof Creature) && !((Creature) entity).isAlive()) {
                    worldMap.clearCell(worldMap.getEntityCoordinates(entity));
                }
            }
        }
    }
}
