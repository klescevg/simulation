package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.EntityFactory;

import static com.klescevg.simulation.world.CoordinateGenerator.random;

public class SpawnEntityAction extends Action{
    private EntityFactory factory;

    public SpawnEntityAction(EntityFactory factory){
        this.factory = factory;
    }

    @Override
    public void execute(WorldMap map) {
        Coordinate randomCoordinate = random(map.sideLength());
        while (!map.isCellEmpty(randomCoordinate)) {
            randomCoordinate = random(map.sideLength());
        }
        map.addEntity(randomCoordinate, factory.create());
    }
}
