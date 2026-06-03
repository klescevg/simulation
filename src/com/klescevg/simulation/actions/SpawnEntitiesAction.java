package com.klescevg.simulation.actions;

import com.klescevg.simulation.SimulationConfig;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.factories.*;

import java.util.HashMap;
import java.util.Map;

public class SpawnEntitiesAction extends Action{
    private Map<EntityFactory, Integer> entityCounts;

    public SpawnEntitiesAction(Map<EntityFactory, Integer> entityCounts){
        this.entityCounts = entityCounts;
    }

    public SpawnEntitiesAction(SimulationConfig simulationConfig){
        this.entityCounts = getEntityCountsMap(simulationConfig);
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (EntityFactory factory : entityCounts.keySet()){
            for (int i = 0; i < entityCounts.get(factory); i++) {
                worldMap.addEntityToRandomPosition(factory.create());
            }
        }
    }

    private Map<EntityFactory, Integer> getEntityCountsMap(SimulationConfig simulationConfig) {
        EntityFactory treeFactory = new TreeFactory();
        EntityFactory rockFactory = new RockFactory();
        EntityFactory grassFactory = new GrassFactory();
        EntityFactory herbivoreFactory = new HerbivoreFactory(simulationConfig.herbivoreHealthPoints(), simulationConfig.herbivoreSpeed());
        EntityFactory predatorFactory = new PredatorFactory(simulationConfig.predatorHealthPoints(), simulationConfig.predatorSpeed(), simulationConfig.predatorAttackPower());

        Map<EntityFactory, Integer> entityCounts = new HashMap<>();
        entityCounts.put(treeFactory, simulationConfig.treeCount());
        entityCounts.put(rockFactory, simulationConfig.rockCount());
        entityCounts.put(grassFactory, simulationConfig.grassCount());
        entityCounts.put(herbivoreFactory, simulationConfig.herbivoreCount());
        entityCounts.put(predatorFactory, simulationConfig.predatorCount());
        return entityCounts;
    }
}
