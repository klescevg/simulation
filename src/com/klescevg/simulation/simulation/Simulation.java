package com.klescevg.simulation.simulation;

import com.klescevg.simulation.actions.Action;
import com.klescevg.simulation.view.renderer.Renderer;
import com.klescevg.simulation.world.WorldMap;

import java.util.List;

import static com.klescevg.simulation.simulation.SimulationState.*;

public class Simulation {
    private final WorldMap worldMap;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile SimulationState state = PAUSED;
    private volatile boolean stepRequested = false;
    private int turnCount;

    public Simulation(WorldMap worldMap, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        this.worldMap = worldMap;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void start() {
        spawnInitialEntities();

        while (isActive()) {
            try {
                if (isPaused()) {
                    if (stepRequested) {
                        stepRequested = false;
                        nextTurn();
                    } else {
                        Thread.sleep(100);
                    }
                    continue;
                }

                nextTurn();
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (isRunning()) {
            state = PAUSED;
        }
    }

    public void resume() {
        if (isPaused()) {
            state = RUNNING;
            stepRequested = false;
        }
    }

    public void stop() {
        state = STOPPED;
    }

    public void requestStep() {
        if (isPaused()) {
            stepRequested = true;
        }
    }

    public boolean isRunning() {
        return state == RUNNING;
    }

    public boolean isPaused() {
        return state == PAUSED;
    }

    public boolean isActive() {
        return state != STOPPED;
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
