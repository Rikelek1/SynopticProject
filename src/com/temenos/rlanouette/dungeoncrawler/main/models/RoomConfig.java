package com.temenos.rlanouette.dungeoncrawler.main.models;

import com.temenos.rlanouette.dungeoncrawler.entities.Direction;
import com.temenos.rlanouette.dungeoncrawler.entities.Item;

import java.awt.*;
import java.util.ArrayList;

public class RoomConfig {
    private Point position;
    private ArrayList<Item> items;
    private ArrayList<PassageConfig> passages;

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<PassageConfig> getPassages() {
        return passages;
    }

    public void setPassages(ArrayList<PassageConfig> passages) {
        this.passages = passages;
    }
}
