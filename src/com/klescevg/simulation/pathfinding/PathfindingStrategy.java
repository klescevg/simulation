package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.entities.Entity;

public interface PathfindingStrategy {
    Path findPath(Entity startEntity, Class<? extends Entity> targetEntityType);
}
