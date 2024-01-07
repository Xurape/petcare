package com.petcare.petcare.Controllers;

import com.petcare.petcare.Auth.Session;
import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private Stage thisStage;

    @FXML
    private TextField _nif, _name, _surname, _email, _address, _phone, _username, _password;

    /**
     *
     * Set the current stage
     *
     * @param stage Current stage
     */
    public void setStage(Stage stage) {
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
        thisStage.setTitle("PetCare - Perfil");
        thisStage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {
        Client client = (Client) Session.getSession().getCurrentUser();

        _nif.setText(client.getnif());
        _name.setText(client.getName());
        _surname.setText(client.getSurname());
        _email.setText(client.getEmail());
        _address.setText(client.getAddress());
        _phone.setText(String.valueOf(client.getPhone()));
    }

    @FXML
    protected void editProfile() {
        Client client = (Client) Session.getSession().getCurrentUser();

        if(Storage.getStorage().userExists(_nif.getText()) && !_nif.getText().equals(client.getnif())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("Já existe um utilizador com esse NIF");
            alert.showAndWait();
            return;
        }

        client.setnif(_nif.getText());
        client.setName(_name.getText());
        client.setSurname(_surname.getText());

        if(!_email.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("O email tem de conter @");
            alert.showAndWait();
            return;
        }

        client.setEmail(_email.getText());
        client.setAddress(_address.getText());

        if(_phone.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("O número de telefone tem de ter 9 dígitos");
            alert.showAndWait();
            return;
        }

        client.setPhone(Integer.parseInt(_phone.getText()));

        if(!_password.getText().isEmpty())
            client.setPassword(_password.getText());

        for (Client c : Storage.getStorage().getClients().values()) {
            if (c.getUsername().equals(_username.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao editar perfil");
                alert.setContentText("Já existe um utilizador com esse username");
                alert.showAndWait();
                return;
            }
        }

        client.setUsername(_username.getText());

        Session.getSession().setCurrentUser(client);

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Profile edited successfully", true, true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Perfil editado com sucesso");
            alert.setContentText("O seu perfil foi editado com sucesso");
            alert.showAndWait();
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
        }
    }

    /**
     *
     * Go to the homepage
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoHomeClient(ActionEvent event) {
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
    }

    /**
     *
     * Go to the homepage
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoInvoices(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/client/invoices.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                InvoicesController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'invoices.fxml' not found.");
        }
    }


    /**
     *
     * Go to the profile page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoProfile(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/client/profile.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                ProfileController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'profile.fxml' not found.");
        }
    }


    /**
     *
     * Logout
     *
     * @param event Event
     * @throws Exception Exception
     *
     */
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
