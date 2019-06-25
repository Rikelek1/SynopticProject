package com.temenos.rlanouette.dungeoncrawler.entities;

public class Item {
    private String name;
    private String defeatingAction;
    private ItemType type;

    public Item(String name, String action, ItemType type) {
        this.setName(name);
        this.setDefeatingAction(action);
        this.setType(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefeatingAction() {
        return defeatingAction;
    }

    public void setDefeatingAction(String defeatingAction) {
        this.defeatingAction = defeatingAction;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
