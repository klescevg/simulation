package com.klescevg.simulation;

public record SimulationConfig(
        int mapSideLength,
        EntityCounts entityCounts,
        HerbivoreStats herbivoreStats,
        PredatorStats predatorStats) {

    public record EntityCounts(
            int initialTreeCount,
            int initialRockCount,
            int initialGrassCount,
            int initialHerbivoreCount,
            int initialPredatorCount,
            int grassSpawnPerTurn) {
    }

    public record HerbivoreStats(
            int healthPoints,
            int speed) {
    }

    public record PredatorStats(
            int healthPoints,
            int speed,
            int attackPower) {
    }
}
