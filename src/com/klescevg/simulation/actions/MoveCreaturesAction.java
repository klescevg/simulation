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
    public void execute(WorldMap map) {
        for (Herbivore herbivore : map.getEntitiesOfType(Herbivore.class)) {
            herbivore.makeMove(map, strategy);
        }

        for (Predator predator : map.getEntitiesOfType(Predator.class)) {
            predator.makeMove(map, strategy);
        }
    }
}
