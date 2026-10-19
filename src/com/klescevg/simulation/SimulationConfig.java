package com.klescevg.simulation;

public record SimulationConfig(
        int mapSideLength,
        EntityCounts entityCounts,
        HerbivoreStats herbivoreStats,
        PredatorStats predatorStats) {

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
