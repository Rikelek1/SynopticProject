package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import com.temenos.rlanouette.dungeoncrawler.main.util.Popup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class TitleController {
    private GameContext gameContext = GameContext.getInstance();

    private Stage stage;
    private Parent root;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnNewGame, btnLeaderBoard, btnLoadConfig, btnChangeName, btnExit;

    @FXML
    private Label lblConfigFile, lblPlayerName;

    @FXML
    private GridPane informationGridPane;

    @FXML
    private void handleExitButtonAction() {
        gameContext.saveProgramConfig();
        stage.close();
    }

    @FXML
    private void handleNewGameAction() {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/temenos/rlanouette/dungeoncrawler/main/views/Game.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleConfigFileLoadAction() {
        // Open a file selection window which is limited to json files
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select configuration file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File configFile = fileChooser.showOpenDialog(stage);

        if(configFile != null && gameContext.validateConfig(configFile)) {
            gameContext.setMazeConfigurationFile(configFile);
            lblConfigFile.setText(gameContext.getMazeConfigurationFile().getName());
            btnNewGame.setDisable(false);
        } else {
            Popup.showSimpleError("Configuration Error", gameContext.getValidationError(), stage);
        }
    }

    @FXML
    private void handleNameChangeAction() {
        // Display a name change popup and display the result in the bottom right corner
        Popup.show("NameEntry", "Player name", stage);
        setPlayerNameLabel();
    }

    @FXML
    public void initialize() {
        // Load the program configuration files
        gameContext.loadProgramConfig();
        gameContext.loadThreats();
        gameContext.loadTreasures();

        // Make the player name right-align
        GridPane.setHalignment(lblPlayerName, HPos.RIGHT);

        // Disable the "New Game" button if there is no valid configuration file loaded
        if (gameContext.getMazeConfigurationFile() == null) {
            btnNewGame.setDisable(true);
        } else {
            lblConfigFile.setText(gameContext.getMazeConfigurationFile().getName());
        }

        initStage();
    }

    private void initStage() {
        // Add a listener for the scene property of the "mainLayout" BorderPane.
        mainLayout.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // Scene is set for the first time so listen for stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // Stage is set so we can now use it in the controller.
                        stage = (Stage)mainLayout.getScene().getWindow();

                        informationGridPane.prefWidthProperty().bind(stage.widthProperty());

                        if(gameContext.getPlayer().getName().equals("")) {
                            // Player name is empty, so prompt the player for it.
                            Popup.show("NameEntry", "Player name", stage);
                        }

                        // Set the player name once it's available.
                        setPlayerNameLabel();

//                        stage.centerOnScreen();
                    }
                });
            }
        });
    }

    private void setPlayerNameLabel() {
        lblPlayerName.setText(gameContext.getPlayer().getName());
    }
}
