package com.temenos.rlanouette.dungeoncrawler.entities;

public class Threat extends Item {
    public Threat() {
        super("Threat", Action.REMOVE, ItemType.THREAT);
    }

    public Threat(String name, Action action) {
        super(name, action, ItemType.THREAT);
    }
}
