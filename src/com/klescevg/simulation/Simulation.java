package com.klescevg.simulation;

import com.klescevg.simulation.actions.Action;
import com.klescevg.simulation.view.renderer.Renderer;
import com.klescevg.simulation.world.WorldMap;

import java.util.List;

public class Simulation {
    private final WorldMap worldMap;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile boolean running = false;
    private volatile boolean paused = false;
    private int turnCount;

    public Simulation(WorldMap worldMap, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        this.worldMap = worldMap;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
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

    private void nextTurn() {
        turnCount++;
        executeActions(turnActions);
        renderer.render(worldMap);
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }
}
