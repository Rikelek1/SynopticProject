package com.temenos.rlanouette.dungeoncrawler.main.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.temenos.rlanouette.dungeoncrawler.entities.*;
import com.temenos.rlanouette.dungeoncrawler.main.util.Popup;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class GameContext {
    // Make a single, static instance of this GameContext
    private final static GameContext instance = new GameContext();

    public static GameContext getInstance() {
        return instance;
    }

    private GameContext() {
        validThreats = new ArrayList<>();
        validTreasures = new ArrayList<>();
        programProperties = new Properties();
        maze = new Maze();
        player = new Player();

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
    }

    private ArrayList<Threat>  validThreats;
    private ArrayList<Treasure> validTreasures;
    private File programConfigurationFile;
    private File mazeConfigurationFile;
    private Gson gson;
    private Maze maze;
    private Player player;
    private Properties programProperties;
    private Properties mazeConfigProperties;
    private String validationError;

    //region Program Config
    public void loadProgramConfig() {
        // Load the program configuration file into a file object
        programConfigurationFile = new File("res/config.properties");
        try {
            // Load the config file
            programProperties.load(createInputStream(programConfigurationFile));
            // Get the player name stored in the properties file
            String playerName = programProperties.getProperty("Playername");
            if (playerName != null) {
                // The config file has a player name in it, so set the context's player's name to it
                this.player.setName(playerName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProgramConfig() {
        try {
            // Create an output stream to write to the program configuration file
            FileOutputStream outputStream = new FileOutputStream(programConfigurationFile);
            System.out.println("Writing to " + programConfigurationFile.getAbsolutePath());
            // Write the program properties to the config file, this will update/append as necessary
            programProperties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProgramConfig(String key, String value) {
        programProperties.setProperty(key, value);
    }
    //endregion

    //region Threats and Treasures
    public void loadThreats() {
        // Create a new Gson JsonReader to read the threats.json file
        JsonReader reader = createJsonReader("res/threats.json");

        if(reader != null) {
            // The reader found the file

            // Create a new Type to use when reading the json file
            Type listType = new TypeToken<List<Threat>>() {}.getType();

            // Read the threats json file, using the listType which was just created as a model binder
            validThreats = gson.fromJson(reader, listType);
        }
    }

    public void loadTreasures() {
        // Create a new Gson JsonReader to read the treasures.json file
        JsonReader reader = createJsonReader("res/treasures.json");

        if(reader != null) {
            // The reader found the file

            // Create a new Type to use when reading the json file
            Type listType = new TypeToken<List<Treasure>>() {}.getType();

            // Read the treasures json file, using the listType which was just created as a model binder
            validTreasures = gson.fromJson(reader, listType);
        }
    }
    //endregion

    //region Helpers
    private InputStream createInputStream(File file) {
        // Declare a new FileInputStream object
        FileInputStream inputStream = null;
        try {
            // Try to attach the FileInputStream to the file provided
            inputStream = new FileInputStream(file);
            System.out.println("Loading file from " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    private JsonReader createJsonReader(String filename) {
        // Declare a new JsonReader object
        JsonReader reader = null;
        try {
            // Try to attach the JsonReader to the file provided
            reader = new JsonReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return reader;
    }

    public boolean validateConfig(File configFile) {
        JsonReader reader = createJsonReader(configFile.getAbsolutePath());
        Type roomListType = new TypeToken<List<RoomConfig>>() {}.getType();
        List<RoomConfig> rooms = gson.fromJson(reader, roomListType);
        boolean exitPassageProcessed = false;

        for(RoomConfig roomconfig : rooms) {
            Room room = new Room(roomconfig.getPosition());

            for(Item itemConfig : roomconfig.getItems()) {
                if(validTreasures.contains(itemConfig)) {
                    Item item = validTreasures.get(validTreasures.indexOf(itemConfig));
                    room.addItem(item);
                    continue;
                }

                if(validThreats.contains(itemConfig)) {
                    Item item = validThreats.get(validThreats.indexOf(itemConfig));
                    room.addItem(item);
                }
            }

            for(PassageConfig passageConfig : roomconfig.getPassages()) {
                Passage passage = new Passage(passageConfig.isExit());
                room.addPassage(passageConfig.getDirection(), passage);
                if(passageConfig.isExit()) {
                    if(exitPassageProcessed) {
                        validationError = "More than one exit passage present in this configuration file!";
                        System.out.println("More than one exit passage present in this configuration file!");
                        maze = new Maze();
                        return false;
                    }
                    exitPassageProcessed = true;
                }
            }

            if(room.getPosition() == null) {
                validationError = "This configuration file contains a room without a position!";
                System.out.println("This configuration file contains a room without a position!");
                maze = new Maze();
                return false;
            }

            if(maze.getRoom(room.getPosition()) != null) {
                validationError = String.format("More than one room is trying to use position %d, %d", room.getPosition().x, room.getPosition().y);
                System.out.println(String.format("More than one room is trying to use position %d, %d", room.getPosition().x, room.getPosition().y));
                maze = new Maze();
                return false;
            }

            maze.addRoom(room);
        }

        for(Room room : maze.getRooms()) {
            for(Map.Entry<Direction, Passage> passage : room.getPassages().entrySet()) {
                Point positionToCheck = new Point();
                positionToCheck.setLocation(room.getPosition().x, room.getPosition().y);
                switch(passage.getKey()) {
                    case NORTH:
                        positionToCheck.translate(0, -1);
                        break;
                    case EAST:
                        positionToCheck.translate(1, 0);
                        break;
                    case SOUTH:
                        positionToCheck.translate(0, 1);
                        break;
                    case WEST:
                        positionToCheck.translate(-1, 0);
                        break;
                }

                if((!passage.getValue().isExit()) && (isValidPosition(positionToCheck)) && (!maze.roomExists(positionToCheck))) {
                    validationError = String.format("Config Validation error! Room at position (%d, %d) has a passage " +
                            "connecting to a non-existent room! (%d, %d)", room.getPosition().x, room.getPosition().y, positionToCheck.x, positionToCheck.y);

                    System.out.println(String.format("Config Validation error! Room at position (%d, %d) has a passage " +
                            "connecting to a non-existent room! (%d, %d)", room.getPosition().x, room.getPosition().y, positionToCheck.x, positionToCheck.y));
                    maze = new Maze();
                    return false;
                }
            }
        }

        if(!exitPassageProcessed) {
            validationError = "No exit passage present in this configuration file!";

            System.out.println("No exit passage present in this configuration file!");
            maze = new Maze();
            return false;
        }

        return true;
    }

    public void reloadMaze() {
        maze = new Maze();
        this.validateConfig(this.getMazeConfigurationFile());
    }

    private boolean isValidPosition(Point position) {
        return (position.x >= 0 && position.y >= 0 && position.x <= 9 && position.y <= 9);
    }
    //endregion

    //region Getters and Setters
    public File getMazeConfigurationFile() {
        return mazeConfigurationFile;
    }

    public void setMazeConfigurationFile(File configurationFile) {
        this.mazeConfigurationFile = configurationFile;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Properties getProgramProperties() {
        return programProperties;
    }

    public void setProgramProperties(Properties programProperties) {
        this.programProperties = programProperties;
    }

    public Properties getMazeConfigProperties() {
        return mazeConfigProperties;
    }

    public void setMazeConfigProperties(Properties mazeConfigProperties) {
        this.mazeConfigProperties = mazeConfigProperties;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    //endregion
}
