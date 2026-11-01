package com.klescevg.simulation.simulation;

import com.klescevg.simulation.actions.Action;
import com.klescevg.simulation.view.renderer.Renderer;
import com.klescevg.simulation.world.WorldMap;

import java.util.List;

import static com.klescevg.simulation.simulation.SimulationState.*;

public class Simulation {
    private static final int TURN_DELAY_MS = 500;
    private static final int PAUSE_POLL_INTERVAL_MS = 100;

    private final WorldMap worldMap;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private volatile SimulationState state = PAUSED;
    private volatile boolean turnRequested = false;
    private int currentTurn = 0;

    public Simulation(WorldMap worldMap, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        this.worldMap = worldMap;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void start() {
        try {
            spawnInitialEntities();
            while (isActive()) {
                switch (state) {
                    case RUNNING -> {
                        nextTurn();
                        sleep(TURN_DELAY_MS);
                    }
                    case PAUSED -> handlePause();
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
            turnRequested = false;
        }
    }

    public void stop() {
        state = STOPPED;
    }

    public void requestTurn() {
        if (isPaused()) {
            turnRequested = true;
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

    public synchronized void nextTurn() {
        currentTurn++;
        executeActions(turnActions);
        renderer.render(worldMap, currentTurn);
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }

    private void handlePause() {
        if (turnRequested) {
            turnRequested = false;
            nextTurn();
        } else {
            sleep(PAUSE_POLL_INTERVAL_MS);
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
