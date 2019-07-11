package com.temenos.rlanouette.dungeoncrawler.entities;

public class GridSquare {
    private Room room;

    public GridSquare() {
        this.setRoom(null);
    }

    //region Getters and Setters
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    //endregion

    //region Helper Methods
    public void removeRoom() {
        this.setRoom(null);
    }
    //endregion
}
