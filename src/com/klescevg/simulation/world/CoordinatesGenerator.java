package com.klescevg.simulation.world;

import java.util.Random;

public class CoordinatesGenerator {
    private static final Random RANDOM = new Random();

    public static Coordinates random(int bound){
        return new Coordinates(RANDOM.nextInt(bound), RANDOM.nextInt(bound));
    }
}
