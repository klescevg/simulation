package com.klescevg.simulation;

import com.klescevg.simulation.SimulationConfig.*;

import java.util.Scanner;

public class SimulationController {
    private static final String COMMAND_PAUSE_RESUME = "";
    private static final String COMMAND_NEXT_TURN = "1";
    private static final String COMMAND_STOP = "x";

    private Simulation simulation;
    private final Scanner scanner = new Scanner(System.in);

    public void startSimulation() throws InterruptedException {
        initSimulation();
        Thread simulationThread = new Thread(() -> simulation.start());

        simulationThread.start();

        while (simulation.isRunning()) {
            String command = scanner.nextLine().trim();

            if (simulation.isPaused()) {
                processCommandWhilePaused(command);
            } else {
                processCommandWhileResumed(command);
            }
        }
        simulationThread.join();
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

    private void processCommandWhilePaused(String command) {
        switch (command) {
            case COMMAND_PAUSE_RESUME -> simulation.resume();
            case COMMAND_NEXT_TURN -> simulation.requestStep();
            case COMMAND_STOP -> simulation.stop();
            default -> { }
        }
    }

    private void processCommandWhileResumed(String command) {
        if (command.isEmpty()) {
            simulation.pause();
        }
    }
}
