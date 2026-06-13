package com.klescevg.simulation;

import com.klescevg.simulation.factories.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimulationController {
    //обработка всего кроме самой симуляции, то есть:
    //взаимодействие с пользователем, создание конфига, создание симуляции

    private Simulation simulation;
    private Scanner SCANNER = new Scanner(System.in);

    public void start() {
        initSimulation();

        System.out.println("Do you want to start a simulation?");
        SCANNER.nextLine();
        long before = System.currentTimeMillis();

        Thread simulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                simulation.startSimulation();
            }
        });
        simulationThread.start();

        Thread inputThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(simulationThread.isAlive()){
                    SCANNER.nextLine();
                    simulation.pauseSimulation();

                    System.out.println("Do you want to continue?");

                    SCANNER.nextLine();
                    simulation.resumeSimulation();
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
        this.simulation = new Simulation(worldMap, getEntityCountsMap(simulationConfig));
    }

    public int getMapSize() {
        //System.out.println("Enter the map size: ");
        //return SCANNER.nextInt();
        return 10;
    }

    public SimulationConfig getSimulationConfig() {
        return new SimulationConfig(5, 5, 5, 2,
                1, 100, 2, 100, 3, 35);
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
