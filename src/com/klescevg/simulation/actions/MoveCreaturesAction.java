package com.klescevg.simulation.actions;

import com.klescevg.simulation.pathfinding.PathFinder;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;

public class MoveCreaturesAction extends Action {
    private PathFinder pathFinder;

    public MoveCreaturesAction(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (Herbivore herbivore : worldMap.getEntitiesOfType(Herbivore.class)) {
            herbivore.makeMove(worldMap, pathFinder);
        }

        for (Predator predator : worldMap.getEntitiesOfType(Predator.class)) {
            predator.makeMove(worldMap, pathFinder);
        }
    }
}
