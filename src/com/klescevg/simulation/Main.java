package com.klescevg.simulation;


import com.klescevg.simulation.simulation.SimulationController;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SimulationController simulationController = new SimulationController();
        simulationController.startSimulation();
    }
}