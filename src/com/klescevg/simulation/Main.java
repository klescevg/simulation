package com.klescevg.simulation;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new SimulationFactory().create();
        SimulationConsoleRunner simulationConsoleRunner = new SimulationConsoleRunner(simulation);
        simulationConsoleRunner.runSimulation();
    }
}