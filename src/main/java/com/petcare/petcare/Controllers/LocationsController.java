package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.*;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.petcare.petcare.Auth.Session;

public class LocationsController {
    Stage thisStage;

    @FXML
    protected Pane createLocationPane;

    @FXML
    protected Pane editLocationPane;

    @FXML
    protected TableView<LocationModel> locationsTable;

    @FXML
    protected TableColumn<LocationModel, String> columnCidade;
    @FXML
    protected TableColumn<LocationModel, String> columnMorada;
    @FXML
    protected TableColumn<LocationModel, Integer> columnTelemovel;
    @FXML
    protected TableColumn<LocationModel, String> columnTipoServico;

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
        thisStage.setTitle("PetCare - Funcionários");
        thisStage.show();
    }

    /**
     *
     * Initializes the controller class.
     *
     */
    public void initialize() {
        columnCidade.setCellValueFactory(new PropertyValueFactory<>("City"));
        columnMorada.setCellValueFactory(new PropertyValueFactory<>("Address"));
        columnTelemovel.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        columnTipoServico.setCellValueFactory(new PropertyValueFactory<>("ServiceType"));

        this.getLocationList();
    }

    /**
     *
     * Get the locations list
     *
     */
    public void getLocationList() {
        for(Location location : Storage.getStorage().getLocations()) {
            Debug.print(location.getCity() + " " + location.getAddress() + " " + location.getPhone() + " " + location.getServiceType(), false, false);
        }

        ObservableList<LocationModel> locationsData = FXCollections.observableArrayList();
        for(Location location : Storage.getStorage().getLocations()) {
            locationsData.add(new LocationModel(location.getAddress(), location.getCity(), location.getPhone(), ""));
        }

        locationsTable.setItems(locationsData);
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

    /**
     *
     * Go to the services page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoServices(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/services.fxml");
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
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/employees.fxml");
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
     * Go to the locations page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoLocations(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/locations.fxml");
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

    @FXML
    protected void createLocation(ActionEvent event) {

    }

    @FXML
    protected void editLocation(ActionEvent event) {

    }

    @FXML
    protected void createLocationToggle(ActionEvent event) {
        if(createLocationPane.getOpacity() == 0) {
            createLocationPane.setOpacity(1);
        } else {
            createLocationPane.setOpacity(0);
        }
    }
    @FXML
    protected void editLocationToggle(ActionEvent event) {
        if(editLocationPane.getOpacity() == 0) {
            editLocationPane.setOpacity(1);
        } else {
            editLocationPane.setOpacity(0);
        }
    }
}
