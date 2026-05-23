package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.creatures.Predator;

public class PredatorFactory implements EntityFactory{
    private int healthPoints;
    private int speed;
    private int attackPower;

    public PredatorFactory(int healthPoints, int speed, int attackPower) {
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.attackPower = attackPower;
    }

    public Predator create(){
        return new Predator(healthPoints, speed, attackPower);
    }
}
