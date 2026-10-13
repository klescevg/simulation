package com.klescevg.simulation;

import com.klescevg.simulation.world.*;

public class WorldMapConsoleRenderer {
    private final WorldMap worldMap;

    public WorldMapConsoleRenderer(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void render() {
        for (int y = 0; y < worldMap.sideLength(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < worldMap.sideLength(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                line.append(" ").append(worldMap.getEntity(coordinates).map(entity -> entity.symbol.getColoredIcon()).orElse(".")).append(" ");
            }
            System.out.println(line);
        }
        System.out.println();
    }
}
