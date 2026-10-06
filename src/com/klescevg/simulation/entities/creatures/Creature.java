package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Coordinates;
import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;

import java.util.Stack;

public abstract class Creature extends Entity {
    private int healthPoints;
    private final int speed;

    public Creature(Symbol symbol, int healthPoints, int speed) {
        super(symbol);
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public final void makeMove(WorldMap worldMap, PathFinder pathFinder){
        Stack<Coordinates> path = pathFinder.getPathToEntityBFS(this, getTargetType());

        if (path != null && !path.isEmpty()) {
            if ((path.size() - 1 <= speed)) {
                consumeTarget(worldMap, path);
            } else {
                moveAlongPath(worldMap, path);
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

    protected abstract Class<?> getTargetType();
    protected abstract void consumeTarget(WorldMap worldMap, Stack<Coordinates> path);

    protected void moveAlongPath(WorldMap worldMap, Stack<Coordinates> path) {
        Coordinates initialPosition = path.pop();
        Coordinates nextPosition = null;

        for (int i = 0; i < speed && !path.isEmpty(); i++) {
            nextPosition = path.pop();
        }

        worldMap.moveEntity(initialPosition, nextPosition);
    }
}
