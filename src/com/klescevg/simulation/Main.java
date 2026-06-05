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
        WorldMap worldMap = new WorldMap(7);
        SimulationConfig simulationConfig = new SimulationConfig(4, 4, 7, 2,
                1, 100, 2, 100, 2, 30);

        Simulation simulation = new Simulation(worldMap, getEntityCountsMap(simulationConfig));

        simulation.startSimulation();

    }

    private static Map<EntityFactory, Integer> getEntityCountsMap(SimulationConfig simulationConfig) {
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