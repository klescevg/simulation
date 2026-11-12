package com.klescevg.simulation.view.renderer;

public class ColorConsoleRenderer extends ConsoleRenderer{
    private static final String ANSI_DEFAULT = "\033[39m";
    private static final String ANSI_GREY = "\033[90m";
    private static final String ANSI_GREEN = "\033[32m";
    private static final String ANSI_LIGHT_GREEN = "\033[92m";
    private static final String ANSI_YELLOW = "\033[33m";
    private static final String ANSI_RED = "\033[31m";
    private static final String ANSI_RESET = "\033[0m";

    private static final String ANSI_CLEAR_CONSOLE = "\033[H\033[2J";

    @Override
    protected void startFrame() {
        System.out.print(ANSI_CLEAR_CONSOLE);
    }

    @Override
    protected String formatSprite(ConsoleSprite sprite) {
        String ansiColor = switch (sprite) {
            case EMPTY_CELL -> ANSI_DEFAULT;
            case ROCK -> ANSI_GREY;
            case TREE -> ANSI_GREEN;
            case GRASS -> ANSI_LIGHT_GREEN;
            case HERBIVORE -> ANSI_YELLOW;
            case PREDATOR -> ANSI_RED;
        };
        return ansiColor + sprite.getSymbol() + ANSI_RESET;
    }
}
