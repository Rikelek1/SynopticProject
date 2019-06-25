package com.temenos.rlanouette.dungeoncrawler.entities;

public class Threat extends Item {
    public Threat() {
        super("Threat", "Remove", ItemType.THREAT);
    }

    public Threat(String name, String action) {
        super(name, action, ItemType.THREAT);
    }
}
