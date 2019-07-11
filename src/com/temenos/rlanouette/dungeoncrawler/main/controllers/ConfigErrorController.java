package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConfigErrorController {
    private Stage stage;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnOk;

    @FXML
    private Label lblErrorMessage;

    @FXML
    public void initialize() {
        initStage();
    }

    @FXML
    private void closeWindow() {
        stage.close();
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
                    }
                });
            }
        });
    }

    public void setLblMessage(String message) {
        this.lblErrorMessage.setText(message);
    }
}
