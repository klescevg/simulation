package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Creature;

public class RemoveDeadCreaturesAction extends Action {
    @Override
    public void execute(WorldMap map) {
        for (Creature creature : map.getEntitiesOfType(Creature.class)) {
            if (!creature.isAlive()) {
                map.removeEntity(map.getCoordinates(creature));
            }
        }
    }
}
