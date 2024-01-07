package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.*;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.ServiceProvider;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
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
    private TextField _client, _service, _value, _date, _dateC, _company, _location, _timestamp, _status;

    @FXML
    private TextField createClient, createValue, companyBalance;

    @FXML
    private ChoiceBox<String> createService, createCompany, createLocation;

    @FXML
    private DatePicker createDate;

    private Appointments currentService;

    @FXML
    private Pane pay_Pane;
    @FXML
    private Button pay_Button;
    @FXML
    private TextField pay_Value;
    @FXML
    private ChoiceBox<String> pay_Method;

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

        if(companyBalance != null && Session.getSession().getCurrentUserAsServiceProvider() != null && Session.getSession().getCurrentUserAsServiceProvider().getCompany() != null) {
            companyBalance.setText(String.valueOf(Session.getSession().getCurrentUserAsServiceProvider().getCompany().getBalance()));
        }

        if(url.equals(getClass().getResource("/com/petcare/petcare/client/homepage.fxml"))) {
            for (Company company : Storage.getStorage().getCompanies().values()) {
                createCompany.getItems().add(company.getName());
            }

            createCompany.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    for (Company company : Storage.getStorage().getCompanies().values()) {
                        createLocation.getItems().clear();
                        createService.getItems().clear();

                        for (Service service : Storage.getStorage().getServices()) {
                            if (service.getCompany().getName().equals(company.getName())) {
                                createService.getItems().add(service.getName());
                            }
                        }
                    }

                    for (Location location : Storage.getStorage().getLocations()) {
                        if (location.getCompany().getName().equals(newValue)) {
                            createLocation.getItems().add(location.getAddress());
                        }
                    }

                    createService.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            createValue.setText("");
                            for (Service service : Storage.getStorage().getServices()) {
                                if (service.getName().equals(newValue) && service.getCompany().getName().equals(createCompany.getValue())) {
                                    createValue.setText(String.valueOf(service.getPrice()));
                                }
                            }
                        }
                    });
                }
            });

            createClient.setText(Session.getSession().getCurrentUser().getUsername());
            _client.setText(Session.getSession().getCurrentUser().getUsername());

            pay_Method.getItems().addAll("MBWay", "PayPal", "Cartão de Crédito", "Dinheiro");
            pay_Button.setDisable(true);

            this.getAppointments();
        } else if(url.equals(getClass().getResource("/com/petcare/petcare/serviceProvider/homepage.fxml"))) {
            this.getServicesSP();
        } else {
            this.getServices();
        }
    }

    public void getServices() {
        servicesList.setStyle("-fx-control-inner-background: #012B49;");

        for(Appointments appointment : Storage.getStorage().getAppointments()) {
            servicesList.getItems().add(appointment.getClient() + " - " + appointment.getService() + " | " + appointment.getDate() + " | " + appointment.getTimestamp());
        }

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem consultas")) {
                    for(Appointments appointment : Storage.getStorage().getAppointments()) {
                        if((appointment.getClient() + " - " + appointment.getService() + " | " + appointment.getDate() + " | " + appointment.getTimestamp()).equals(newValue)) {
                            currentService = appointment;
                        }
                    }
                    _client.setText(currentService.getClient());
                    _service.setText(currentService.getService());
                    Double productsPrice = getProductPrice();
                    _value.setText(String.valueOf(Double.parseDouble(currentService.getValue()) + productsPrice));
                    _date.setText(currentService.getDate());
                }
            }
        });
    }

    public void getServicesSP() {
        servicesList.setStyle("-fx-control-inner-background: #012B49;");

        if(((ServiceProvider) Session.getSession().getCurrentUser()).getCompany() == null) {
            servicesList.getItems().add("Por favor, associa uma empresa primeiro");
            return;
        }

        for(Appointments appointment : Storage.getStorage().getAppointments()) {
            if(appointment.getCompany().equals(((ServiceProvider) Session.getSession().getCurrentUser()).getCompany().getName())) {
                servicesList.getItems().add(appointment.getClient() + " - " + appointment.getService() + " | " + appointment.getDate() + " | " + appointment.getTimestamp());
            }
        }

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem consultas")) {
                    for(Appointments appointment : Storage.getStorage().getAppointments()) {
                        if((appointment.getClient() + " - " + appointment.getService() + " | " + appointment.getDate() + " | " + appointment.getTimestamp()).equals(newValue)) {
                            currentService = appointment;
                        }
                    }
                    _client.setText(currentService.getClient());
                    _service.setText(currentService.getService());
                    Double productsPrice = getProductPrice();
                    _value.setText(String.valueOf(Double.parseDouble(currentService.getValue()) + productsPrice));
                    _date.setText(currentService.getDate());
                }
            }
        });
    }

    public void rejectService(ActionEvent event) {
        if(!Session.getSession().isAdmin() || Session.getSession().isDeskEmployee()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao rejeitar serviço");
            alert.setContentText("Não tem permissões para rejeitar serviços.");
            alert.showAndWait();
            return;
        }

        String client = _client.getText();
        String service = _service.getText();
        String value = _value.getText();
        String date = _date.getText();

        Popup popup = new Popup();
        popup.setX(300);
        popup.setY(200);
        popup.getContent().add(new Label("Razão para rejeitar:"));
        TextField reason = new TextField();
        popup.getContent().add(reason);
        Button confirm = new Button("Confirmar");
        popup.getContent().add(confirm);
        confirm.setOnAction(e -> {
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
                currentService.setReason(reason.getText());
            }
            popup.hide();
        });

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

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Appointment status changed successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao rejeitar marcação");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
        }
    }

    public void acceptService(ActionEvent event) {
        if(!Session.getSession().isAdmin() || Session.getSession().isDeskEmployee()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao aceitar serviço");
            alert.setContentText("Não tem permissões para aceitar serviços.");
            alert.showAndWait();
            return;
        }

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


        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Appointment status changed successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao aceitar marcação");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
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
                servicesList.getItems().add(appointment.getService() + " | " + appointment.getDate() + " | " + appointment.getTimestamp());
            }
        }

        if(servicesList.getItems().isEmpty()) {
            servicesList.getItems().add("Não existem consultas");
        }

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(newValue != null && !newValue.equals("Não existem consultas")) {
                    for(Appointments appointment : appointments) {
                        if(appointment.getClient().equals(Session.getSession().getCurrentUser().getUsername()) && (appointment.getService() + " | " + appointment.getDate() + " | " + appointment.getTimestamp()).equals(newValue)) {
                            currentService = appointment;
                        }
                    }

                    _client.setText(currentService.getClient());
                    _service.setText(currentService.getService());
                    Double productsPrice = getProductPrice();
                    _value.setText(String.valueOf(Double.parseDouble(currentService.getValue()) + productsPrice));
                    _dateC.setText(currentService.getDate());
                    _location.setText(currentService.getLocation());
                    _company.setText(currentService.getCompany());
                    _timestamp.setText(currentService.getTimestamp());
                    _status.setText(currentService.getStatusAsString());

                    if(currentService.getStatus() == AppointmentsStatus.PENDING) {
                        pay_Pane.setOpacity(1);
                        pay_Value.setText(String.valueOf(Double.parseDouble(currentService.getValue()) + productsPrice));
                        pay_Button.setDisable(false);
                    } else {
                        pay_Pane.setOpacity(0.60);
                        pay_Value.setText("--");
                        pay_Button.setDisable(true);
                    }
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

        if(client.isEmpty() || service.isEmpty() || location.isEmpty() || company.isEmpty() || date.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar consulta");
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
        } else if (value.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar consulta");
            alert.setContentText("Essa empresa não faz esse serviço!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Consulta marcada");
            alert.setContentText("A consulta foi marcada com sucesso.");
            alert.showAndWait();

            Invoice invoice = new Invoice(null, client, service, location, company, value, "N/A");
            Appointments appointment = new Appointments(client, service, location, company, date, value);
            appointment.setInvoice(invoice);

            Storage.getStorage().getAppointments().add(appointment);
            Storage.getStorage().getInvoices().add(invoice);
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

    @FXML
    public void pay(ActionEvent event) {
        String value = pay_Value.getText();
        String method = pay_Method.getValue();

        if(value.isEmpty() || method.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao pagar consulta");
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Consulta paga");
            alert.setContentText("A consulta foi paga com sucesso.");
            alert.showAndWait();
            currentService.setStatus(AppointmentsStatus.PAID);
            _status.setText("Pago");

            Double productsPrice = getProductPrice();
            Storage.getStorage().getCompanyByName(currentService.getCompany()).setBalance(Storage.getStorage().getCompanyByName(currentService.getCompany()).getBalance() + Double.parseDouble(currentService.getValue()) + productsPrice - (Double.parseDouble(currentService.getValue()) * 0.07));
            Storage.getStorage().setPetcareBalance(Storage.getStorage().getPetcareBalance() + (Double.parseDouble(currentService.getValue()) * 0.07));

            for(Appointments appointment : Storage.getStorage().getAppointments()) {
                if(appointment.getClient().equals(currentService.getClient()) && appointment.getService().equals(currentService.getService()) && appointment.getDate().equals(currentService.getDate()) && appointment.getTimestamp().equals(currentService.getTimestamp())) {
                    appointment.setStatus(AppointmentsStatus.PAID);
                    appointment.getInvoice().setStatus(InvoiceStatus.PAID);
                }
            }

            pay_Pane.setOpacity(0.60);
            pay_Value.setText("--");
            pay_Button.setDisable(true);

            try {
                Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                Debug.success("Appointment paid successfully", true, true);
            } catch(CouldNotSerializeException e) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erro");
                alert2.setHeaderText("Erro ao pagar consulta");
                alert2.setContentText("Não foi possível guardar");
                alert2.showAndWait();
            }
        }
    }

    private Double getProductPrice() {
        Double productsPrice = 0.0;

        for(Company company : Storage.getStorage().getCompanies().values()) {
            if(company.getName().equals(currentService.getCompany())) {
                for(Service service : Storage.getStorage().getServices()) {
                    if(service.getCompany().getName().equals(currentService.getCompany())) {
                        for(Product product : service.getProducts()) {
                            productsPrice += product.getPrice();
                        }
                    }
                }
            }
        }
        return productsPrice;
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
