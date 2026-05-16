package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;

public abstract class Creature extends Entity {
    private int healthPoints;
    private int speed;
    public abstract void makeMove();

    public Creature(Symbol symbol, int healthPoints, int speed){
        super(symbol);
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
