package com.klescevg.simulation;

public enum Symbol {
    G("*", "\033[32m"),
    R("#", "\033[90m"),
    T("T", "\033[92m"),
    H("h", "\033[33m"),
    P("P", "\033[31m");

    private static final String RESET = "\033[0m";

    private final String icon;
    private final String color;

    Symbol(String icon, String color) {
        this.icon = icon;
        this.color = color;
    }

    public String getColoredIcon() {
        return color + icon + RESET;
    }
}
