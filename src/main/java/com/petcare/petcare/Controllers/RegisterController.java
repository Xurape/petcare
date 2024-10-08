package com.petcare.petcare.Controllers;

import com.petcare.petcare.Auth.Session;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.ServiceProvider;
import com.petcare.petcare.Users.User;
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
    @FXML
    private TextField fieldPhone;
    @FXML
    private TextField fieldEmail;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldSurname;
    @FXML
    private TextField fieldNumber;
    @FXML
    private Label errorText;

    /**
     *
     * Initializes the controller class.
     *
     */
    public void initialize() {
        if (tipoConta != null) {
            tipoConta.getItems().addAll("Prestador de serviço", "Cliente");
        }
    }

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
        thisStage.setTitle("PetCare - Registo");
        thisStage.show();
    }

    /**
     *
     * Go back to the login page
     *
     * @param event Event
     * @throws Exception Exception
     *
     */
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

    /**
     *
     * Register the user
     *
     * @param event Event
     * @throws Exception Exception
     *
     */
    @FXML
    protected void registerUser(ActionEvent event) throws Exception {
        String error = null;

        String username = fieldUsername.getText();
        String password = fieldPassword.getText();
        String email = fieldEmail.getText();
        String passwordAgain = fieldPasswordAgain.getText();
        String nif = fieldNIF.getText();
        String address = fieldAddress.getText();
        String tipo = (String) tipoConta.getValue();
        String phone = fieldPhone.getText();
        String surname = fieldSurname.getText();
        String name = fieldName.getText();
        String citizenNumber = fieldNumber.getText();

        if(!password.equals(passwordAgain))
            error = "Password não coincide";

        if(username.isEmpty() || password.isEmpty() || passwordAgain.isEmpty() || nif.isEmpty() || address.isEmpty() || phone.isEmpty() || tipo == null || name.isEmpty() || surname.isEmpty() || citizenNumber.isEmpty())
            error = "Por favor preencha todos os campos.";

        if(Storage.getStorage().userExists(nif))
            error = "O utilizador já existe!";

        if(nif.length() != 9)
            error = "O NIF tem de ter 9 dígitos.";

        if(!nif.matches("[0-9]+"))
            error = "O NIF só pode conter números.";

        if(!phone.matches("[0-9]+"))
            error = "O número de telefone só pode conter números.";

        if(phone.length() != 9)
            error = "O número de telefone tem de ter 9 dígitos.";

        if(!email.contains("@"))
            error = "O email tem de conter @";

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
            case "Prestador de serviço":
                _user = new ServiceProvider(username, password);
                _user.setnif(nif);
                ((ServiceProvider) _user).setEmail(email);
                _user.setAddress(address);
                _user.setOnline(true);
                ((ServiceProvider) _user).setName(name);
                ((ServiceProvider) _user).setSurname(surname);
                ((ServiceProvider) _user).setCitizenNumber(citizenNumber);
                ((ServiceProvider) _user).setPhone(Integer.parseInt(phone));
                Storage.getStorage().getServiceProviders().put(nif, (ServiceProvider) _user);
                if(!Session.getSession().register(_user))
                    error = "Erro ao registar utilizador como prestador de serviço.";
                break;
                
            case "Cliente":
                _user = new Client(username, password);
                _user.setnif(nif);
                ((Client) _user).setEmail(email);
                ((Client) _user).setPhone(Integer.parseInt(phone));
                ((Client) _user).setName(name);
                ((Client) _user).setSurname(surname);
                _user.setCitizenNumber(citizenNumber);
                _user.setAddress(address);
                _user.setOnline(true);
                Storage.getStorage().getClients().put(nif, (Client) _user);
                if(!Session.getSession().register(_user))
                    error = "Erro ao registar utilizador como cliente.";
                break;
        }

        if(error != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro no registo");
            alert.setContentText(error);
            alert.showAndWait();
            _user = null;
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Registo efetuado com sucesso");
        alert.setContentText("A sua conta foi criada com sucesso.");
        alert.showAndWait();

        Session.getSession().setCurrentUser(_user);

        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/homepage.fxml");
        else if(Session.getSession().isServiceProvider())
            resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/homepage.fxml");
        else if (Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/homepage.fxml");
        else
            resourceUrl = getClass().getResource("/com/petcare/petcare/client/homepage.fxml");

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