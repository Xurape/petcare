package com.petcare.petcare.Controllers;

import com.petcare.petcare.Auth.Session;
import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.Employee;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompaniesController implements Initializable {
    Stage thisStage;

    Company currentCompany;

    @FXML
    private ListView<String> companiesList;

    @FXML
    private TextField editNIF, editName, editEmail, editAddress;

    @FXML
    private TextField createNIF, createName, createEmail, createAddress;

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
        thisStage.setTitle("PetCare - Empresas");
        thisStage.show();
    }

    /**
     *
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        companiesList.setStyle("-fx-control-inner-background: #012B49;");

        this.getCompanies();

        companiesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem empresas")) {
                    currentCompany = Storage.getStorage().getCompanyByName(newValue);
                    editNIF.setText(currentCompany.getnif());
                    editName.setText(currentCompany.getName());
                    editEmail.setText(currentCompany.getEmail());
                    editAddress.setText(currentCompany.getAddress());
                }
            }
        });
    }

    public void getCompanies() {
        boolean isEmpty = false;
        ObservableList<String> companies = FXCollections.observableArrayList();
        if(Storage.getStorage().getCompanies().isEmpty()) {
            isEmpty = true;
            companiesList.setStyle("-fx-control-inner-background: #012B49;");
            companiesList.getItems().clear();
            companiesList.getItems().addAll("Não existem empresas");
        } else {
            for (Company company : Storage.getStorage().getCompanies().values()) {
                companies.add(company.getName());
            }
        }
        if(!isEmpty) {
            companiesList.getItems().clear();
            companiesList.getItems().addAll(companies);
        }
        companiesList.getSelectionModel().clearSelection();
    }

    public void editCompany(ActionEvent event) {
        if(Storage.getStorage().getCompanies().containsKey(editNIF.getText()) && !editNIF.getText().equals(currentCompany.getnif())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar empresa");
            alert.setContentText("O NIF já existe");
            alert.showAndWait();
            return;
        }

        if(editNIF.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar empresa");
            alert.setContentText("O NIF tem de ter 9 dígitos");
            alert.showAndWait();
            return;
        }

        if(!editNIF.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar empresa");
            alert.setContentText("O NIF só pode conter números");
            alert.showAndWait();
            return;
        }

        currentCompany.setnif(editNIF.getText());
        currentCompany.setName(editName.getText());

        if(!editEmail.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar empresa");
            alert.setContentText("O email tem de conter @");
            alert.showAndWait();
            return;
        }

        currentCompany.setEmail(editEmail.getText());
        currentCompany.setAddress(editAddress.getText());

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Company edited successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar empresa");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getCompanies();
    }

    public void createCompany(ActionEvent event) {
        if(createNIF.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar empresa");
            alert.setContentText("O NIF tem de ter 9 dígitos");
            alert.showAndWait();
            return;
        }

        if(!createNIF.getText().matches("[0-9]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar empresa");
            alert.setContentText("O NIF só pode conter números");
            alert.showAndWait();
            return;
        }

        if(!createEmail.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar empresa");
            alert.setContentText("O email tem de conter @");
            alert.showAndWait();
            return;
        }

        Company company = new Company(createNIF.getText(), createName.getText(), createEmail.getText(), createAddress.getText());

        for(Company _company : Storage.getStorage().getCompanies().values()) {
            if(company.getName().equals(_company.getName())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao criar empresa");
                alert.setContentText("A empresa já existe");
                alert.showAndWait();
                return;
            } else {
                Storage.getStorage().getCompanies().put(createNIF.getText(), company);

                try {
                    Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                    Debug.success("Company created successfully", true, true);
                } catch(CouldNotSerializeException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao criar empresa");
                    alert.setContentText("Não foi possível guardar");
                    alert.showAndWait();
                    return;
                }

                this.getCompanies();
            }
        }
    }

    public void removeCompany(ActionEvent event) {
        if(currentCompany == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover empresa");
            alert.setContentText("Selecione uma empresa");
            alert.showAndWait();
            return;
        }

        for(Company company : Storage.getStorage().getCompanies().values()) {
            if(company.getName().equals(currentCompany.getName())) {
                Storage.getStorage().getCompanies().remove(currentCompany.getnif());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successo!");
                alert.setHeaderText("Empresa removida com sucesso!");
                alert.setContentText("A empresa foi removida com sucesso");
                alert.showAndWait();

                try {
                    Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                    Debug.success("Company removed successfully", true, true);
                    this.getCompanies();
                    currentCompany = null;
                } catch(CouldNotSerializeException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao remover empresa");
                    alert.setContentText("Não foi possível guardar");
                    alert.showAndWait();
                    return;
                }

                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao remover empresa");
                alert.setContentText("A empresa não existe");
                alert.showAndWait();
                return;
            }
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
            resourceUrl = getClass().getResource("/com/petcare/petcare/admin/employees.fxml");
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
