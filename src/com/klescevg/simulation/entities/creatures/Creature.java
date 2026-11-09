package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.world.WorldMap;
import com.klescevg.simulation.world.Coordinate;
import com.klescevg.simulation.entities.Entity;

public abstract class Creature extends Entity {
    private int healthPoints;
    private final int speed;

    public Creature(int healthPoints, int speed) {
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public final void makeMove(WorldMap map, PathfindingStrategy strategy) {
        Path path = strategy.find(map, getCurrentCoordinate(map), getTargetType());

        if (!path.isEmpty()) {
            if (path.getStepCount() <= speed) {
                interactWithTarget(map, path);
            } else {
                approachTarget(map, path);
            }
        }
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Damage must be positive, but was: " + amount);
        }
        healthPoints = Math.max(0, healthPoints - amount);
    }

    public void heal(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Heal amount must be positive, but was: " + amount);
        }
        healthPoints += amount;
    }

    public boolean isAlive() {
        return healthPoints > 0;
    }

    protected abstract Class<? extends Entity> getTargetType();

    protected abstract void interactWithTarget(WorldMap map, Path path);

    protected void moveTo(WorldMap map, Coordinate to) {
        Coordinate from = getCurrentCoordinate(map);

        if (!map.isCellEmpty(to)) {
            throw new IllegalStateException("Cell is occupied: " + to);
        }

        map.removeEntity(from);
        map.addEntity(to, this);
    }

    private Coordinate getCurrentCoordinate(WorldMap map) {
        return map.getCoordinate(this)
                .orElseThrow(() -> new IllegalStateException("Entity %s is not found on map".formatted(this.getClass().getSimpleName())));
    }

    private void approachTarget(WorldMap map, Path path) {
        moveTo(map, path.getCoordinateAfter(speed));
    }
}
