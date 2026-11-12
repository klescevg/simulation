package com.klescevg.simulation.view.renderer;

import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;
import com.klescevg.simulation.entities.environment.*;

import java.util.Map;

public abstract class ConsoleRenderer implements Renderer {
    private static final String CELL_PADDING = " ";
    private static final String TURN_LABEL = "Turn: ";

    private static final Map<Class<? extends Entity>, ConsoleSprite> SPRITES = Map.of(
            Rock.class, ConsoleSprite.ROCK,
            Tree.class, ConsoleSprite.TREE,
            Grass.class, ConsoleSprite.GRASS,
            Herbivore.class, ConsoleSprite.HERBIVORE,
            Predator.class, ConsoleSprite.PREDATOR);

    @Override
    public void render(WorldMap map, int currentTurn) {
        startFrame();
        System.out.println(TURN_LABEL + currentTurn);
        for (int row = 0; row < map.getHeight(); row++) {
            StringBuilder line = new StringBuilder();
            for (int column = 0; column < map.getWidth(); column++) {
                Coordinate coordinate = new Coordinate(row, column);
                line.append(renderCell(map, coordinate));
            }
            System.out.println(line);
        }
        System.out.println();
    }

    private String renderCell(WorldMap map, Coordinate coordinate) {
        ConsoleSprite sprite = map.getEntity(coordinate).map(this::getSprite).orElse(ConsoleSprite.EMPTY_CELL);
        return CELL_PADDING + formatSprite(sprite) + CELL_PADDING;
    }

    private ConsoleSprite getSprite(Entity entity) {
        ConsoleSprite sprite = SPRITES.get(entity.getClass());
        if (sprite == null) {
            throw new IllegalArgumentException("Missing sprite for entity type: " + entity.getClass().getSimpleName());
        }
        return sprite;
    }

    protected abstract void startFrame();
    protected abstract String formatSprite(ConsoleSprite sprite);
}
