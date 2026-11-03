package com.klescevg.simulation;

import com.klescevg.simulation.SimulationConfig.*;

public class SimulationConfigProvider {
    private static final int INITIAL_TREE_COUNT = 50;
    private static final int INITIAL_ROCK_COUNT = 50;
    private static final int INITIAL_GRASS_COUNT = 50;
    private static final int INITIAL_HERBIVORE_COUNT = 10;
    private static final int INITIAL_PREDATOR_COUNT = 2;
    private static final int GRASS_SPAWN_PER_TURN = 1;
    private static final int HERBIVORE_HEALTH_POINTS = 100;
    private static final int HERBIVORE_SPEED = 1;
    private static final int PREDATOR_HEALTH_POINTS = 100;
    private static final int PREDATOR_SPEED = 2;
    private static final int PREDATOR_ATTACK_POWER = 25;
    private static final int MAP_SIDE_LENGTH = 20;

    public SimulationConfig getConfig() {
        EntityCounts entityCounts = new EntityCounts(
                INITIAL_TREE_COUNT,
                INITIAL_ROCK_COUNT,
                INITIAL_GRASS_COUNT,
                INITIAL_HERBIVORE_COUNT,
                INITIAL_PREDATOR_COUNT,
                GRASS_SPAWN_PER_TURN);
        HerbivoreStats herbivoreStats = new HerbivoreStats(
                HERBIVORE_HEALTH_POINTS,
                HERBIVORE_SPEED);
        PredatorStats predatorStats = new PredatorStats(
                PREDATOR_HEALTH_POINTS,
                PREDATOR_SPEED,
                PREDATOR_ATTACK_POWER);
        return new SimulationConfig(MAP_SIDE_LENGTH, entityCounts, herbivoreStats, predatorStats);
    }
}
