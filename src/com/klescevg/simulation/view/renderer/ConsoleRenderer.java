package com.klescevg.simulation.view.renderer;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.Coordinates;

public class ConsoleRenderer implements Renderer {
    private static final String ANSI_DEFAULT = "\033[39m";
    private static final String ANSI_GREY = "\033[90m";
    private static final String ANSI_GREEN = "\033[32m";
    private static final String ANSI_LIGHT_GREEN = "\033[92m";
    private static final String ANSI_YELLOW = "\033[33m";
    private static final String ANSI_RED = "\033[31m";
    private static final String ANSI_RESET = "\033[0m";
    private static final String CELL_PADDING = " ";

    @Override
    public void render(WorldMap map) {
        for (int y = 0; y < map.sideLength(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.sideLength(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                line.append(renderCell(map, coordinates));
            }
            System.out.println(line);
        }
        System.out.println();
    }

    private String renderCell(WorldMap map, Coordinates coordinates) {
        Sprite sprite = map.getEntity(coordinates).map(this::toSprite).orElse(Sprite.EMPTY_CELL);
        return CELL_PADDING + sprite.toColoredString() + CELL_PADDING;
    }

    private Sprite toSprite(Entity entity) {
        String name = entity.getClass().getSimpleName().toUpperCase();
        return Sprite.valueOf(name);
    }

    private enum Sprite {
        EMPTY_CELL(".", ANSI_DEFAULT),
        ROCK("#", ANSI_GREY),
        TREE("T", ANSI_GREEN),
        GRASS("*", ANSI_LIGHT_GREEN),
        HERBIVORE("h", ANSI_YELLOW),
        PREDATOR("P", ANSI_RED);

        private final String symbol;
        private final String ansiColor;

        Sprite(String symbol, String ansiColor) {
            this.symbol = symbol;
            this.ansiColor = ansiColor;
        }

        public String toColoredString() {
            return ansiColor + symbol + ANSI_RESET;
        }
    }
}
