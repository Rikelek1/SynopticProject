package com.temenos.rlanouette.dungeoncrawler.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private HashMap<Direction, Passage> passages;
    private ArrayList<Item> items;

    public Room() {
        this.setPassages(new HashMap<>());
        this.setItems(new ArrayList<>());
    }

    public HashMap<Direction, Passage> getPassages() {
        return passages;
    }

    public void setPassages(HashMap<Direction, Passage> passages) {
        this.passages = passages;
    }

    public void addPassage(Direction direction, Passage passage) {
        this.passages.put(direction, passage);
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}
