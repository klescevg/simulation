package com.klescevg.simulation.world;

import java.util.Random;

public class CoordinateGenerator {
    private static final Random RANDOM = new Random();

    public static Coordinate random(int bound){
        return new Coordinate(RANDOM.nextInt(bound), RANDOM.nextInt(bound));
    }
}
