# Simulation

A console simulation of a 2D world. Herbivores look for grass, predators hunt herbivores, and the map is dotted with rocks and trees. New grass grows every turn.

This is a learning project focused on OOP and SOLID principles. It is written in plain Java with no external dependencies.

## Requirements

Java 21 or newer

## Build and run

From the project root:

```
javac -d out/production/simulation -sourcepath src src/com/klescevg/simulation/Main.java
java -cp out/production/simulation com.klescevg.simulation.Main
```

## Controls

| Input | Action |
|-------|--------|
| Enter | Start / pause / resume |
| `1` | Next turn (only while paused) |
| `x` | Stop (only while paused) |

## Configuration

Map size, entity counts and creature stats are set in `SimulationConfigProvider`.
