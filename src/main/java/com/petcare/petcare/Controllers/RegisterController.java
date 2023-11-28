package com.petcare.petcare.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RegisterController {
    Stage thisStage;

    @FXML
    ChoiceBox tipoConta;

    public void initialize() {
        if (tipoConta != null) {
            tipoConta.getItems().addAll("Prestador de serviço", "Cliente");
        }
    }

    public void setStage (Stage stage){
        thisStage = stage;
    }

    public void showStage(){
        thisStage.setTitle("PetCare - Registo");
        thisStage.show();
    }

    @FXML
    protected void goBack(ActionEvent event) throws Exception {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/login.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                LauncherController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'login.fxml' not found.");
        } 
    }

    @FXML
    protected void registerUser(ActionEvent event) throws Exception {

    }
}