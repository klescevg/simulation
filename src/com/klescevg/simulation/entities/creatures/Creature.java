package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Symbol;
import com.klescevg.simulation.entities.Entity;

public abstract class Creature extends Entity {
    private int speed;
    private int healthPoints;
    public abstract void makeMove();

    public Creature(){
        super(Symbol.C);
    }
}
