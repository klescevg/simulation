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
}
