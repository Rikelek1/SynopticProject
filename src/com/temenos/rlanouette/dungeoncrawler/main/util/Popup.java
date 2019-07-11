package com.temenos.rlanouette.dungeoncrawler.main.util;

import com.temenos.rlanouette.dungeoncrawler.entities.Item;
import com.temenos.rlanouette.dungeoncrawler.main.controllers.ConfigErrorController;
import com.temenos.rlanouette.dungeoncrawler.main.controllers.ItemSelectController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Popup {
    // General-use popup, specify the name of the .fxml view file you want the popup to use, the title and the owning
    // stage
    public static void show(String view, String title, Stage owner) {
        Stage stage = new Stage();
        try {
            // try to create a new root node from the view file given
            Parent root = FXMLLoader.load(Popup.class.getResource(String.format("../views/%s.fxml", view)));

            // try to set the scene for the window to a new scene which uses the newly-created root node
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.showAndWait();
    }

    public static void showSimpleError(String title, String message, Stage owner) {
        Stage stage = new Stage();
        BorderPane mainLayout = new BorderPane();
        VBox mainContainer = new VBox(10);
        Label lblMessage = new Label(message);
        Button btnClose = new Button("Ok");

        mainContainer.setAlignment(Pos.CENTER);

        lblMessage.setWrapText(true);
        lblMessage.setAlignment(Pos.CENTER);
        lblMessage.setTextAlignment(TextAlignment.CENTER);
        btnClose.setOnAction(event -> stage.close());

        mainContainer.getChildren().addAll(lblMessage, btnClose);

        mainLayout.setCenter(mainContainer);

        stage.setScene(new Scene(mainLayout));
        stage.setTitle(title);
        stage.setWidth(300);
        stage.setHeight(165);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.showAndWait();
    }

    public static void selectItemDisplay(ArrayList<Item> items, String title, Stage owner) {
        Stage stage = new Stage();
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(Popup.class.getResource("../views/ItemSelect.fxml"));

            Parent root = loader.load();

            ItemSelectController controller = loader.getController();
            controller.initialize(items);

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.showAndWait();
    }
}
