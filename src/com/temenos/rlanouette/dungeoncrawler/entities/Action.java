package com.temenos.rlanouette.dungeoncrawler.entities;

public enum Action {
    ATTACK("Attack"),
    SAVE("Save"),
    DEFUSE("Defuse"),
    REMOVE("Remove"),
    PICK_UP("Pick Up"),
    DROP_COIN("Drop Coin");

    private String action;

    Action(String actionString) {
        this.action = actionString;
    }

    public String printString() {
        return this.action;
    }
}
