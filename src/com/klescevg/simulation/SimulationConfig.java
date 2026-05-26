package com.klescevg.simulation;

public record SimulationConfig (int treeCount, int rockCount, int grassCount, int herbivoreCount,
                                int predatorCount, int herbivoreHealthPoints, int herbivoreSpeed,
                                int predatorHealthPoints, int predatorSpeed, int predatorAttackPower){
}
