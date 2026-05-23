package com.klescevg.simulation.actions;

import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.factories.EntityFactory;

import java.util.Map;

public class SpawnEntitiesAction extends Action{
    private Map<EntityFactory, Integer> entityCounts;

    public SpawnEntitiesAction(Map<EntityFactory, Integer> entityCounts){
        this.entityCounts = entityCounts;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (EntityFactory e : entityCounts.keySet()){
            for (int i = 0; i < entityCounts.get(e); i++) {
                worldMap.addEntityToRandomPosition(e.create());
            }
        }
    }
}
