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
        Coordinate currentCoordinate = map.getCoordinate(this)
                .orElseThrow(() -> new IllegalStateException("Entity %s is not found on map".formatted(this.getClass().getSimpleName())));
        Path path = strategy.find(map, currentCoordinate, getTargetType());

        if (!path.isEmpty()) {
            if (path.getStepCount() <= speed) {
                interactWithTarget(map, path);
            } else {
                moveToTarget(map, path);
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

    private void moveToTarget(WorldMap map, Path path) {
        Coordinate start = path.getStart();

        map.moveEntity(start, path.getCoordinateAfter(speed));
    }
}
