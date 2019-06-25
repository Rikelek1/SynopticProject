package com.temenos.rlanouette.dungeoncrawler.entities;

public class Passage {
    private boolean exit;

    public Passage() {
        this.setExit(false);
    }

    public Passage(boolean exit) {
        this.setExit(exit);
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
