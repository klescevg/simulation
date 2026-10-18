package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.factories.*;
import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.view.renderer.ConsoleRenderer;
import com.klescevg.simulation.view.renderer.Renderer;
import com.klescevg.simulation.world.WorldMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
    private int turnCount;
    private final WorldMap worldMap;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile boolean running = false;
    private volatile boolean paused = false;

    public Simulation(WorldMap worldMap, Map<EntityFactory, Integer> entityCounts, PathfindingStrategy strategy) {
        turnCount = 0;
        this.worldMap = worldMap;
        renderer = new ConsoleRenderer();

        initActions = List.of(new SpawnEntitiesAction(entityCounts));
        Map<EntityFactory, Integer> grassCounts = new HashMap<>();
        grassCounts.put(new GrassFactory(), 1);
        turnActions = List.of(new SpawnEntitiesAction(grassCounts), new MoveCreaturesAction(strategy), new HungerAction(), new RemoveDeadCreaturesAction());
    }

    public void start() {
        running = true;
        spawnInitialEntities();

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

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    private void spawnInitialEntities() {
        executeActions(initActions);
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }

    public void nextTurn() {
        turnCount++;
        executeActions(turnActions);
        renderer.render(worldMap);
    }
}
