package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinates;
import com.klescevg.simulation.entities.Entity;

import java.util.Stack;

public interface PathfindingStrategy {
    Stack<Coordinates> getPathToTarget(Entity startEntity, Class<? extends Entity> targetEntityType);
}
