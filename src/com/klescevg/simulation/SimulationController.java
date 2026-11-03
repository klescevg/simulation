package com.klescevg.simulation;

import java.util.Scanner;

public class SimulationController {
    private static final String COMMAND_PAUSE_RESUME = "";
    private static final String COMMAND_NEXT_TURN = "1";
    private static final String COMMAND_STOP = "x";

    private static final String WELCOME_MESSAGE = """
            Welcome to the Simulation: herbivores eat grass, predators hunt herbivores.
            
            Commands:
              Enter - start / pause / resume
              %s     - next turn (only while paused)
              %s     - stop (only while paused)
            """.formatted(COMMAND_NEXT_TURN, COMMAND_STOP);
    private static final String PAUSED_MESSAGE = "Paused. Commands: Enter - resume, %s - next turn, %s - stop".formatted(COMMAND_NEXT_TURN, COMMAND_STOP);
    private static final String INPUT_PROMPT = "Enter the command: ";
    private static final String UNEXPECTED_COMMAND_MESSAGE = "Unexpected command: ";
    private static final String STOPPED_MESSAGE = "Simulation stopped.";

    private Simulation simulation;
    private final Scanner scanner = new Scanner(System.in);

    public void runSimulation() {
        initSimulation();
        Thread simulationThread = new Thread(() -> simulation.start());

        simulationThread.start();
        printWelcomeMessage();

        while (!simulation.isStopped()) {
            String command = readCommand();

            if (!simulation.isStopped()) {
                processCommand(command);
            }
        }

        printStoppedMessage();
    }

    private void initSimulation() {
        SimulationConfig config = new SimulationConfigProvider().getConfig();
        simulation = new SimulationFactory().create(config);
    }

    private String readCommand() {
        if (simulation.isPaused()) {
            printCommandPrompt();
        }
        return scanner.nextLine().trim();
    }

    private void processCommand(String command) {
        if (simulation.isPaused()) {
            processCommandWhilePaused(command);
        } else {
            processCommandWhileRunning(command);
        }
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

    private void printStoppedMessage() {
        System.out.println(STOPPED_MESSAGE);
    }

    private void printCommandPrompt() {
        System.out.println();
        System.out.print(INPUT_PROMPT);
    }

    private void printUnexpectedCommandMessage(String command) {
        System.out.println(UNEXPECTED_COMMAND_MESSAGE + command);
    }
}
