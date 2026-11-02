package com.klescevg.simulation;

import java.util.Objects;

public record SimulationConfig(
        int mapSideLength,
        EntityCounts entityCounts,
        HerbivoreStats herbivoreStats,
        PredatorStats predatorStats) {

    private static final int MAX_SIDE_LENGTH = 46340; //max side length for map capacity to fit in int

    public SimulationConfig {
        if (mapSideLength <= 0 || mapSideLength > MAX_SIDE_LENGTH) {
            throw new IllegalArgumentException("mapSideLength must be between 1 and " + MAX_SIDE_LENGTH + ", but was " + mapSideLength);
        }
        Objects.requireNonNull(entityCounts, "entityCounts must not be null");
        Objects.requireNonNull(herbivoreStats, "herbivoreStats must not be null");
        Objects.requireNonNull(predatorStats, "predatorStats must not be null");
        int mapCapacity = mapSideLength * mapSideLength;
        if (entityCounts.initialTotal() >= mapCapacity) {
            throw new IllegalArgumentException("Initial entities " + entityCounts.initialTotal() + " must be fewer than map cells " + mapCapacity);
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

    public record EntityCounts(
            int initialTreeCount,
            int initialRockCount,
            int initialGrassCount,
            int initialHerbivoreCount,
            int initialPredatorCount,
            int grassSpawnPerTurn) {

        public EntityCounts {
            requireNonNegative("initialTreeCount", initialTreeCount);
            requireNonNegative("initialRockCount", initialRockCount);
            requireNonNegative("initialGrassCount", initialGrassCount);
            requireNonNegative("initialHerbivoreCount", initialHerbivoreCount);
            requireNonNegative("initialPredatorCount", initialPredatorCount);
            requireNonNegative("grassSpawnPerTurn", grassSpawnPerTurn);
        }

        public int initialTotal() {
            return initialTreeCount + initialRockCount + initialGrassCount + initialHerbivoreCount + initialPredatorCount;
        }
    }

    public record HerbivoreStats(
            int healthPoints,
            int speed) {

        public HerbivoreStats {
            requirePositive("healthPoints", healthPoints);
            requirePositive("speed", speed);
        }
    }

    public record PredatorStats(
            int healthPoints,
            int speed,
            int attackPower) {

        public PredatorStats {
            requirePositive("healthPoints", healthPoints);
            requirePositive("speed", speed);
            requirePositive("attackPower", attackPower);
        }
    }
}
