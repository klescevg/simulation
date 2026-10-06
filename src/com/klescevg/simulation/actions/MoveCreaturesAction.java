package com.klescevg.simulation.actions;

import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Creature;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;

public class MoveCreaturesAction extends Action {
    private PathFinder pathFinder;

    public MoveCreaturesAction(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (Creature creature : worldMap.getEntitiesOfType(Creature.class)) {
            if (worldMap.getEntityCoordinates(creature) != null) {
                creature.makeMove(worldMap, pathFinder);
            }
        }
    }
}
