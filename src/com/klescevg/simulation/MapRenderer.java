package com.klescevg.simulation;

public class MapRenderer {
    private final Map map;

    public MapRenderer(Map map){
        this.map = map;
    }

    public void displayInTerminal() {
        for (int y = 0; y < map.sideLength(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.sideLength(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (!map.isCellEmpty(coordinates)) {
                    line.append(" ").append(map.getEntity(coordinates).symbol).append(" ");
                } else {
                    line.append(" _ ");
                }
            }
            System.out.println(line);
        }
        System.out.println();
    }
}
