package com.klescevg.simulation.factories;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.EntityType;
import com.klescevg.simulation.entities.creatures.Herbivore;
import com.klescevg.simulation.entities.creatures.Predator;
import com.klescevg.simulation.entities.environment.Grass;
import com.klescevg.simulation.entities.environment.Rock;
import com.klescevg.simulation.entities.environment.Tree;

public class EntityFactory {
    public Entity create(EntityType type){
        return switch (type) {
            case TREE -> new Tree();
            case ROCK -> new Rock();
            case GRASS -> new Grass();
            case HERBIVORE -> new Herbivore();
            case PREDATOR -> new Predator();
        };
    }
}

