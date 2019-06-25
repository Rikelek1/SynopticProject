package com.temenos.rlanouette.dungeoncrawler.main.models;

import com.temenos.rlanouette.dungeoncrawler.entities.Maze;
import com.temenos.rlanouette.dungeoncrawler.entities.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class GameContext {
    private final static GameContext instance = new GameContext();

    public static GameContext getInstance() {
        return instance;
    }

    private File programConfigurationFile;
    private File mazeConfigurationFile;
    private Maze maze = new Maze();
    private Player player = new Player();
    private Properties programProperties = new Properties();
    private Properties mazeConfigProperties = new Properties();

    public void loadProgramConfig() {
        programConfigurationFile = new File("res/config.properties");
        try {
            FileInputStream inputStream = new FileInputStream(programConfigurationFile);
            System.out.println("Loading file from " + programConfigurationFile.getAbsolutePath());
            programProperties.load(inputStream);
            this.player.setName(programProperties.getProperty("Playername"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveProgramConfig() {
        try {
            FileOutputStream outputStream = new FileOutputStream(programConfigurationFile);
            System.out.println("Writing to " + programConfigurationFile.getAbsolutePath());
            programProperties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProgramConfig(String key, String value) {
        programProperties.setProperty(key, value);
    }

    public File getMazeConfigurationFile() {
        return mazeConfigurationFile;
    }

    public void setMazeConfigurationFile(File configurationFile) {
        this.mazeConfigurationFile = configurationFile;
        Reader configReader;
        try {
            configReader = Files.newBufferedReader(Paths.get(configurationFile.getAbsolutePath()));
            mazeConfigProperties.load(configReader);
            configReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
