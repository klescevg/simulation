package com.klescevg.simulation.view.renderer;

public class MonochromeConsoleRenderer extends ConsoleRenderer{
    @Override
    protected void startFrame() {
        System.out.println();
    }

    @Override
    protected String formatSprite(ConsoleSprite sprite) {
        return sprite.getSymbol();
    }
}
