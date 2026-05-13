package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Symbol;

public class Herbivore extends Creature {
    public Herbivore(int healthPoints, int speed){
        super(Symbol.H, healthPoints, speed);
    }

    @Override
    public void makeMove() {
        //if grass is near
        //  eat()
        //else
        //  moveToGrass();
    }
}
