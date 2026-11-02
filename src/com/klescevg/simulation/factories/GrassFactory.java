package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.environment.Grass;

public class GrassFactory implements EntityFactory{
    public Grass create(){
        return new Grass();
    }
}
