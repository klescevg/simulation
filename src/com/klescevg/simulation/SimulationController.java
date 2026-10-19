package com.klescevg.simulation;

import com.klescevg.simulation.SimulationConfig.*;

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

    private void initSimulation() {
        SimulationConfig config = getSimulationConfig();
        simulation = new SimulationFactory().create(config);
    }

    private SimulationConfig getSimulationConfig() {
        EntityCounts entityCounts = new EntityCounts(10, 10, 20, 5, 2, 1);
        HerbivoreStats herbivoreStats = new HerbivoreStats(100, 1);
        PredatorStats predatorStats = new PredatorStats(100, 2, 25);
        return new SimulationConfig(10, entityCounts, herbivoreStats, predatorStats);
    }
}
