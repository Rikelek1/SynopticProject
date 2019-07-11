package com.temenos.rlanouette.dungeoncrawler.main.controllers;

import com.temenos.rlanouette.dungeoncrawler.entities.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ItemSelectController {
    private Stage stage;
    private ArrayList<Item> items;
    private Item returnItem;

    @FXML
    private BorderPane mainLayout;

    @FXML
    private HBox targetsHBox;

    @FXML
    public void initialize(ArrayList<Item> items) {
        // Inject this instance into the GameController
        GameController.injectItemSelectController(this);

        // Set this instance's list of items to the same as the list provided, for use in other methods
        this.items = items;

        // Loop through each item in the list
        for (Item item : this.items) {
            if(item.getName().equals("Coin")) {
                continue;
            }
            // Create a button for the item and set it's action handler
            Button button = new Button(item.getName());
            button.setOnAction(this::handleTargetSelect);

            // Add the button to the targets HBox, to display in the window
            targetsHBox.getChildren().add(button);
        }
    }

    private void handleTargetSelect(ActionEvent event) {
        // Get the button which called this method and the Stage
        Button chosenButton = (Button)event.getSource();
        Stage stage = (Stage)chosenButton.getScene().getWindow();

        // Loop each item in the list of items
        for(Item item : items) {
            if ( item.getName().equals(chosenButton.getText())) {
                // The item name maches the one chosen by the player, so set it to the "returnItem"
               this.returnItem = item;
            }
        }

        // Close the stage, triggering the collection of the "returnItem" variable by the game controller
        stage.close();
    }

    public Item getReturnItem() {
        return returnItem;
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
