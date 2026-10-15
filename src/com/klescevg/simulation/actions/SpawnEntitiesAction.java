package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.*;

import java.util.Map;

import static com.klescevg.simulation.world.CoordinateGenerator.random;

public class SpawnEntitiesAction extends Action {
    private Map<EntityFactory, Integer> entityCounts;

    public SpawnEntitiesAction(Map<EntityFactory, Integer> entityCounts) {
        this.entityCounts = entityCounts;
    }

    @Override
    public void execute(WorldMap map) {
        for (EntityFactory factory : entityCounts.keySet()) {
            for (int i = 0; i < entityCounts.get(factory); i++) {
                Coordinate randomCoordinate = random(map.sideLength());
                while (!map.isCellEmpty(randomCoordinate)) {
                    randomCoordinate = random(map.sideLength());
                }
                map.addEntity(randomCoordinate, factory.create());
            }
        }
    }
}
