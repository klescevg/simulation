import entities.objects.Grass;

public class Main {
    public static void main(String[] args) {
        Map map = new Map(10);
        Grass grass = new Grass();
        Coordinates grassCoordinates1 = new Coordinates(2,3);
        Coordinates grassCoordinates2 = new Coordinates(5,2);
        map.addEntity(grassCoordinates1, grass);
        map.addEntity(grassCoordinates2, grass);
        map.display();
        map.clearCell(grassCoordinates1);
        map.display();
    }
}