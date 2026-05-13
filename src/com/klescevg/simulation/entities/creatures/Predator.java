package com.klescevg.simulation.entities.creatures;

import com.klescevg.simulation.Symbol;

public class Predator extends Creature {
    private int attackPower;

    public Predator(int healthPoints, int speed, int attackPower){
        super(Symbol.P, healthPoints, speed);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove() {
        //if herbivore is near
        //  attack()
        //else
        //  moveToHerbivore()
    }
}
