package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.creatures.Herbivore;

public class HerbivoreFactory implements EntityFactory{
    private final int healthPoints;
    private final int speed;

    public HerbivoreFactory(int healthPoints, int speed){
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    public Herbivore create(){
        return new Herbivore(healthPoints, speed);
    }
}
