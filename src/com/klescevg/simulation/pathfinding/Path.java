package com.klescevg.simulation.pathfinding;

import com.klescevg.simulation.world.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Path {
    private final List<Coordinate> cells;

    private Path(List<Coordinate> cells) {
        this.cells = cells;
    }

    public static Path reconstruct(Map<Coordinate, Coordinate> parents, Coordinate start, Coordinate target) {
        List<Coordinate> cells = new ArrayList<>();
        Coordinate current = target;

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

    public Coordinate getStart() {
        return cells.getFirst();
    }

    public Coordinate getTarget() {
        return cells.getLast();
    }

    public int getStepCount() {
        return Math.max(cells.size() - 1, 0);
    }

    public Coordinate getPositionAfter(int stepCount) {
        return cells.get(Math.min(stepCount, cells.size() - 1));
    }
}
