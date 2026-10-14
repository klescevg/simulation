package com.klescevg.simulation.view.renderer;

import com.klescevg.simulation.world.*;

public class ConsoleRenderer implements Renderer {
    @Override
    public void render(WorldMap map) {
        for (int y = 0; y < map.sideLength(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.sideLength(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                line.append(" ").append(map.getEntity(coordinates).map(entity -> entity.symbol.getColoredIcon()).orElse(".")).append(" ");
            }
            System.out.println(line);
        }
        System.out.println();
    }
}
