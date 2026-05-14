package com.klescevg.simulation;

import com.klescevg.simulation.entities.Entity;
import com.klescevg.simulation.entities.objects.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MapImp1 {
    private int sideLength;
    private final HashMap<Coordinates, Entity> map = new HashMap<>();

    public MapImp1(int length) {
        this.sideLength = length;

        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                map.put(new Coordinates(i, j), null);
            }
        }
    }

    public int sideLength() {
        return sideLength;
    }

    public void addEntity(Coordinates entityCoordinates, Entity entity) {
        map.putIfAbsent(entityCoordinates, entity);
/*        if (!map.containsKey(entityCoordinates)) {
            map.put(entityCoordinates, entity);
        } else {
            System.out.println("Cell is already full");
        }*/
    }

    public void moveEntity(Coordinates initialPosition, Coordinates newPosition) {
        if(!map.containsKey(newPosition)) {
            addEntity(newPosition, map.get(initialPosition));
            clearCell(initialPosition);
        } else {
            System.out.println("New position is already full");
        }
    }

    //работает даже при вложении нового объекта координат
    public Entity getEntity(Coordinates coordinates){
        return map.get(coordinates);
    }

    public Coordinates getEntityCoordinates(Entity entity){
        for (Coordinates c : map.keySet()){
            if (map.get(c) == entity){
                return c;
            }
        }
        return null;
    }

    public Collection<Entity> getEntities(){
        return map.values();
    }

    public Set<Coordinates> getCoordinates(){
        return map.keySet();
    }

    public Set<Coordinates> getBorderCells(Coordinates coordinates){
        Set<Coordinates> borderCells = new HashSet<>();

        Coordinates left = new Coordinates(coordinates.x()-1, coordinates.y());
        Coordinates right = new Coordinates(coordinates.x()+1, coordinates.y());
        Coordinates top = new Coordinates(coordinates.x(), coordinates.y()-1);
        Coordinates bottom = new Coordinates(coordinates.x(), coordinates.y()+1);

        if (map.containsKey(left)){
            borderCells.add(left);
        }

        if (map.containsKey(right)){
            borderCells.add(right);
        }

        if (map.containsKey(top)){
            borderCells.add(top);
        }

        if (map.containsKey(bottom)){
            borderCells.add(bottom);
        }

        return borderCells;
    }

    public boolean isCellEmpty(Coordinates coordinates){
        return map.get(coordinates) == null;
    }

    public void clearCell(Coordinates coordinates) {
        map.remove(coordinates);
    }

    public void display() {
        for (int y = 0; y < sideLength; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < sideLength; x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (!isCellEmpty(coordinates)) {
                    line.append(" ").append(map.get(coordinates).symbol).append(" ");
                } else {
                    line.append(" _ ");
                }
            }
            System.out.println(line);
        }

        for (Coordinates i : map.keySet()) {
            System.out.print(i + " - ");
            System.out.println(map.get(i));
        }
    }
}
