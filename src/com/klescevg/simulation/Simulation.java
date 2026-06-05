package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.objects.Grass;
import com.klescevg.simulation.factories.*;

import java.util.List;
import java.util.Map;

public class Simulation {
    private int turnCount;
    private WorldMap worldMap;
    private WorldMapConsoleRenderer worldMapConsoleRenderer;
    private List<Action> initActions;
    private List<Action> turnActions;
    private boolean isRunning;
    private boolean isPaused;

    public Simulation(WorldMap worldMap, Map<EntityFactory, Integer> entityCounts) {
        turnCount = 0;
        this.worldMap = worldMap;
        worldMapConsoleRenderer = new WorldMapConsoleRenderer(worldMap);

        initActions = List.of(new SpawnEntitiesAction(entityCounts));
        turnActions = List.of(new MoveCreaturesAction(new PathFinder(worldMap)));
        //turnActions.add(new SpawnEntityAction(new GrassFactory()));
        //turnActions.add(new SpawnEntityAction(new HerbivoreFactory(simulationConfig.herbivoreHealthPoints(), simulationConfig.herbivoreSpeed())));

        executeActions(initActions);

        isRunning = false;
        isPaused = false;
    }

    public void nextTurn() {
        turnCount++;
        System.out.println(turnCount);
        executeActions(turnActions);
        worldMapConsoleRenderer.render();
    }

    public void startSimulation() {
        isRunning = true;

        while (isRunning) {
            if (!worldMap.getEntities().contains(new Grass()) ||
                    !worldMap.getEntities().contains(new Herbivore())) {
                break;
            }
            nextTurn();
            try{
                Thread.sleep(1000);
            } catch (Exception e){

            }
        }
    }

    public void pauseSimulation() {
        isPaused = true;
    }

    public void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(worldMap);
        }
    }
}
