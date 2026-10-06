package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.WorldMap;
import com.klescevg.simulation.PathFinder;
import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;

public abstract class Creature extends Entity {
    protected int healthPoints;
    protected final int speed;
    public abstract void makeMove(WorldMap worldMap, PathFinder pathFinder);

    public Creature(Symbol symbol, int healthPoints, int speed){
        super(symbol);
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void takeDamage(int amount){
        if (healthPoints > amount) {
            healthPoints -= amount;
        } else {
            healthPoints = 0;
        }
    }

    public void heal(int amount){
        healthPoints += amount;
    }

    public boolean isAlive(){
        return healthPoints > 0;
    }
}
