package com.klescevg.simulation.actions;

import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.factories.EntityFactory;

import java.util.Optional;

import static com.klescevg.simulation.world.CoordinateGenerator.random;

public class SpawnEntityAction extends Action {
    private final EntityFactory factory;

    public SpawnEntityAction(EntityFactory factory) {
        this.factory = factory;
    }

    @Override
    public void execute(WorldMap map) {
        findRandomEmptyCoordinate(map).ifPresent(coordinate -> map.addEntity(coordinate, factory.create()));
    }

    private Optional<Coordinate> findRandomEmptyCoordinate(WorldMap map) {
        if (map.isFull()) {
            return Optional.empty();
        }

        Coordinate coordinate = random(map.sideLength());
        while (!map.isCellEmpty(coordinate)) {
            coordinate = random(map.sideLength());
        }
        return Optional.of(coordinate);
    }
}
