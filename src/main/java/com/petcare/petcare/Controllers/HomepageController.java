package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.*;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.petcare.petcare.Auth.Session;

public class HomepageController implements Initializable {
    Stage thisStage;

    @FXML
    private Text welcomeText;

    @FXML
    private ListView<String> servicesList;

    @FXML
    private TextField _client, _service, _value;

    @FXML
    private TextField createClient, createValue;

    @FXML
    private ChoiceBox<String> createService, createCompany, createLocation;

    @FXML
    private DatePicker createDate;

    @FXML
    private Text _date;

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
    public void initialize(URL url, ResourceBundle rb) {
        if(welcomeText != null)
            welcomeText.setText("Bem-vindo, " + Session.getSession().getCurrentUser().getUsername() + "!");

        if(url.equals(getClass().getResource("/com/petcare/petcare/client/homepage.fxml"))) {
            for(Company company : Storage.getStorage().getCompanies().values()) {
                createCompany.getItems().add(company.getName());
            }

            createCompany.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    createLocation.getItems().clear();
                    createService.getItems().clear();

                    for(Company company : Storage.getStorage().getCompanies().values()) {
                        for(Service service : Storage.getStorage().getServices()) {
                            if (service.getCompany().getName().equals(company.getName())) {
                                createService.getItems().add(service.getName());
                            }
                        }
                    }

                    for(Location location : Storage.getStorage().getLocations()) {
                        if(location.getCompany().getName().equals(newValue)) {
                            createLocation.getItems().add(location.getAddress());
                        }
                    }

                    createService.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                            createValue.setText("");
                            for(Service service : Storage.getStorage().getServices()) {
                                if(service.getName().equals(newValue) && service.getCompany().getName().equals(createCompany.getValue())) {
                                    createValue.setText(String.valueOf(service.getPrice()));
                                }
                            }
                        }
                    });
                }
            });

            createClient.setText(Session.getSession().getCurrentUser().getUsername());
            _client.setText(Session.getSession().getCurrentUser().getUsername());

            servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                    if(newValue != null && !newValue.equals("Não existem consultas")) {
                        currentService = Storage.getStorage().getAppointments().get(servicesList.getSelectionModel().getSelectedIndex());
                        _client.setText(currentService.getClient());
                        _service.setText(currentService.getService());
                        _value.setText(currentService.getValue());
                        _date.setText(currentService.getDate());
                    }
                }
            });

            this.getAppointments();
        } else {
            this.getServices();
        }

    }

    public void getServices() {
        servicesList.setStyle("-fx-control-inner-background: #012B49;");

        for(Appointments appointment : Storage.getStorage().getAppointments()) {
            servicesList.getItems().add(appointment.getClient() + " - " + appointment.getService() + " | " + appointment.getDate());
        }

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem consultas")) {
                    currentService = Storage.getStorage().getAppointments().get(servicesList.getSelectionModel().getSelectedIndex());
                    _client.setText(currentService.getClient());
                    _service.setText(currentService.getService());
                    _value.setText(currentService.getValue());
                    _date.setText(currentService.getDate());
                }
            }
        });
    }

    public void rejectService(ActionEvent event) {
        String client = _client.getText();
        String service = _service.getText();
        String value = _value.getText();
        String date = _date.getText();
        if (client.isEmpty() || service.isEmpty() || value.isEmpty() || date.isEmpty()) {
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
            currentService.setStatus(AppointmentsStatus.REJECTED);
        }
    }

    public void acceptService(ActionEvent event) {
        String client = _client.getText();
        String service = _service.getText();
        String value = _value.getText();
        String date = _date.getText();
        if (client.isEmpty() || service.isEmpty() || value.isEmpty() || date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao aceitar serviço");
            alert.setContentText("Por favor escolha um serviço.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Serviço aceite");
            alert.setContentText("O serviço foi aceite com sucesso.");
            alert.showAndWait();
            currentService.setStatus(AppointmentsStatus.ACCEPTED);
        }
    }

    /****
     *
     *
     * CLIENTS
     *
     *
     */
    public void getAppointments() {
        servicesList.setStyle("-fx-control-inner-background: #012B49;");

        servicesList.getItems().clear();

        List<Appointments> appointments = Storage.getStorage().getAppointments();
        for(Appointments appointment : appointments) {
            if(appointment.getClient().equals(Session.getSession().getCurrentUser().getUsername())) {
                servicesList.getItems().add(appointment.getService() + " | " + appointment.getDate());
            }
        }

        if(servicesList.getItems().isEmpty()) {
            servicesList.getItems().add("Não existem consultas");
        }

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(newValue != null && !newValue.equals("Não existem consultas")) {
                    currentService = Storage.getStorage().getAppointments().get(servicesList.getSelectionModel().getSelectedIndex());
                    _client.setText(currentService.getClient());
                    _service.setText(currentService.getService());
                    _value.setText(currentService.getValue());
                    _date.setText(currentService.getDate());
                }
            }
        });
    }

    @FXML
    public void createAppointment(ActionEvent event) {
        String client = createClient.getText();
        String service = (String) createService.getValue();
        String location = (String) createLocation.getValue();
        String company = (String) createCompany.getValue();
        String date = createDate.getValue().toString();
        String value = createValue.getText();

        if(client.isEmpty() || service.isEmpty() || location.isEmpty() || company.isEmpty() || date.isEmpty() || value.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar consulta");
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Consulta marcada");
            alert.setContentText("A consulta foi marcada com sucesso.");
            alert.showAndWait();
            Appointments appointment = new Appointments(client, service, location, company, date, value);
            Storage.getStorage().getAppointments().add(appointment);
            this.getAppointments();
        }

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Appointment created successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar consulta");
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
