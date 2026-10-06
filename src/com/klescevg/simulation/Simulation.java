package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.entities.*;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;
import com.klescevg.simulation.factories.*;

import java.util.List;
import java.util.Map;

public class Simulation {
    private int turnCount;
    private WorldMap worldMap;
    private WorldMapConsoleRenderer worldMapConsoleRenderer;
    private List<Action> initActions;
    private List<Action> turnActions;
    private volatile boolean running = false;
    private volatile boolean paused = false;

    public Simulation(WorldMap worldMap, Map<EntityFactory, Integer> entityCounts) {
        turnCount = 0;
        this.worldMap = worldMap;
        worldMapConsoleRenderer = new WorldMapConsoleRenderer(worldMap);

        initActions = List.of(new SpawnEntitiesAction(entityCounts));
        turnActions = List.of(new SpawnEntityAction(new GrassFactory()), new MoveCreaturesAction(new PathFinder(worldMap)), new HungerAction());
        //turnActions.add(new SpawnEntityAction(new HerbivoreFactory(simulationConfig.herbivoreHealthPoints(), simulationConfig.herbivoreSpeed())));

        executeActions(initActions);
    }

    public void nextTurn() {
        turnCount++;
        System.out.print("\033[H\033[2J");
        System.out.println(turnCount);
        executeActions(turnActions);
        worldMapConsoleRenderer.render();
    }

    public void startSimulation() {
        running = true;

        while (running) {
            try {
                if (paused) {
                    Thread.sleep(1000);
                    continue;
                }

                if (!worldMap.isEntityTypePresent(Herbivore.class)) {
                    running = false;
                    break;
                }

                nextTurn();
                Thread.sleep(500);
            } catch (Exception e) {

            }
        }
    }

    public void pauseSimulation() {
        this.paused = true;
    }

    public void resumeSimulation() {
        this.paused = false;
    }

    public void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }
}
