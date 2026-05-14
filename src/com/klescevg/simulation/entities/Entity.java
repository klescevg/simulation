package com.klescevg.simulation.entities;

import com.klescevg.simulation.Symbol;

public abstract class Entity {
    public Symbol symbol;

    public Entity(Symbol symbol){
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object comparedObject){
        if (this == comparedObject){
            return true;
        }

        if (!(comparedObject instanceof Entity)){
            return false;
        }

        Entity comparedEntity = (Entity) comparedObject;

        if (this.symbol.equals(comparedEntity.symbol)){
            return true;
        }

        return false;
    }
}
