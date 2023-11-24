package com.petcare.petcare.Controllers;
import com.petcare.petcare.Scenes.Register;
import com.petcare.petcare.Users.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class LauncherController {
    private boolean error;

    private Stage stage;

    @FXML
    private Label errorText;
    @FXML
    private TextField fieldUsername;
    @FXML
    private PasswordField fieldPassword;

    private String username, password;

    Stage thisStage;

    public void setStage (Stage stage){
        thisStage = stage;
    }

    public void showStage(){
        thisStage.setTitle("PetCare - Bem-vindo");
        thisStage.show();
    }

    @FXML
    protected void tryLogin(ActionEvent event) throws Exception {
        error = false;

        Admin admin = new Admin("admin", "admin");
        
        username = fieldUsername.getText();
        password = fieldPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            error = true;
            errorText.setText("Os campos não podem estar vazios!");
        } else {
            if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                error = false;
                errorText.setText("Login efetuado com sucesso!");
            } else {
                error = true;
                errorText.setText("Utilizador ou password incorretos!");
            }
        }
        
        if(!error) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = (Parent) fxmlLoader.load(getClass().getResource("register.fxml").openStream());
            thisStage.setScene(new Scene(root));
        }
    }
}