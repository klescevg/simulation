package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Creature;

public class RemoveDeadCreaturesAction extends Action {
    @Override
    public void execute(WorldMap worldMap) {
        for (Creature creature : worldMap.getEntitiesOfType(Creature.class)) {
            if (!creature.isAlive()) {
                worldMap.removeEntity(worldMap.getEntityCoordinates(creature));
            }
        }
    }
}
