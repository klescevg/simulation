package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.*;

import java.util.Map;

public class SpawnEntitiesAction extends Action {
    private final Map<EntityFactory, Integer> spawnCounts;

    public SpawnEntitiesAction(Map<EntityFactory, Integer> spawnCounts) {
        this.spawnCounts = spawnCounts;
    }

    @Override
    public void execute(WorldMap map) {
        for (EntityFactory factory : spawnCounts.keySet()) {
            SpawnEntityAction action = new SpawnEntityAction(factory);
            for (int i = 0; i < spawnCounts.get(factory); i++) {
                action.execute(map);
            }
        }
    }
}
