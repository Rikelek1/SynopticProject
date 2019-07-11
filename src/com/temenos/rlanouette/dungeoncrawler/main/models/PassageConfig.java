package com.temenos.rlanouette.dungeoncrawler.main.models;

import com.temenos.rlanouette.dungeoncrawler.entities.Direction;

public class PassageConfig {
    private Direction direction;
    private boolean exit;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
