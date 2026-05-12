import entities.Entity;

import java.util.HashMap;

public class Map {
    private int sideLength;
    private final HashMap<Coordinates, Entity> map = new HashMap<>();

    public Map(int length) {
        this.sideLength = length;
    }

    public void addEntity(Coordinates entityCoordinates, Entity entity){
        if (entityCoordinates.x() < sideLength && entityCoordinates.y() < sideLength) {
            map.put(entityCoordinates, entity);
        }
    }

    public void clearCell(Coordinates coordinates){
        map.remove(coordinates);
    }

    public void display(){
        for (int y = 1; y <= sideLength; y++){
            for (int x = 1; x <= sideLength; x++) {
                if (map.containsKey(new Coordinates(x, y))){
                    System.out.print(" X ");
                } else {
                    System.out.print(" _ ");
                }
            }
            System.out.println();
        }

        for (Coordinates i : map.keySet()){
            System.out.print(i + " - ");
            System.out.println(map.get(i));
        }
    }
}
