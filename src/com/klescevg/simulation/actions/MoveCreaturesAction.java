package com.klescevg.simulation.actions;

import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;

public class MoveCreaturesAction extends Action {
    private final PathfindingStrategy strategy;

    public MoveCreaturesAction(PathfindingStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (Herbivore herbivore : worldMap.getEntitiesOfType(Herbivore.class)) {
            herbivore.makeMove(worldMap, strategy);
        }

        for (Predator predator : worldMap.getEntitiesOfType(Predator.class)) {
            predator.makeMove(worldMap, strategy);
        }
    }
}
