package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.environment.Rock;

public class RockFactory implements EntityFactory{
    public Rock create(){
        return new Rock();
    }
}
