package com.petcare.petcare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.petcare.petcare.Controllers.LauncherController;

public class Launcher extends Application {

    /**
     *
     * COLORS:
     * Left - #02223A
     * Middle - #0E2A47
     * Right - #113255
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(getClass().getResource("login.fxml").openStream());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        LauncherController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.showStage();
    }

    public static void main(String[] args) {
        launch();
    }
}