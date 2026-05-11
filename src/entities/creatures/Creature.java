package entities.creatures;

import entities.Entity;

public abstract class Creature extends Entity {
    private int speed;
    private int healthPoints;
    public abstract void makeMove();
}
