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
        SimulationController simulationController = new SimulationController();
        simulationController.start();
    }
}