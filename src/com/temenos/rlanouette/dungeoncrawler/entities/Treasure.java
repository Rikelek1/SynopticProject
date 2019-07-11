package com.temenos.rlanouette.dungeoncrawler.entities;

public class Treasure extends Item {
    private int value;

    public Treasure() {
        super("Treasure", Action.PICK_UP, ItemType.TREASURE);
        this.setValue(0);
    }

    public Treasure(String name, int value) {
        super(name, Action.PICK_UP, ItemType.TREASURE);
        this.setValue(value);
    }

    //region Getters and Setters
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    //endregion
}
