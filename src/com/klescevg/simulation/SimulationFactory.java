package com.klescevg.simulation;

import com.klescevg.simulation.actions.*;
import com.klescevg.simulation.config.SimulationConfig;
import com.klescevg.simulation.factories.*;
import com.klescevg.simulation.pathfinding.*;
import com.klescevg.simulation.view.renderer.*;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.entities.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationFactory {
    public Simulation create(SimulationConfig config) {
        WorldMap map = buildMap(config.mapWidth(), config.mapHeight());
        Renderer renderer = buildRenderer();
        EntityFactory factory = new EntityFactory();
        List<Action> initActions = buildInitActions(factory, config);
        List<Action> turnActions = buildTurnActions(factory, config);
        return new Simulation(map, renderer, initActions, turnActions);
    }

    private WorldMap buildMap(int mapWidth, int mapHeight) {
        return new WorldMap(mapWidth, mapHeight);
    }

    private Renderer buildRenderer() {
        return new ConsoleRenderer();
    }

    private List<Action> buildInitActions(EntityFactory factory, SimulationConfig config) {
        Map<EntityType, Integer> initialSpawnCounts = buildInitialSpawnCounts(config);
        return List.of(new SpawnEntitiesAction(factory, initialSpawnCounts));
    }

    private List<Action> buildTurnActions(EntityFactory factory, SimulationConfig config) {
        Map<EntityType, Integer> turnSpawnCounts = buildTurnSpawnCounts(config);
        Pathfinder pathfinder = new AStarPathfinder();
        return List.of(new SpawnEntitiesAction(factory, turnSpawnCounts), new MoveCreaturesAction(pathfinder), new ApplyHungerDamageAction(), new RemoveDeadCreaturesAction());
    }

    private Map<EntityType, Integer> buildTurnSpawnCounts(SimulationConfig config) {
        Map<EntityType, Integer> turnSpawnCounts = new HashMap<>();
        turnSpawnCounts.put(EntityType.GRASS, config.entityCounts().grassSpawnPerTurn());
        return turnSpawnCounts;
    }

    private Map<EntityType, Integer> buildInitialSpawnCounts(SimulationConfig config) {
        Map<EntityType, Integer> spawnCounts = new HashMap<>();
        spawnCounts.put(EntityType.TREE, config.entityCounts().initialTreeCount());
        spawnCounts.put(EntityType.ROCK, config.entityCounts().initialRockCount());
        spawnCounts.put(EntityType.GRASS, config.entityCounts().initialGrassCount());
        spawnCounts.put(EntityType.HERBIVORE, config.entityCounts().initialHerbivoreCount());
        spawnCounts.put(EntityType.PREDATOR, config.entityCounts().initialPredatorCount());
        return spawnCounts;
    }
}
