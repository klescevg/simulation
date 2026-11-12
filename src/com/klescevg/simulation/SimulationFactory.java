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
    private static final int INITIAL_TREE_COUNT = 40;
    private static final int INITIAL_ROCK_COUNT = 40;
    private static final int INITIAL_GRASS_COUNT = 50;
    private static final int INITIAL_HERBIVORE_COUNT = 10;
    private static final int INITIAL_PREDATOR_COUNT = 2;
    private static final int GRASS_SPAWN_PER_TURN = 1;

    private static final int MAP_WIDTH = 30;
    private static final int MAP_HEIGHT = 20;

    public Simulation create(SimulationConfig config) {
        WorldMap map = buildMap(MAP_WIDTH, MAP_HEIGHT);
        Renderer renderer = buildRenderer();
        EntityFactory factory = new EntityFactory();
        List<Action> initActions = buildInitActions(factory);
        List<Action> turnActions = buildTurnActions(factory);
        return new Simulation(map, renderer, initActions, turnActions);
    }

    private WorldMap buildMap(int mapWidth, int mapHeight) {
        return new WorldMap(mapWidth, mapHeight);
    }

    private Renderer buildRenderer() {
        return new ConsoleRenderer();
    }

    private List<Action> buildInitActions(EntityFactory factory) {
        Map<EntityType, Integer> initialSpawnCounts = buildInitialSpawnCounts();
        return List.of(new SpawnEntitiesAction(factory, initialSpawnCounts));
    }

    private List<Action> buildTurnActions(EntityFactory factory) {
        Map<EntityType, Integer> turnSpawnCounts = buildTurnSpawnCounts();
        Pathfinder pathfinder = new AStarPathfinder();
        return List.of(new SpawnEntitiesAction(factory, turnSpawnCounts), new MoveCreaturesAction(pathfinder), new ApplyHungerDamageAction(), new RemoveDeadCreaturesAction());
    }

    private Map<EntityType, Integer> buildTurnSpawnCounts() {
        Map<EntityType, Integer> turnSpawnCounts = new HashMap<>();
        turnSpawnCounts.put(EntityType.GRASS, GRASS_SPAWN_PER_TURN);
        return turnSpawnCounts;
    }

    private Map<EntityType, Integer> buildInitialSpawnCounts() {
        Map<EntityType, Integer> spawnCounts = new HashMap<>();
        spawnCounts.put(EntityType.TREE, INITIAL_TREE_COUNT);
        spawnCounts.put(EntityType.ROCK, INITIAL_ROCK_COUNT);
        spawnCounts.put(EntityType.GRASS, INITIAL_GRASS_COUNT);
        spawnCounts.put(EntityType.HERBIVORE, INITIAL_HERBIVORE_COUNT);
        spawnCounts.put(EntityType.PREDATOR, INITIAL_PREDATOR_COUNT);
        return spawnCounts;
    }
}
