package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.factories.*;
import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.world.WorldMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
    private int turnCount;
    private final WorldMap worldMap;
    private final WorldMapConsoleRenderer worldMapConsoleRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile boolean running = false;
    private volatile boolean paused = false;

    public Simulation(WorldMap worldMap, Map<EntityFactory, Integer> entityCounts, PathfindingStrategy strategy) {
        turnCount = 0;
        this.worldMap = worldMap;
        worldMapConsoleRenderer = new WorldMapConsoleRenderer(worldMap);

        initActions = List.of(new SpawnEntitiesAction(entityCounts));
        Map<EntityFactory, Integer> grassCounts = new HashMap<>();
        grassCounts.put(new GrassFactory(), 1);
        turnActions = List.of(new SpawnEntitiesAction(grassCounts), new MoveCreaturesAction(strategy), new HungerAction(), new RemoveDeadCreaturesAction());
        executeActions(initActions);
    }

    public void nextTurn() {
        turnCount++;
        executeActions(turnActions);
        System.out.print("\033[H\033[2J");
        System.out.println(turnCount);
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

                nextTurn();
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
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
