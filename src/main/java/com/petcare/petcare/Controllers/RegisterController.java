package com.petcare.petcare.Controllers;

import com.petcare.petcare.Auth.Session;
import com.petcare.petcare.Auth.Users;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.User;
import com.petcare.petcare.Users.UserType;
import com.petcare.petcare.Utils.Storage;

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
    @FXML
    private TextField fieldUsername;
    @FXML
    private PasswordField fieldPassword;
    @FXML
    private PasswordField fieldPasswordAgain;
    @FXML
    private TextField fieldNIF;
    @FXML
    private TextField fieldAddress;

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
        String error = null;

        String username = fieldUsername.getText();
        String password = fieldPassword.getText();
        String passwordAgain = fieldPasswordAgain.getText();
        String nif = fieldNIF.getText();
        String address = fieldAddress.getText();
        String tipo = (String) tipoConta.getValue();

        if(!password.equals(passwordAgain))
            error = "Password não coincide";

        if(username.equals("") || password.equals("") || passwordAgain.equals("") || nif.equals("") || address.equals(""))
            error = "Por favor preencha todos os campos.";

        if(Storage.getStorage().userExists(nif))
            error = "O utilizador já existe!";

        if(error != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro no registo");
            alert.setContentText(error);
            alert.showAndWait();
            return;
        }

        User _user = null;

        switch(tipo) {
            case "Prestador de Serviços":
                _user = new Company(username, password);
                _user.setNIF(nif);
                _user.setAddress(address);
                _user.setOnline(true);
                
                Storage.getStorage().getCompanies().put(nif, (Company) _user);
                break;
                
            case "Cliente":
                _user = new Client(username, password);
                _user.setNIF(nif);
                _user.setAddress(address);
                _user.setOnline(true);
                Storage.getStorage().getClients().put(nif, (Client) _user);
                break;
        }

        Session.getSession().setCurrentUser(_user);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Registo efetuado com sucesso");
        alert.setContentText("A sua conta foi criada com sucesso.");
        alert.showAndWait();
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/homepage.fxml");
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