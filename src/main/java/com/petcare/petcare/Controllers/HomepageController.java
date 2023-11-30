package com.petcare.petcare.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import java.net.URL;

import com.petcare.petcare.Auth.Session;

public class HomepageController {
    Stage thisStage;

    @FXML
    private Text welcomeText;
    
    public void setStage(Stage stage) {
        thisStage = stage;
    }

    public void showStage(){
        thisStage.setTitle("PetCare - Bem-vindo");
        thisStage.show();
    }

    @FXML
    protected void initialize() {
        welcomeText.setText("Bem-vindo, " + Session.getSession().getCurrentUser().getUsername() + "!");
    }

    @FXML
    protected void logout(ActionEvent event) throws Exception {
        Session.getSession().logout();
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
}
