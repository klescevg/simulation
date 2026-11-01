package com.klescevg.simulation.simulation;

import com.klescevg.simulation.simulation.SimulationConfig.*;

import java.util.Scanner;

public class SimulationController {
    private static final String COMMAND_PAUSE_RESUME = "";
    private static final String COMMAND_NEXT_TURN = "1";
    private static final String COMMAND_STOP = "x";

    private static final String PAUSED_COMMANDS = "Enter - resume, %s - next turn, %s - stop".formatted(COMMAND_NEXT_TURN, COMMAND_STOP);

    private static final String WELCOME_MESSAGE = """
            Welcome to the Simulation: herbivores eat grass, predators hunt herbivores.
            
            Commands:
              Enter - start / pause / resume
              %s     - next turn (only while paused)
              %s     - stop (only while paused)
            
            Press Enter to start.
            """.formatted(COMMAND_NEXT_TURN, COMMAND_STOP);
    private static final String PAUSED_MESSAGE = "Paused. Commands: %s".formatted(PAUSED_COMMANDS);
    private static final String UNEXPECTED_COMMAND_MESSAGE = "Unexpected command: ";

    private Simulation simulation;
    private final Scanner scanner = new Scanner(System.in);

    public void startSimulation() throws InterruptedException {
        initSimulation();
        Thread simulationThread = new Thread(() -> simulation.start());

        simulationThread.start();
        printWelcomeMessage();

        while (simulation.isActive()) {
            String command = scanner.nextLine().trim();

            if (simulation.isPaused()) {
                processCommandWhilePaused(command);
            } else {
                processCommandWhileRunning(command);
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
            case COMMAND_NEXT_TURN -> {
                simulation.nextTurn();
                printPausedMessage();
            }
            case COMMAND_STOP -> simulation.stop();
            default -> printUnexpectedCommandMessage(command);
        }
    }

    private void processCommandWhileRunning(String command) {
        if (command.isEmpty()) {
            simulation.pause();
            printPausedMessage();
        }
    }

    private void printWelcomeMessage() {
        System.out.print(WELCOME_MESSAGE);
    }

    private void printPausedMessage() {
        System.out.println(PAUSED_MESSAGE);
    }

    private void printUnexpectedCommandMessage(String command) {
        System.out.println(UNEXPECTED_COMMAND_MESSAGE + command);
        System.out.println(PAUSED_COMMANDS);
    }
}
