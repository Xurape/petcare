package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.Location;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.DeskEmployee;
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
    private ChoiceBox editService, createService;

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
        this.getLocationList();
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
                if(newValue != null) {
                    currentLocation = Storage.getStorage().getLocationByAddress(newValue);
                    editAddress.setText(currentLocation.getAddress());
                    editCity.setText(currentLocation.getCity());
                    editZipcode.setText(currentLocation.getZipcode());
                    editPhone.setText(String.valueOf(currentLocation.getPhone()));
                    editService.getSelectionModel().select(currentLocation.getServiceType());
                }
            }
        });
    }

    public void getLocations() {
        boolean isEmpty = false;

        ObservableList<String> locations = FXCollections.observableArrayList();
        if(Storage.getStorage().getLocations().isEmpty()) {
            isEmpty = true;
            locationList.setStyle("-fx-control-inner-background: #012B49;");
            locationList.getItems().clear();
            locationList.getItems().addAll("Não existem localidades");
        } else {
            for(Location location : Storage.getStorage().getLocations()) {
                locations.add(location.getAddress());
            }
        }

        if(!isEmpty) {
            locationList.getItems().clear();
            locationList.getItems().addAll(locations);
        }

        locationList.getSelectionModel().clearSelection();
    }

    @FXML
    protected void createLocation(ActionEvent event) {
        String address = createAddress.getText();
        String city = createCity.getText();
        String zipcode = createZipcode.getText();
        int phone = Integer.parseInt(createPhone.getText());
        String serviceType = createService.getSelectionModel().getSelectedItem().toString();

        Location location = new Location(address, city, zipcode, phone, serviceType);
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

    @FXML
    protected void editLocation(ActionEvent event) {
        currentLocation.setAddress(editAddress.getText());
        currentLocation.setCity(editCity.getText());
        currentLocation.setZipcode(editZipcode.getText());
        currentLocation.setPhone(Integer.parseInt(editPhone.getText()));
        currentLocation.setServiceType(editService.getSelectionModel().getSelectedItem().toString());

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

    @FXML
    protected void removeLocation(ActionEvent event) {
        if(currentLocation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover funcionário");
            alert.setContentText("Selecione um funcionário");
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
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao remover localidade");
                alert.setContentText("O funcionário não existe");
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

    /**
     *
     * Go to the services page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoServices(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/admin/services.fxml");
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
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/admin/employees.fxml");
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
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/admin/locations.fxml");
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
}
