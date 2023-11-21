package com.petcare.petcare.Scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Register extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Register.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 810, 385);
        stage.setTitle("PetCare - Registo");
        stage.setScene(scene);
        stage.show();
    }

    public void launch() {
        launch();
    }
}