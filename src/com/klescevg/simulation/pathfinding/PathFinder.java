package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.world.Coordinates;

import java.util.Stack;

public class PathFinder {
    private PathfindingStrategy strategy;

    public PathFinder(PathfindingStrategy strategy) {
        this.strategy = strategy;
    }

    public Stack<Coordinates> getPathToTarget(Entity startEntity, Class<? extends Entity> targetEntityType) {
        return strategy.getPathToTarget(startEntity, targetEntityType);
    }
}