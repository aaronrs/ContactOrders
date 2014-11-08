package net.astechdesign.clients.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        ClassLoader classLoader = Main.class.getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("fxml/main.fxml"));
        VBox main = fxmlLoader.load();

        primaryStage.setScene(new Scene(main, main.getPrefWidth(), main.getPrefHeight()));
        primaryStage.setTitle("Order Management");
        primaryStage.show();
    }

}
