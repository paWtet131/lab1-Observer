package com.example.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 560, 520);
        stage.setTitle("Observer - TimeServer");
        stage.setScene(scene);
        stage.show();

        HelloController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(e -> controller.shutdown());
    }

    public static void main(String[] args) {
        launch();
    }
}