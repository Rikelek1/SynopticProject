package com.temenos.rlanouette.dungeoncrawler.entities;

public class Player {
    private String name;
    private int wealth;
    private Room room;

    public Player() {
        this.setName("");
        this.setWealth(0);
        this.setRoom(new Room());
    }

    public Player(String name, Room room) {
        this.setName(name);
        this.setWealth(0);
        this.setRoom(room);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
