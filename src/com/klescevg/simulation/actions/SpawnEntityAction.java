package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.Coordinates;
import com.klescevg.simulation.world.CoordinatesGenerator;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.EntityFactory;

import static com.klescevg.simulation.world.CoordinatesGenerator.random;

public class SpawnEntityAction extends Action{
    private EntityFactory factory;

    public SpawnEntityAction(EntityFactory factory){
        this.factory = factory;
    }

    @Override
    public void execute(WorldMap worldMap) {
        Coordinates randomCoordinates = random(worldMap.sideLength());
        while (!worldMap.isCellEmpty(randomCoordinates)) {
            randomCoordinates = random(worldMap.sideLength());
        }
        worldMap.addEntity(randomCoordinates, factory.create());
    }
}
