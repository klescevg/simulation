package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Path {
    private final List<Coordinates> cells;

    private Path(List<Coordinates> cells) {
        this.cells = cells;
    }

    public static Path fromParents(Map<Coordinates, Coordinates> parents, Coordinates start, Coordinates target) {
        List<Coordinates> cells = new ArrayList<>();
        Coordinates current = target;

        while (current != null && !current.equals(start)) {
            cells.add(current);
            current = parents.get(current);
        }

        cells.add(start);
        Collections.reverse(cells);

        return new Path(cells);
    }

    public static Path empty() {
        return new Path(List.of());
    }

    public boolean isEmpty() {
        return cells.isEmpty();
    }

    public int steps() {
        return Math.max(cells.size() - 1, 0);
    }

    public Coordinates start() {
        return cells.getFirst();
    }

    public Coordinates target() {
        return cells.getLast();
    }

    public Coordinates positionAfter(int stepCount) {
        return cells.get(Math.min(stepCount, cells.size() - 1));
    }
}
