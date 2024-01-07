package com.petcare.petcare.Controllers;

import com.petcare.petcare.Auth.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import java.net.URL;

public class LauncherController {
    private Stage stage;

    @FXML
    private Label errorText;
    @FXML
    private TextField fieldUsername;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private Hyperlink goToReg;

    private String username, password;

    Stage thisStage;

    /**
     *
     * Set the current stage
     *
     * @param stage Current stage
     */
    public void setStage (Stage stage){
        thisStage = stage;
    }

    /**
     *
     * Show the current stage
     *
     * @see #thisStage
     *
     */
    public void showStage(){
        thisStage.setTitle("PetCare - Por favor, autentique-se!");
        thisStage.show();
    }

    /**
     *
     * Go to the register page
     *
     * @param event Event
     * @throws Exception Exception
     *
     */
    @FXML
    protected void goToRegister(ActionEvent event) throws Exception {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/register.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                RegisterController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'register.fxml' not found.");
        }
    }

    /**
     *
     * Try to log in the user
     *
     * @param event Event
     * @throws Exception Exception
     *
     */
    @FXML
    protected void tryLogin(ActionEvent event) throws Exception {
        username = fieldUsername.getText();
        password = fieldPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorText.setText("Os campos não podem estar vazios!");
            return;
        }

        if(Session.getSession().getCurrentUser() != null) {
            errorText.setText("O utilizador já está logado!");
            return;
        }

        if(!Session.getSession().login(username, password)) {
            errorText.setText("O utilizador não existe ou a password está errada!");
            return;
        }

        if(Session.getSession().isClient()) {
            URL resourceUrl = getClass().getResource("/com/petcare/petcare/client/homepage.fxml");
            if (resourceUrl != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                    Parent root = fxmlLoader.load();
                    HomepageController controller = fxmlLoader.getController();
                    controller.setStage(thisStage);
                    thisStage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Resource 'homepage.fxml' not found.");
            }
        } else if(Session.getSession().isServiceProvider()) {
            URL resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/homepage.fxml");
            if (resourceUrl != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                    Parent root = fxmlLoader.load();
                    HomepageController controller = fxmlLoader.getController();
                    controller.setStage(thisStage);
                    thisStage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Resource 'homepage.fxml' not found.");
            }
        } else if(Session.getSession().isEmployee()) {
            URL resourceUrl = getClass().getResource("/com/petcare/petcare/employee/homepage.fxml");
            if (resourceUrl != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                    Parent root = fxmlLoader.load();
                    HomepageController controller = fxmlLoader.getController();
                    controller.setStage(thisStage);
                    thisStage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Resource 'homepage.fxml' not found.");
            }
        } else if(Session.getSession().isAdmin()) {
            URL resourceUrl = getClass().getResource("/com/petcare/petcare/admin/homepage.fxml");
            if (resourceUrl != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                    Parent root = fxmlLoader.load();
                    HomepageController controller = fxmlLoader.getController();
                    controller.setStage(thisStage);
                    thisStage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Resource 'homepage.fxml' not found.");
            }
        }
    }
}