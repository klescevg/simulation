package com.klescevg.simulation;

import com.klescevg.simulation.actions.Action;
import com.klescevg.simulation.actions.SpawnEntitiesAction;

import java.util.List;

public class Simulation {
    private int turnCount;
    private WorldMap worldMap;
    private WorldMapConsoleRenderer worldMapConsoleRenderer;
    private List<Action> initActions;
    private List<Action> turnActions;

    public Simulation(WorldMap worldMap, SimulationConfig simulationConfig){
/*        turnCount = 0;
        WorldMapConsoleRenderer consoleRenderer = new WorldMapConsoleRenderer(worldMap);

        initActions.add(new SpawnEntitiesAction(new))*/
    }

    public void nextTurn(){

    }

    public void startSimulation(){

    }

    public void pauseSimulation(){

    }
}
