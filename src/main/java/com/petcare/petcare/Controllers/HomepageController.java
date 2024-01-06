package com.petcare.petcare.Controllers;

import com.petcare.petcare.Services.Services;
import com.petcare.petcare.Utils.Storage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.petcare.petcare.Auth.Session;

public class HomepageController {
    Stage thisStage;

    @FXML
    private Text welcomeText;

    @FXML
    private TableView<String> servicesList;

    @FXML
    private TextField _client, _service, _serviceType, _value, _date;

    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    // TODO Criar appointments na Storage
    private Appointments currentService;

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
        thisStage.setTitle("PetCare - Bem-vindo");
        thisStage.show();
    }

    /**
     *
     * Initializes the controller class.
     *
     */
    @FXML
    protected void initialize() {
        if(welcomeText != null)
            welcomeText.setText("Bem-vindo, " + Session.getSession().getCurrentUser().getUsername() + "!");

        this.getServices();
    }

    public void getServices() {
        servicesList.setStyle("-fx-control-inner-background: #012B49;");

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem consultas")) {
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage
                    // TODO fazer getRequestedServices() na Storage

                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                    // TODO Criar appointments na Storage
                }
            }
        });
    }

    public void denyService(ActionEvent event) {
        String client = _client.getText();
        String service = _service.getText();
        String serviceType = _serviceType.getText();
        String value = _value.getText();
        String date = _date.getText();
        if (client.isEmpty() || service.isEmpty() || serviceType.isEmpty() || value.isEmpty() || date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao rejeitar serviço");
            alert.setContentText("Por favor escolha um serviço.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Serviço rejeitado");
            alert.setContentText("O serviço foi rejeitado com sucesso.");
            alert.showAndWait();
        }
    }

    public void acceptService(ActionEvent event) {
        String client = _client.getText();
        String service = _service.getText();
        String serviceType = _serviceType.getText();
        String value = _value.getText();
        String date = _date.getText();
        if (client.isEmpty() || service.isEmpty() || serviceType.isEmpty() || value.isEmpty() || date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao aceitar serviço");
            alert.setContentText("Por favor escolha um serviço.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Serviço aceite");
            alert.setContentText("O serviço foi rejeitado com sucesso.");
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

    /**
     *
     * Go to the companies page
     *
     * @param event Event
     *
     */
    @FXML
    protected void gotoCompanies(ActionEvent event) {
        URL resourceUrl = getClass().getResource("/com/petcare/petcare/admin/companies.fxml");
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
}
