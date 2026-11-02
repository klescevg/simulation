package com.klescevg.simulation.simulation;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.view.renderer.Renderer;
import com.klescevg.simulation.actions.Action;

import java.util.List;

import static com.klescevg.simulation.simulation.SimulationState.*;

public class Simulation {
    private static final int TURN_DELAY_MS = 500;
    private static final int PAUSE_POLL_INTERVAL_MS = 100;

    private final WorldMap map;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile SimulationState state = PAUSED;
    private int currentTurn = 0;

    public Simulation(WorldMap map, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        this.map = map;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void start() {
        try {
            executeActions(initActions);

            while (!isStopped()) {
                switch (state) {
                    case RUNNING -> {
                        nextTurn();
                        sleep(TURN_DELAY_MS);
                    }
                    case PAUSED -> sleep(PAUSE_POLL_INTERVAL_MS);
                }
            }
        } finally {
            stop();
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
        }
    }

    public void stop() {
        state = STOPPED;
    }

    public boolean isRunning() {
        return state == RUNNING;
    }

    public boolean isPaused() {
        return state == PAUSED;
    }

    public boolean isStopped() {
        return state == STOPPED;
    }

    public synchronized void nextTurn() {
        if (isStopped()) {
            throw new IllegalStateException("Cannot make a turn: simulation is stopped");
        } else {
            currentTurn++;
            executeActions(turnActions);
            renderer.render(map, currentTurn);
        }
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(map);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Simulation thread was interrupted", e);
        }
    }
}
