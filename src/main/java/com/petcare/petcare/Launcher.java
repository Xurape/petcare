package com.petcare.petcare;

import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Seeder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import com.petcare.petcare.Controllers.LauncherController;
import com.petcare.petcare.Exceptions.CouldNotDeserializeException;
import com.petcare.petcare.Utils.Storage;

public class Launcher extends Application {
    /**
     *
     * Try deserializing the storage
     *
     * @param filename Name of the file to deserialize
     *
     */
    public static void tryDeserializing(String filename) {
        try {
            Storage.deserialize(filename);
        } catch (CouldNotDeserializeException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao executar o programa");
            alert.setContentText("Não foi possível carregar os dados atuais.");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     *
     * @throws IOException IOException
     *
     */
    @Override
    public void start(Stage stage) throws IOException {
        Storage storage;
        tryDeserializing("./src/main/resources/data/storage.db");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")).openStream());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        LauncherController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.showStage();

        long pid = ProcessHandle.current().pid();
        Debug.print("Application started - Process [ " + pid + " ]", true, true);

        Seeder.seed();
    }

    /**
     *
     * The main() method is ignored in correctly deployed JavaFX application.
     *
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        launch();
    }
}