package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.WorldMapUtils;
import com.klescevg.simulation.entities.creatures.Creature;

public class RemoveDeadCreaturesAction implements Action {
    @Override
    public void execute(WorldMap map) {
        for (Creature creature : WorldMapUtils.getCreatures(map)) {
            if (!creature.isAlive()) {
                map.getCoordinate(creature).ifPresent(map::removeEntity);
            }
        }
    }
}
