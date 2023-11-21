package com.petcare.petcare.Controllers;

import com.petcare.petcare.Users.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LauncherController {
    private boolean error;

    @FXML
    private Label errorText;
    @FXML
    private TextField fieldUsername;
    @FXML
    private PasswordField fieldPassword;

    private String username, password;

    @FXML
    protected void tryLogin(ActionEvent event) {
        error = false;

        Admin admin = new Admin("Teste", "Teste");
        
        username = fieldUsername.getText();
        password = fieldPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            error = true;
            errorText.setText("Os campos não podem estar vazios!");
        }

        // make a function to search for a user d a password in the users array
    }
}