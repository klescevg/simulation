package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.*;
import com.klescevg.simulation.entities.objects.*;
import com.klescevg.simulation.factories.*;

import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(10);
        WorldMapConsoleRenderer worldMapConsoleRenderer = new WorldMapConsoleRenderer(worldMap);
        PathFinder pathFinder = new PathFinder(worldMap);

        Map<EntityFactory, Integer> entityCounts = getEntityCountsMap();
        SpawnEntitiesAction spawnEntitiesAction = new SpawnEntitiesAction(entityCounts);
        spawnEntitiesAction.execute(worldMap);

        worldMapConsoleRenderer.render();

        MoveCreaturesAction moveCreaturesAction = new MoveCreaturesAction(pathFinder);

        for (int i = 1; i <= 5; i++) {
            System.out.println("Day " + i + ":");
            moveCreaturesAction.execute(worldMap);
            worldMapConsoleRenderer.render();
        }
    }

    private static Map<EntityFactory, Integer> getEntityCountsMap() {
        SimulationConfig simulationConfig = new SimulationConfig(5, 5, 5, 2,
                1, 100, 2, 100, 3, 35);

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