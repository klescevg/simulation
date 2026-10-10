package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.*;

import java.util.Map;

public class SpawnEntitiesAction extends Action{
    private Map<EntityFactory, Integer> entityCounts;

    public SpawnEntitiesAction(Map<EntityFactory, Integer> entityCounts){
        this.entityCounts = entityCounts;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (EntityFactory factory : entityCounts.keySet()){
            for (int i = 0; i < entityCounts.get(factory); i++) {
                worldMap.addEntityToRandomPosition(factory.create());
            }
        }
    }
}
