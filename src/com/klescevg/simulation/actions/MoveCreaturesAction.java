package com.klescevg.simulation.actions;

import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Creature;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;

public class MoveCreaturesAction extends Action{
    private PathFinder pathFinder;

    public MoveCreaturesAction(PathFinder pathFinder){
        this.pathFinder = pathFinder;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (Entity entity : worldMap.getEntities()){
            if (worldMap.getEntityCoordinates(entity) != null){
                if (entity instanceof Herbivore){
                    ((Herbivore) entity).makeMove(worldMap, pathFinder);
                } else if (entity instanceof Predator){
                    ((Predator) entity).makeMove(worldMap, pathFinder);
                }
            }
        }
    }
}
