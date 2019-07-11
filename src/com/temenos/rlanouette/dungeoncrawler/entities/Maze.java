package com.temenos.rlanouette.dungeoncrawler.entities;

import java.awt.*;
import java.util.ArrayList;

public class Maze {
    private ArrayList<Room> rooms;
    private GridSquare[][] mazeGrid;

    public Maze() {
        // Initialize the maze with a new arraylist of rooms and a new empty maze grid
        this.rooms = new ArrayList<>();
        this.mazeGrid = new GridSquare[10][10];

        for (int i = 0; i < mazeGrid.length; i++) {
            for(int j = 0; j < mazeGrid[i].length; j++) {
                mazeGrid[i][j] = new GridSquare();
            }
        }
    }

    //region Helper Methods
    public boolean roomExists(Point position) {
        return this.mazeGrid[position.x][position.y].getRoom() != null;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        this.mazeGrid[room.getPosition().x][room.getPosition().y].setRoom(room);
    }

    public void removeRoom(Room room) {
        this.mazeGrid[room.getPosition().x][room.getPosition().y].removeRoom();
        this.rooms.remove(room);
    }
    //endregion

    //region Getters and Setters
    public Room getRoom(int index) {
        return this.getRooms().get(index);
    }

    public Room getRoom(Point position) {
        for(Room room : rooms) {
            if(room.getPosition().x == position.x && room.getPosition().y == position.y) {
                return room;
            }
        }

        return null;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public GridSquare[][] getMazeGrid() {
        return mazeGrid;
    }

    public void setMazeGrid(GridSquare[][] mazeGrid) {
        this.mazeGrid = mazeGrid;
    }
    //endregion
}
