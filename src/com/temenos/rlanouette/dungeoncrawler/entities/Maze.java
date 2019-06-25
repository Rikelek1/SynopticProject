package com.temenos.rlanouette.dungeoncrawler.entities;

import java.util.ArrayList;

public class Maze {
    private ArrayList<Room> rooms;

    public Maze() {
        this.rooms = new ArrayList<>();
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
