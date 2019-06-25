package com.temenos.rlanouette.dungeoncrawler.entities;

public class Treasure extends Item {
    private int value;

    public Treasure() {
        super("Treasure", "Pick Up", ItemType.TREASURE);
        this.setValue(0);
    }

    public Treasure(String name, String action, int value) {
        super(name, action, ItemType.TREASURE);
        this.setValue(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
