package com.klescevg.simulation.actions;

import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Creature;

import static com.klescevg.simulation.world.WorldMapUtils.getCreatures;

public class MoveCreaturesAction implements Action {
    private final PathfindingStrategy strategy;

    public MoveCreaturesAction(PathfindingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(WorldMap map) {
        for (Creature creature : getCreatures(map)) {
            //validation in case creature dies before its move
            if (creature.isAlive()) {
                creature.makeMove(map, strategy);
            }
        }
    }
}
