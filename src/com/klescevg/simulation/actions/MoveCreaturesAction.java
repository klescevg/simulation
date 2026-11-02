package com.klescevg.simulation.actions;

import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.WorldMapUtils;
import com.klescevg.simulation.entities.creatures.Creature;

public class MoveCreaturesAction implements Action {
    private final PathfindingStrategy strategy;

    public MoveCreaturesAction(PathfindingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(WorldMap map) {
        for (Creature creature : WorldMapUtils.getEntitiesOfType(map, Creature.class)) {
            if (creature.isAlive()) {
                creature.makeMove(map, strategy);
            }
        }
    }
}
