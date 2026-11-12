package com.klescevg.simulation.actions;

import com.klescevg.simulation.entities.EntityType;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SpawnEntitiesAction implements Action {
    private static final Random RANDOM = new Random();

    private final EntityFactory factory;
    private final Map<EntityType, Integer> spawnCounts;

    public SpawnEntitiesAction(EntityFactory factory, Map<EntityType, Integer> spawnCounts) {
        this.factory = factory;
        this.spawnCounts = spawnCounts;
    }

    @Override
    public void execute(WorldMap map) {
        for (EntityType type : spawnCounts.keySet()) {
            for (int i = 0; i < spawnCounts.get(type); i++) {
                findRandomEmptyCoordinate(map).ifPresent(coordinate -> map.addEntity(coordinate, factory.create(type)));
            }
        }
    }

    private Optional<Coordinate> findRandomEmptyCoordinate(WorldMap map) {
        if (map.isFull()) {
            return Optional.empty();
        }

        Coordinate coordinate = createRandomCoordinate(map);
        while (!map.isCellEmpty(coordinate)) {
            coordinate = createRandomCoordinate(map);
        }
        return Optional.of(coordinate);
    }

    private Coordinate createRandomCoordinate(WorldMap map) {
        return new Coordinate(RANDOM.nextInt(map.getHeight()), RANDOM.nextInt(map.getWidth()));
    }
}
