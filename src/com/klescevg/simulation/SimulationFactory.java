package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.factories.*;
import com.klescevg.simulation.view.renderer.*;
import com.klescevg.simulation.world.WorldMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationFactory {
    public Simulation create(SimulationConfig config) {
        WorldMap map = buildMap(config.mapSideLength());
        Renderer renderer = buildRenderer();
        List<Action> initActions = buildInitActions(config);
        List<Action> turnActions = buildTurnActions(config, map);
        return new Simulation(map, renderer, initActions, turnActions);
    }

    private WorldMap buildMap(int sideLength) {
        return new WorldMap(sideLength);
    }

    private Renderer buildRenderer() {
        return new ConsoleRenderer();
    }

    private List<Action> buildInitActions(SimulationConfig config) {
        Map<EntityFactory, Integer> initialSpawnCounts = buildInitialSpawnCounts(config);
        return List.of(new SpawnEntitiesAction(initialSpawnCounts));
    }

    private Map<EntityFactory, Integer> buildInitialSpawnCounts(SimulationConfig config) {
        EntityFactory treeFactory = new TreeFactory();
        EntityFactory rockFactory = new RockFactory();
        EntityFactory grassFactory = new GrassFactory();
        EntityFactory herbivoreFactory = new HerbivoreFactory(config.herbivoreStats().healthPoints(), config.herbivoreStats().speed());
        EntityFactory predatorFactory = new PredatorFactory(config.predatorStats().healthPoints(), config.predatorStats().speed(), config.predatorStats().attackPower());

        Map<EntityFactory, Integer> spawnCounts = new HashMap<>();
        spawnCounts.put(treeFactory, config.entityCounts().initialTreeCount());
        spawnCounts.put(rockFactory, config.entityCounts().initialRockCount());
        spawnCounts.put(grassFactory, config.entityCounts().initialGrassCount());
        spawnCounts.put(herbivoreFactory, config.entityCounts().initialHerbivoreCount());
        spawnCounts.put(predatorFactory, config.entityCounts().initialPredatorCount());
        return spawnCounts;
    }
}
