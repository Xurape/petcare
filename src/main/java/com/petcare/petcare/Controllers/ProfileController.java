package com.petcare.petcare.Controllers;

import com.petcare.petcare.Auth.Session;
import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.*;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    private Stage thisStage;

    @FXML
    private TextField _nif, _name, _surname, _email, _address, _phone, _username, _password, _number;

    @FXML
    private ChoiceBox<String> _company;

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

    /**
     *
     * Initializes the controller class.
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     *
     */
    public void initialize(URL location, ResourceBundle resources) {
        User client = null;
        client = Session.getSession().getCurrentUser();

        if(location.equals(getClass().getResource("/com/petcare/petcare/admin/profile.fxml"))) {
            _name.setText(((Admin) client).getName());
            _surname.setText(((Admin) client).getSurname());
            _email.setText(((Admin) client).getEmail());
            _phone.setText(String.valueOf(((Admin) client).getPhone()));
            if(_company != null) {
                _company.setValue("PetCare");
                _company.setDisable(true);
            }
        } else if (location.equals(getClass().getResource("/com/petcare/petcare/deskEmployee/profile.fxml"))) {
            _name.setText(((DeskEmployee) client).getName());
            _surname.setText(((DeskEmployee) client).getSurname());
            _email.setText(((DeskEmployee) client).getEmail());
            _phone.setText(String.valueOf(((DeskEmployee) client).getPhone()));
            if(_company != null) {
                _company.setValue("PetCare");
                _company.setDisable(true);
            }
        } else if (location.equals(getClass().getResource("/com/petcare/petcare/serviceProvider/profile.fxml"))) {
            _name.setText(((ServiceProvider) client).getName());
            _surname.setText(((ServiceProvider) client).getSurname());
            _email.setText(((ServiceProvider) client).getEmail());
            _phone.setText(String.valueOf(((ServiceProvider) client).getPhone()));
            if(_company != null) {
                for(Company company : Storage.getStorage().getCompanies().values()) {
                    _company.getItems().add(company.getName());
                }

                if(((ServiceProvider) client).getCompany() != null)
                    _company.setValue(((ServiceProvider) client).getCompany().getName());
            }
        } else {
            _name.setText(((Client) client).getName());
            _surname.setText(((Client) client).getSurname());
            _email.setText(((Client) client).getEmail());
            _phone.setText(String.valueOf(((Client) client).getPhone()));
            if(_company != null)
                _company.setDisable(true);
        }

        _nif.setText(client.getnif());
        _address.setText(client.getAddress());
        _username.setText(client.getUsername());
        _number.setText(client.getCitizenNumber());
    }

    /**
     *
     * Edit profile
     *
     */
    @FXML
    protected void editProfile() {
        User client = null;
        client = Session.getSession().getCurrentUser();

        if(Storage.getStorage().userExists(_nif.getText()) && !_nif.getText().equals(client.getnif())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("Já existe um utilizador com esse NIF");
            alert.showAndWait();
            return;
        }

        client.setnif(_nif.getText());

        if(!_email.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("O email tem de conter @");
            alert.showAndWait();
            return;
        }

        if(_phone.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar perfil");
            alert.setContentText("O número de telefone tem de ter 9 dígitos");
            alert.showAndWait();
            return;
        }

        if(Session.getSession().isAdmin()) {
            ((Admin) client).setName(_name.getText());
            ((Admin) client).setSurname(_surname.getText());
            ((Admin) client).setEmail(_email.getText());
            ((Admin) client).setAddress(_address.getText());
            ((Admin) client).setPhone(Integer.parseInt(_phone.getText()));
            ((Admin) client).setCitizenNumber(_number.getText());
        } else if(Session.getSession().isDeskEmployee()) {
            ((DeskEmployee) client).setName(_name.getText());
            ((DeskEmployee) client).setSurname(_surname.getText());
            ((DeskEmployee) client).setEmail(_email.getText());
            ((DeskEmployee) client).setAddress(_address.getText());
            ((DeskEmployee) client).setPhone(Integer.parseInt(_phone.getText()));
            ((DeskEmployee) client).setCitizenNumber(_number.getText());
        } else if(Session.getSession().isServiceProvider()) {
            ((ServiceProvider) client).setName(_name.getText());
            ((ServiceProvider) client).setSurname(_surname.getText());
            ((ServiceProvider) client).setEmail(_email.getText());
            ((ServiceProvider) client).setAddress(_address.getText());
            ((ServiceProvider) client).setPhone(Integer.parseInt(_phone.getText()));
            ((ServiceProvider) client).setCitizenNumber(_number.getText());
        } else {
            ((Client) client).setName(_name.getText());
            ((Client) client).setSurname(_surname.getText());
            ((Client) client).setEmail(_email.getText());
            ((Client) client).setAddress(_address.getText());
            ((Client) client).setPhone(Integer.parseInt(_phone.getText()));
            ((Client) client).setCitizenNumber(_number.getText());
        }

        if(!_password.getText().isEmpty())
            client.setPassword(_password.getText());

        for (Client c : Storage.getStorage().getClients().values()) {
            if (c.getUsername().equals(_username.getText()) && !c.getnif().equals(client.getnif())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao editar perfil");
                alert.setContentText("Já existe um utilizador com esse username");
                alert.showAndWait();
                return;
            }
        }

        client.setUsername(_username.getText());

        if(Session.getSession().isServiceProvider()) {
            if(_company.getValue() != null) {
                for (Company company : Storage.getStorage().getCompanies().values()) {
                    if (company.getName().equals(_company.getValue())) {
                        ((ServiceProvider) client).setCompany(company);
                        break;
                    }
                }
            }
        }

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

    /**
     *
     * Go to the homepage
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoHome(ActionEvent event) {
        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/homepage.fxml");
        else if(Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/homepage.fxml");
        else if(Session.getSession().isServiceProvider())
            resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/homepage.fxml");
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
     * Go to the services page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoServices(ActionEvent event) {
        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/services.fxml");
        else if(Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/services.fxml");
        else if(Session.getSession().isServiceProvider())
            resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/services.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                ServicesController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'services.fxml' not found.");
        }
    }

    /**
     *
     * Go to the employees page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoEmployees(ActionEvent event) {
        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/employees.fxml");
        else if(Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/employees.fxml");
        else if(Session.getSession().isServiceProvider())
            resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/employees.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                EmployeesController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'employees.fxml' not found.");
        }
    }

    /**
     *
     * Go to the employees page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoDeskEmployees(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/admin/deskemployees.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                DeskEmployeesController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'deskemployees.fxml' not found.");
        }
    }

    /**
     *
     * Go to the locations page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoLocations(ActionEvent event) {
        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/locations.fxml");
        else if(Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/locations.fxml");
        else if(Session.getSession().isServiceProvider())
            resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/locations.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                LocationsController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'locations.fxml' not found.");
        }
    }

    /**
     *
     * Go to the companies page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoCompanies(ActionEvent event) {
        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/companies.fxml");
        else if(Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/companies.fxml");
        if (resourceUrl != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(resourceUrl);
                Parent root = fxmlLoader.load();
                CompaniesController controller = fxmlLoader.getController();
                controller.setStage(thisStage);
                thisStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Resource 'companies.fxml' not found.");
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
        URL resourceUrl = null;
        if(Session.getSession().isAdmin())
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/profile.fxml");
        else if(Session.getSession().isDeskEmployee())
            resourceUrl = getClass().getResource("/com/petcare/petcare/deskEmployee/profile.fxml");
        else if(Session.getSession().isServiceProvider())
            resourceUrl = getClass().getResource("/com/petcare/petcare/serviceProvider/profile.fxml");
        else
            resourceUrl = getClass().getResource("/com/petcare/petcare/client/profile.fxml");
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
}
