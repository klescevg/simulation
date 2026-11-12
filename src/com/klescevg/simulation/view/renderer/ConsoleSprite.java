package com.klescevg.simulation.view.renderer;

public enum ConsoleSprite {
    EMPTY_CELL("."),
    ROCK("#"),
    TREE("T"),
    GRASS("*"),
    HERBIVORE("h"),
    PREDATOR("P");

    private final String symbol;

    ConsoleSprite(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
