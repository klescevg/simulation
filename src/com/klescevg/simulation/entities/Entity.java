package com.klescevg.simulation.entities;

import com.klescevg.simulation.Symbol;

public abstract class Entity {
    public Symbol symbol;

    public Entity(Symbol symbol){
        this.symbol = symbol;
    }
}
