package com.klescevg.simulation;

import com.klescevg.simulation.factories.*;
import com.klescevg.simulation.pathfinding.*;
import com.klescevg.simulation.world.WorldMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimulationController {
    //обработка всего кроме самой симуляции, то есть:
    //взаимодействие с пользователем, создание конфига, создание симуляции

    private Simulation simulation;
    private final Scanner SCANNER = new Scanner(System.in);

    public void startSimulation() {
        initSimulation();

        System.out.println("Do you want to start a simulation?");
        SCANNER.nextLine();
        long before = System.currentTimeMillis();

        Thread simulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                simulation.start();
            }
        });
        simulationThread.start();

        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(simulationThread.isAlive()){
                    SCANNER.nextLine();
                    simulation.pause();

                    System.out.println("Do you want to continue?");

                    SCANNER.nextLine();
                    simulation.resume();
                }
            }
        });

        inputThread.setDaemon(true);
        inputThread.start();

        try {
            simulationThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Simulation has finished!");
        long after = System.currentTimeMillis();
        System.out.println("Simulation duration: " + (after - before) + " ms");
    }

    public void initSimulation() {
        SimulationConfig simulationConfig = getSimulationConfig();
        WorldMap worldMap = new WorldMap(getMapSize());
        PathfindingStrategy strategy = new AStarStrategy(worldMap);
        simulation = new Simulation(worldMap, getEntityCountsMap(simulationConfig), strategy);
    }

    public int getMapSize() {
        //System.out.println("Enter the map size: ");
        //return SCANNER.nextInt();
        return 100;
    }

    public SimulationConfig getSimulationConfig() {
        return new SimulationConfig(100, 100, 1, 1000,
                200, 1000, 1, 1000, 2, 25);
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
