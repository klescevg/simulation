package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.CoordinateGenerator;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.*;

import java.util.Map;
import java.util.Optional;

public class SpawnEntitiesAction implements Action {
    private final Map<EntityFactory, Integer> spawnCounts;

    public SpawnEntitiesAction(Map<EntityFactory, Integer> spawnCounts) {
        this.spawnCounts = spawnCounts;
    }

    @Override
    public void execute(WorldMap map) {
        for (EntityFactory factory : spawnCounts.keySet()) {
            for (int i = 0; i < spawnCounts.get(factory); i++) {
                findRandomEmptyCoordinate(map).ifPresent(coordinate -> map.addEntity(coordinate, factory.create()));
            }
        }
    }

    private Optional<Coordinate> findRandomEmptyCoordinate(WorldMap map) {
        if (map.isFull()) {
            return Optional.empty();
        }

        Coordinate coordinate = CoordinateGenerator.random(map.sideLength());
        while (!map.isCellEmpty(coordinate)) {
            coordinate = CoordinateGenerator.random(map.sideLength());
        }
        return Optional.of(coordinate);
    }
}
