package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.environment.Tree;

public class TreeFactory implements EntityFactory{
    public Tree create(){
        return new Tree();
    }
}
