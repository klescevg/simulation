package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.EntityFactory;

public class SpawnEntityAction extends Action{
    private EntityFactory factory;

    public SpawnEntityAction(EntityFactory factory){
        this.factory = factory;
    }

    @Override
    public void execute(WorldMap worldMap) {
        worldMap.addEntityToRandomPosition(factory.create());
    }
}
