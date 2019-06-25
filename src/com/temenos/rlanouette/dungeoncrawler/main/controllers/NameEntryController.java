package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.main.models.GameContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NameEntryController {
    private GameContext gameContext = GameContext.getInstance();

    @FXML
    private BorderPane mainLayout;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtName;

    @FXML
    private void handleSaveButtonAction() {
        gameContext.getPlayer().setName(txtName.getText());
        gameContext.addProgramConfig("Playername", gameContext.getPlayer().getName());
        gameContext.saveProgramConfig();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}
