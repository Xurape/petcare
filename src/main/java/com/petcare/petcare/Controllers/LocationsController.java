package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.Location;
import com.petcare.petcare.Users.*;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.petcare.petcare.Auth.Session;

public class LocationsController {
    Stage thisStage;

    private Location currentLocation;

    @FXML
    private ListView<String> locationList;

    @FXML
    private TextField editAddress, editCity, editZipcode, editPhone;

    @FXML
    private TextField createAddress, createCity, createZipcode, createPhone;

    @FXML
    private ChoiceBox<String> editService, createService, createCompany, editCompany;

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
        thisStage.setTitle("PetCare - Localidades");
        thisStage.show();
    }

    /**
     *
     * Initializes the controller class.
     *
     */
    public void initialize() {
        if(editService != null) {
            editService.getItems().addAll("Banho", "Tosquia", "Hotel", "Passeio", "Veterinário", "Treino", "Babysitting", "Daycare", "Spa", "Transporte", "Outro");
        }
        if(createService != null) {
            createService.getItems().addAll("Banho", "Tosquia", "Hotel", "Passeio", "Veterinário", "Treino", "Babysitting", "Daycare", "Spa", "Transporte", "Outro");
        }

        if(createCompany != null && editCompany != null) {
            ObservableList<String> companies = FXCollections.observableArrayList();
            for (Company company : Storage.getStorage().getCompanies().values()) {
                companies.add(company.getName());
            }
            createCompany.getItems().addAll(companies);
            editCompany.getItems().addAll(companies);
        }

        if(Session.getSession().isServiceProvider()) {
            if(Session.getSession().getCurrentUserAsServiceProvider().getCompany() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao obter localidades");
                alert.setContentText("Por favor, associa-te a uma empresa no \"Meu Perfil\".");
                alert.showAndWait();
                return;
            }

            this.getLocationList();
        }
    }

    /**
     *
     * Get the locations list
     *
     */
    public void getLocationList() {
        locationList.setStyle("-fx-control-inner-background: #012B49;");

        this.getLocations();

        locationList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem localidades")) {
                    currentLocation = Storage.getStorage().getLocationByAddress(newValue);
                    editAddress.setText(currentLocation.getAddress());
                    editCity.setText(currentLocation.getCity());
                    editZipcode.setText(currentLocation.getZipcode());
                    editPhone.setText(String.valueOf(currentLocation.getPhone()));
                    editService.getSelectionModel().select(currentLocation.getServiceType());
                    if(Session.getSession().isAdmin())
                        editCompany.getSelectionModel().select(currentLocation.getCompany().getName());
                    else if(Session.getSession().isDeskEmployee())
                        editCompany.getSelectionModel().select(currentLocation.getCompany().getName());
                }
            }
        });
    }

    /**
     *
     * Get the locations
     *
     */
    public void getLocations() {
        boolean isEmpty = false;

        ObservableList<String> locations = FXCollections.observableArrayList();
        if(Storage.getStorage().getLocations().isEmpty()) {
            isEmpty = true;
            locationList.setStyle("-fx-control-inner-background: #012B49;");
            locationList.getItems().clear();
            locationList.getItems().addAll("Não existem localidades");
        } else {
            if(Session.getSession().getCurrentUser() instanceof Admin) {
                for(Location location : Storage.getStorage().getLocations()) {
                    locations.add(location.getAddress());
                }
            } else {
                for(Location location : Storage.getStorage().getLocations()) {
                    if(location.getCompany().getName().equals(Session.getSession().getCurrentUserAsServiceProvider().getCompany().getName()))
                        locations.add(location.getAddress());
                }
            }
        }

        if(!isEmpty) {
            locationList.getItems().clear();
            locationList.getItems().addAll(locations);
        }

        locationList.getSelectionModel().clearSelection();
    }

    /**
     *
     * Create a location
     *
     * @param event Event
     *
     */
    @FXML
    protected void createLocation(ActionEvent event) {
        String address = createAddress.getText();
        String city = createCity.getText();
        String zipcode = createZipcode.getText();

        if(Session.getSession().getCurrentUserAsServiceProvider().getCompany() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar localidade");
            alert.setContentText("Por favor, associa-te a uma empresa no \"Meu Perfil\".");
            alert.showAndWait();
            return;
        }

        if(createPhone.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar localidade");
            alert.setContentText("O número de telefone tem de ter 9 dígitos.");
            alert.showAndWait();
            return;
        }

        int phone = Integer.parseInt(createPhone.getText());
        String serviceType = createService.getSelectionModel().getSelectedItem().toString();

        Company company = null;

        if(Session.getSession().isAdmin()) {
            company = Storage.getStorage().getCompanyByName((String) createCompany.getSelectionModel().getSelectedItem());
        } else {
            company = Session.getSession().getCurrentUserAsServiceProvider().getCompany();
        }

        Location location = new Location(address, city, zipcode, phone, serviceType, company);
        if(Session.getSession().getCurrentUser() instanceof Admin) {
            location.setCompany(Storage.getStorage().getCompanyByName((String) createCompany.getSelectionModel().getSelectedItem()));
        } else if(Session.getSession().getCurrentUser() instanceof ServiceProvider) {
            location.setCompany((Session.getSession().getCurrentUserAsServiceProvider().getCompany()));
        }
        Storage.getStorage().getLocations().add(location);

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Location created successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar localidade");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getLocations();
    }

    /**
     *
     * Edit a location
     *
     * @param event Event
     *
     */
    @FXML
    protected void editLocation(ActionEvent event) {
        if(currentLocation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar localidade");
            alert.setContentText("Selecione uma localidade");
            alert.showAndWait();
            return;
        }

        currentLocation.setAddress(editAddress.getText());
        currentLocation.setCity(editCity.getText());
        currentLocation.setZipcode(editZipcode.getText());

        if(editPhone.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar localidade");
            alert.setContentText("O número de telefone tem de ter 9 dígitos.");
            alert.showAndWait();
            return;
        }

        currentLocation.setPhone(Integer.parseInt(editPhone.getText()));
        currentLocation.setServiceType(editService.getSelectionModel().getSelectedItem().toString());
        if(Session.getSession().isAdmin() || Session.getSession().isDeskEmployee())
            currentLocation.setCompany(Storage.getStorage().getCompanyByName((String) editCompany.getSelectionModel().getSelectedItem()));


        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Location edited successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar location");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getLocations();
    }

    /**
     *
     * Remove a location
     *
     * @param event Event
     *
     */
    @FXML
    protected void removeLocation(ActionEvent event) {
        if(currentLocation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover localidade");
            alert.setContentText("Selecione um localidade");
            alert.showAndWait();
            return;
        }
        for(Location location : Storage.getStorage().getLocations()) {
            if(location.equals(currentLocation)) {
                Storage.getStorage().getLocations().remove(location);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successo!");
                alert.setHeaderText("Localidade removida com sucesso!");
                alert.setContentText("A localidade foi removido com sucesso!");
                alert.showAndWait();

                try {
                    Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                    Debug.success("Location removed successfully", true, true);
                    this.getLocations();
                    currentLocation = null;
                } catch(CouldNotSerializeException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao remover localidade");
                    alert.setContentText("Não foi possível guardar");
                    alert.showAndWait();
                    return;
                }
            }
        }
        if(currentLocation != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover localidade");
            alert.setContentText("A localidade não existe");
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
