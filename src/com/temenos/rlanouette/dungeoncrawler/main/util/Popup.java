package com.temenos.rlanouette.dungeoncrawler.main.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Popup {
    public static void show(String view, String title, Stage owner) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(Popup.class.getResource(String.format("../views/%s.fxml", view)));

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.showAndWait();
    }
}
