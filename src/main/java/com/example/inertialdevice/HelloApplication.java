package com.example.inertialdevice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        var device = new Device(600,400);
        ((AnchorPane)fxmlLoader.getRoot()).getChildren().add(device);
        ((ViewController)fxmlLoader.getController()).setDevice(device);
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            device.disable();
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}