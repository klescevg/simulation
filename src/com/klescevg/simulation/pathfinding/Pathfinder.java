package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.entities.Entity;

public interface Pathfinder {
    Path find(WorldMap map, Coordinate start, Class<? extends Entity> targetType);
}
