package com.petcare.petcare.Scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.petcare.petcare.Controllers.LauncherController;

public class Register extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("register.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(getClass().getResource("register.fxml").openStream());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        LauncherController mainController = fxmlLoader.getController();
        mainController.setStage(stage);
        mainController.showStage();
    }

    public void launch() {
        launch();
    }
}