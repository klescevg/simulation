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
    public void execute(WorldMap map) {
        Coordinates randomCoordinates = random(map.sideLength());
        while (!map.isCellEmpty(randomCoordinates)) {
            randomCoordinates = random(map.sideLength());
        }
        map.addEntity(randomCoordinates, factory.create());
    }
}
