package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.pathfinding.Path;
import com.klescevg.simulation.pathfinding.PathfindingStrategy;
import com.klescevg.simulation.world.*;
import com.klescevg.simulation.entities.Entity;

public abstract class Creature extends Entity {
    private int healthPoints;
    private final int speed;

    public Creature(int healthPoints, int speed) {
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public final void makeMove(WorldMap worldMap, PathfindingStrategy strategy) {
        Path path = strategy.find(this, getTargetType());

        if (!path.isEmpty()) {
            if (path.getStepCount() <= speed) {
                consumeTarget(worldMap, path);
            } else {
                moveToTarget(worldMap, path);
            }
        }
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Damage must be positive, got: " + amount);
        }

        healthPoints = Math.max(0, healthPoints - amount);
    }

    public void heal(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Heal amount must be positive, got: " + amount);
        }

        healthPoints += amount;
    }

    public boolean isAlive() {
        return healthPoints > 0;
    }

    protected abstract Class<? extends Entity> getTargetType();

    protected abstract void consumeTarget(WorldMap worldMap, Path path);

    protected void moveToTarget(WorldMap worldMap, Path path) {
        Coordinate initialPosition = path.getStart();

        worldMap.moveEntity(initialPosition, path.getPositionAfter(speed));
    }
}
