package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import com.temenos.rlanouette.dungeoncrawler.main.util.Popup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GameCompleteController {
    private Stage stage;
    private Parent root;

    private GameContext gameContext = GameContext.getInstance();

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnNewGame, btnNewConfig, btnMainMenu, btnQuit;

    @FXML
    private Label lblFinalWealth;

    @FXML
    public void initialize() {
        initStage();

        lblFinalWealth.setText("$" + String.valueOf(gameContext.getPlayer().getWealth()));
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
    private void handleLoadNewConfigAction() {
        // Open a file selection window which is limited to json files
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select configuration file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File configFile = fileChooser.showOpenDialog(stage);

        if(configFile != null && gameContext.validateConfig(configFile)) {
            gameContext.setMazeConfigurationFile(configFile);
        } else if(configFile != null) {
            Popup.showSimpleError("Configuration Error", gameContext.getValidationError(), stage);
        }
    }

    @FXML
    private void handleMainMenuAction() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/temenos/rlanouette/dungeoncrawler/main/views/Title.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleQuitButtonAction() {
        stage.close();
    }

    //region Initialisation
    private void initStage() {
        // Add a listener for the scene property of the "mainLayout" BorderPane.
        mainLayout.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // Scene is set for the first time so listen for stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // Stage is set so we can now use it in the controller.
                        stage = (Stage)mainLayout.getScene().getWindow();

//                        stage.centerOnScreen();
                    }
                });
            }
        });
    }
    //endregion
}
