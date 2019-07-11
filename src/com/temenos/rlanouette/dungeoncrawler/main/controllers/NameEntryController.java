package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NameEntryController {
    private GameContext gameContext = GameContext.getInstance();

    private Stage stage;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnSave, btnCancel;

    @FXML
    private TextField txtName;

    @FXML
    public void initialize() {
        // Get the player name
        String playerName = gameContext.getPlayer().getName();
        if(!playerName.equals("")) {
            // The player already has a name, so put that in the text field for editing
            txtName.setText(playerName);
        }

        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 12) {
                txtName.setText(oldValue);
            }
        });
    }

    @FXML
    private void handleSaveButtonAction() {
        if(!txtName.getText().equals("")) {
            // The player has actually entered a name, so save it

            // Set the gameContext's player name to the one entered
            gameContext.getPlayer().setName(txtName.getText());
            // Add the player name to the game config, ready to save for name persistence between program launches
            gameContext.addProgramConfig("Playername", gameContext.getPlayer().getName());
            // Save the config to file
            gameContext.saveProgramConfig();
            // Close the window
            handleCancelButtonAction();
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        // Get the stage for the window and close it
        Stage stage = (Stage) btnCancel.getScene().getWindow();
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
