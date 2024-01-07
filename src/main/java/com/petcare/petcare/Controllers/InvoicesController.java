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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.petcare.petcare.Auth.Session;

public class InvoicesController implements Initializable {
    Stage thisStage;

    @FXML
    private ListView<String> invoicesList;

    @FXML
    private TextField _client, _service, _value, _date, _company, _location, _timestamp, _status;

    private Invoice currentInvoice;

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
        thisStage.setTitle("PetCare - Faturas");
        thisStage.show();
    }

    /**
     *
     * Initializes the controller class.
     *
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        invoicesList.setStyle("-fx-control-inner-background: #012B49;");
        _client.setText(Session.getSession().getCurrentUser().getUsername());

        pay_Method.getItems().addAll("MBWay", "PayPal", "Cartão de Crédito", "Dinheiro");
        pay_Button.setDisable(true);

        this.getInvoices();
    }

    /****
     *
     *
     * CLIENTS
     *
     *
     */
    public void getInvoices() {
        invoicesList.getItems().clear();

        List<Invoice> invoices = Storage.getStorage().getInvoices();
        for(Invoice invoice : invoices) {
            if(invoice.getClient().equals(Session.getSession().getCurrentUser().getUsername())) {
                invoicesList.getItems().add(invoice.getService() + " | " + invoice.getDate() + " | " + invoice.getTimestamp());
            }
        }

        invoicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(newValue != null && !newValue.equals("Não existem faturas")) {
                    for(Invoice invoice : invoices) {
                        if(invoice.getClient().equals(Session.getSession().getCurrentUser().getUsername()) && (invoice.getService() + " | " + invoice.getDate() + " | " + invoice.getTimestamp()).equals(newValue)) {
                            currentInvoice = invoice;
                        }
                    }

                    _client.setText(currentInvoice.getClient());
                    _service.setText(currentInvoice.getService());
                    Double productsPrice = getProductPrice();
                    _value.setText(String.valueOf(Double.parseDouble(currentInvoice.getValue()) + productsPrice));
                    _date.setText(currentInvoice.getDate());
                    _location.setText(currentInvoice.getLocation());
                    _company.setText(currentInvoice.getCompany());
                    _timestamp.setText(currentInvoice.getTimestamp());
                    _status.setText(currentInvoice.getStatusAsString());

                    if(currentInvoice.getStatus() == InvoiceStatus.PENDING) {
                        pay_Pane.setOpacity(1);
                        pay_Value.setText(String.valueOf(Double.parseDouble(currentInvoice.getValue()) + productsPrice));
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
            currentInvoice.setStatus(InvoiceStatus.PAID);
            _status.setText("Pago");

            Double productsPrice = getProductPrice();
            Storage.getStorage().getCompanyByName(currentInvoice.getCompany()).setBalance(Storage.getStorage().getCompanyByName(currentInvoice.getCompany()).getBalance() + Double.parseDouble(currentInvoice.getValue()) + productsPrice - (Double.parseDouble(currentInvoice.getValue()) * 0.07));
            Storage.getStorage().setPetcareBalance(Storage.getStorage().getPetcareBalance() + (Double.parseDouble(currentInvoice.getValue()) * 0.07));

            for(Appointments appointment : Storage.getStorage().getAppointments()) {
                if(appointment.getClient().equals(currentInvoice.getClient()) && appointment.getService().equals(currentInvoice.getService()) && appointment.getTimestamp().equals(currentInvoice.getTimestamp())) {
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
            if(company.getName().equals(currentInvoice.getCompany())) {
                for(Service service : Storage.getStorage().getServices()) {
                    if(service.getCompany().getName().equals(currentInvoice.getCompany())) {
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
