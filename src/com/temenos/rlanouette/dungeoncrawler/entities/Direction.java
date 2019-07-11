package com.temenos.rlanouette.dungeoncrawler.entities;

public enum Direction {
    NORTH("North"),
    EAST("East"),
    SOUTH("South"),
    WEST("West");

    private String direction;

    Direction(String directionString) {
        this.direction = directionString;
    }

    public String printString() {
        return this.direction;
    }
}
