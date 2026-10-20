package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Creature;

import static com.klescevg.simulation.world.WorldMapUtils.getCreatures;

public class RemoveDeadCreaturesAction implements Action {
    @Override
    public void execute(WorldMap map) {
        for (Creature creature : getCreatures(map)) {
            if (!creature.isAlive()) {
                map.getCoordinate(creature).ifPresent(map::removeEntity);
            }
        }
    }
}
