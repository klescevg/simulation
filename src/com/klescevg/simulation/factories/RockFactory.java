package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.objects.Rock;

public class RockFactory implements EntityFactory{
    public Rock create(){
        return new Rock();
    }
}
