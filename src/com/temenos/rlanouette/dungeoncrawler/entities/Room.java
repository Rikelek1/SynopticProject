package com.temenos.rlanouette.dungeoncrawler.entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private HashMap<Direction, Passage> passages;
    private ArrayList<Item> items;
    private Point position;

    public Room() {
        this.setPassages(new HashMap<>());
        this.setItems(new ArrayList<>());
        this.setPosition(new Point(0, 0));
    }

    public Room(Point position) {
        this.setPassages(new HashMap<>());
        this.setItems(new ArrayList<>());
        this.setPosition(position);
    }

    //region Helper Methods
    public boolean validPassage(Direction direction) {
        return this.getPassages().containsKey(direction);
    }

    public boolean hasThreats() {
        return this.getThreats().size() > 0;
    }

    public boolean hasTreasures() {
        return this.getTreasures().size() > 0;
    }

    public boolean hasCoin() {
        boolean result = false;
        for(Item item : items) {
            if(item.getName().equals("Coin")) {
                result = true;
            }
        }

        return result;
    }

    public boolean hasItems() {
        return this.getItems().size() > 0;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public void addPassage(Direction direction, Passage passage) {
        this.passages.put(direction, passage);
    }
    //endregion

    //region Getters and Setters
    public HashMap<Direction, Passage> getPassages() {
        return passages;
    }

    public Passage getPassage(Direction direction) {
        return this.getPassages().get(direction);
    }

    public void setPassages(HashMap<Direction, Passage> passages) {
        this.passages = passages;
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> result = new ArrayList<>();

        for(Item item : this.items) {
            if(!item.getName().equals("Coin")) {
                result.add(item);
            }
        }
        return result;
    }

    public ArrayList<Item> getItems(ItemType type) {
        ArrayList<Item> result = new ArrayList<>();

        for(Item item : items) {
            if(item.getType() == type) {
                result.add(item);
            }
        }

        return result;
    }

    public Item getCoin() {
        Item result = null;

        for(Item item : this.items) {
            if(item.getName().equals("Coin")) {
                result = item;
            }
        }

        return result;
    }

    public Item getItem(Item item) {
        if (items.contains(item)) {
            return items.get(items.indexOf(item));
        }

        return null;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Threat> getThreats() {
        ArrayList<Threat> returnList = new ArrayList<>();

        for(Item item : items) {
            if(item.getType() == ItemType.THREAT) {
                returnList.add((Threat) item);
            }
        }

        return returnList;
    }

    public ArrayList<Treasure> getTreasures() {
        ArrayList<Treasure> returnList = new ArrayList<>();

        for(Item item : items) {
            if(item.getType() == ItemType.TREASURE) {
                returnList.add((Treasure) item);
            }
        }

        return returnList;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    //endregion
}
