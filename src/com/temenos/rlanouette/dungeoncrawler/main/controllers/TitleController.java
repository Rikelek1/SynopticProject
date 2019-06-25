package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import com.temenos.rlanouette.dungeoncrawler.main.util.Popup;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class TitleController {
    private GameContext gameContext = GameContext.getInstance();

    private Stage stage;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnLeaderBoard;

    @FXML
    private Button btnLoadConfig;

    @FXML
    private Button btnChangeName;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblConfigFile;

    @FXML
    private Label lblPlayerName;

    @FXML
    private GridPane informationGridPane;

    @FXML
    private VBox mainMenuVBox;

    @FXML
    private void handleExitButtonAction() {
        gameContext.saveProgramConfig();
        stage.close();
    }

    @FXML
    private void handleConfigFileLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select configuration file");
        File configFile = fileChooser.showOpenDialog(stage);

        if(configFile != null) {
            gameContext.setMazeConfigurationFile(configFile);
            lblConfigFile.setText(gameContext.getMazeConfigurationFile().getName());
            btnNewGame.setDisable(false);
        }
    }

    @FXML
    private void handleNameChangeAction() {
        Popup.show("NameEntry", "Player name", stage);
        setPlayerName();
    }

    @FXML
    public void initialize() {
        // Load the program configuration file
        gameContext.loadProgramConfig();

        gameContext.saveProgramConfig();

        // Make the player name right-align
        GridPane.setHalignment(lblPlayerName, HPos.RIGHT);

        if (gameContext.getMazeConfigurationFile() == null) {
            btnNewGame.setDisable(true);
        }

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
                        setPlayerName();
                    }
                });
            }
        });
    }

    private void setPlayerName() {
        lblPlayerName.setText(gameContext.getPlayer().getName());
    }
}
