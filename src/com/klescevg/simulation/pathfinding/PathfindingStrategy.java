package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.world.WorldMap;

public interface PathfindingStrategy {
    Path find(WorldMap map, Coordinate start, Class<? extends Entity> targetType);
}
