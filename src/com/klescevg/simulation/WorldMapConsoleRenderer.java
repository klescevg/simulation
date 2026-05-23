package com.klescevg.simulation;

public class WorldMapConsoleRenderer {
    private final WorldMap worldMap;

    public WorldMapConsoleRenderer(WorldMap worldMap){
        this.worldMap = worldMap;
    }

    public void render() {
        for (int y = 0; y < worldMap.sideLength(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < worldMap.sideLength(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (!worldMap.isCellEmpty(coordinates)) {
                    line.append(" ").append(worldMap.getEntity(coordinates).symbol).append(" ");
                } else {
                    line.append(" _ ");
                }
            }
            System.out.println(line);
        }
        System.out.println();
    }
}
