package com.temenos.rlanouette.dungeoncrawler.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // Default entry point for JavaFx
    @Override
    public void start(Stage primaryStage) throws Exception {


        // Load the Title fxml and configure and display the window
        Parent root = FXMLLoader.load(Main.class.getResource("/com/temenos/rlanouette/dungeoncrawler/main/views/Title.fxml"));
        primaryStage.setTitle("Olde Worlde Phunne - Maze Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
