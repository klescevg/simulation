package com.klescevg.simulation.config;

import com.klescevg.simulation.config.SimulationConfig.*;

public class SimulationConfigProvider {
    private static final int INITIAL_TREE_COUNT = 40;
    private static final int INITIAL_ROCK_COUNT = 40;
    private static final int INITIAL_GRASS_COUNT = 50;
    private static final int INITIAL_HERBIVORE_COUNT = 10;
    private static final int INITIAL_PREDATOR_COUNT = 2;
    private static final int GRASS_SPAWN_PER_TURN = 1;

    private static final int HERBIVORE_HEALTH_POINTS = 100;
    private static final int HERBIVORE_SPEED = 1;

    private static final int PREDATOR_HEALTH_POINTS = 100;
    private static final int PREDATOR_SPEED = 2;
    private static final int PREDATOR_ATTACK_POWER = 25;

    private static final int MAP_WIDTH = 20;
    private static final int MAP_HEIGHT = 20;

    private static final int MAX_SIDE_LENGTH = 46340; //max side length for map capacity to fit in int

    public SimulationConfig getConfig() {
        validate();

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
        return new SimulationConfig(MAP_WIDTH, MAP_HEIGHT, entityCounts, herbivoreStats, predatorStats);
    }

    private void validate() {
        requireValidSideLength("MAP_WIDTH", MAP_WIDTH);
        requireValidSideLength("MAP_HEIGHT", MAP_HEIGHT);

        int mapCapacity = MAP_WIDTH * MAP_HEIGHT;
        if (initialTotal() >= mapCapacity) {
            throw new IllegalArgumentException("Initial entities " + initialTotal() + " must be fewer than map cells " + mapCapacity);
        }

        requireNonNegative("INITIAL_TREE_COUNT", INITIAL_TREE_COUNT);
        requireNonNegative("INITIAL_ROCK_COUNT", INITIAL_ROCK_COUNT);
        requireNonNegative("INITIAL_GRASS_COUNT", INITIAL_GRASS_COUNT);
        requireNonNegative("INITIAL_HERBIVORE_COUNT", INITIAL_HERBIVORE_COUNT);
        requireNonNegative("INITIAL_PREDATOR_COUNT", INITIAL_PREDATOR_COUNT);
        requireNonNegative("GRASS_SPAWN_PER_TURN", GRASS_SPAWN_PER_TURN);

        requirePositive("HERBIVORE_HEALTH_POINTS", HERBIVORE_HEALTH_POINTS);
        requirePositive("HERBIVORE_SPEED", HERBIVORE_SPEED);

        requirePositive("PREDATOR_HEALTH_POINTS", PREDATOR_HEALTH_POINTS);
        requirePositive("PREDATOR_SPEED", PREDATOR_SPEED);
        requirePositive("PREDATOR_ATTACK_POWER", PREDATOR_ATTACK_POWER);
    }

    private static void requireValidSideLength(String name, int value) {
        if (value <= 0 || value > MAX_SIDE_LENGTH) {
            throw new IllegalArgumentException(name + " must be between 1 and " + MAX_SIDE_LENGTH + ", but was " + value);
        }
    }

    private static void requireNonNegative(String name, int value) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must not be negative, but was " + value);
        }
    }

    private static void requirePositive(String name, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive, but was " + value);
        }
    }

    private static int initialTotal() {
        return INITIAL_TREE_COUNT + INITIAL_ROCK_COUNT + INITIAL_GRASS_COUNT + INITIAL_HERBIVORE_COUNT + INITIAL_PREDATOR_COUNT;
    }
}
