package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.Employee;
import com.petcare.petcare.Users.ServiceProvider;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.petcare.petcare.Auth.Session;

public class EmployeesController implements Initializable {
    Stage thisStage;

    Employee currentEmployee;

    @FXML
    private ListView<String> employeesList;

    @FXML
    private TextField editNIF, editName, editSurname, editEmail, editAddress, editUsername, editPassword, editProfessionalNumber;

    @FXML
    private TextField createNIF, createName, createSurname, createEmail, createAddress, createUsername, createPassword;

    @FXML
    private ChoiceBox<String> createCompany, createType;

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeesList.setStyle("-fx-control-inner-background: #012B49;");

        if(createCompany != null) {
            ObservableList<String> companies = FXCollections.observableArrayList();
            for (Company company : Storage.getStorage().getCompanies().values()) {
                companies.add(company.getName());
            }
            createCompany.getItems().addAll(companies);
        }

        createType.getItems().addAll("Veterinário", "Tosquiador", "Passeador", "Auxiliar");

        this.getEmployees();
        employeesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem funcionários")) {
                    currentEmployee = Storage.getStorage().getEmployeeByName(newValue.split(" ")[0], newValue.split(" ")[1]);
                    editNIF.setText(currentEmployee.getnif());
                    editName.setText(currentEmployee.getName());
                    editSurname.setText(currentEmployee.getSurname());
                    editEmail.setText(currentEmployee.getEmail());
                    editAddress.setText(currentEmployee.getAddress());
                    editUsername.setText(currentEmployee.getUsername());
                    editPassword.setText(currentEmployee.getPassword());
                    if(currentEmployee.getProfessionalNumber() != null)
                        editProfessionalNumber.setText(currentEmployee.getProfessionalNumber());
                }
            }
        });
    }

    public void getEmployees() {
        boolean isEmpty = false;
        ObservableList<String> employees = FXCollections.observableArrayList();
        for (Company company : Storage.getStorage().getCompanies().values()) {
            if(Session.getSession().getCurrentUser() instanceof Admin) {
                if(company.getEmployees().isEmpty()) {
                    isEmpty = true;
                    employeesList.setStyle("-fx-control-inner-background: #012B49;");
                    employeesList.getItems().clear();
                    employeesList.getItems().addAll("Não existem funcionários");
                } else {
                    for(Employee employee : company.getEmployees()) {
                        employees.add(employee.getName() + " " + employee.getSurname());
                    }
                }
            } else if(Session.getSession().getCurrentUser() instanceof ServiceProvider) {
                if(company.getName().equals(Session.getSession().getCurrentUserAsServiceProvider().getName())) {
                    if(company.getEmployees().isEmpty()) {
                        isEmpty = true;
                        employeesList.setStyle("-fx-control-inner-background: #012B49;");
                        employeesList.getItems().clear();
                        employeesList.getItems().addAll("Não existem funcionários");
                    } else {
                        if (company.getName().equals(Session.getSession().getCurrentUserAsServiceProvider().getCompany().getName()))
                            for (Employee employee : company.getEmployees()) {
                                employees.add(employee.getName() + " " + employee.getSurname());
                            }
                    }
                }
            } else {
                Debug.print("User is not an admin or a service provider", true, true);
            }
        }
        if(!isEmpty) {
            employeesList.getItems().clear();
            employeesList.getItems().addAll(employees);
        }
        employeesList.getSelectionModel().clearSelection();
    }


    public void editEmployee(ActionEvent event) {
        if(currentEmployee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar funcionário");
            alert.setContentText("Selecione um funcionário");
            alert.showAndWait();
            return;
        }

        if(Storage.getStorage().userExists(editNIF.getText()) && !editNIF.getText().equals(currentEmployee.getnif())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar funcionário");
            alert.setContentText("Já existe um utilizador com esse NIF");
            alert.showAndWait();
            return;
        }

        if(editNIF.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar funcionário");
            alert.setContentText("O NIF tem de ter 9 dígitos");
            alert.showAndWait();
            return;
        }

        currentEmployee.setnif(editNIF.getText());
        currentEmployee.setName(editName.getText());
        currentEmployee.setSurname(editSurname.getText());

        if(!editEmail.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar funcionário");
            alert.setContentText("O email tem de conter @");
            alert.showAndWait();
            return;
        }

        currentEmployee.setEmail(editEmail.getText());
        currentEmployee.setAddress(editAddress.getText());
        currentEmployee.setProfessionalNumber(editProfessionalNumber.getText());

        for (Company company : Storage.getStorage().getCompanies().values()) {
            for (Employee employee : company.getEmployees()) {
                if (employee.getUsername().equals(editUsername.getText()) && !employee.getnif().equals(currentEmployee.getnif())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao editar funcionário");
                    alert.setContentText("Já existe um utilizador com esse username");
                    alert.showAndWait();
                    return;
                }
            }
        }

        currentEmployee.setUsername(editUsername.getText());
        currentEmployee.setPassword(editPassword.getText());

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Employee edited successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar funcionário");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getEmployees();
    }

    public void createEmployee(ActionEvent event) {
        Company _company = null;

        if(Session.getSession().getCurrentUser() instanceof Admin) {
            for(Company company : Storage.getStorage().getCompanies().values()) {
                if(company.getName().equals((String) createCompany.getValue())) {
                    _company = company;
                }
            }
        } else {
            _company = Session.getSession().getCurrentUserAsServiceProvider().getCompany();
        }

        if(createNIF.getText().length() != 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar funcionário");
            alert.setContentText("O NIF tem de ter 9 dígitos");
            alert.showAndWait();
            return;
        }

        if(!createEmail.getText().contains("@")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar funcionário");
            alert.setContentText("O email tem de conter @");
            alert.showAndWait();
            return;
        }

        for (Company company : Storage.getStorage().getCompanies().values()) {
            for (Employee employee : company.getEmployees()) {
                if (employee.getUsername().equals(createUsername.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao criar funcionário");
                    alert.setContentText("Já existe um utilizador com esse username");
                    alert.showAndWait();
                    return;
                }
            }
        }

        Employee employee = new Employee(createNIF.getText(), createName.getText(), createSurname.getText(), createEmail.getText(), createAddress.getText(), _company, Employee.convertStringToEmployeeType(createType.getValue()));
        employee.setUsername(createUsername.getText());
        employee.setPassword(createPassword.getText());

        for(Company company : Storage.getStorage().getCompanies().values()) {
            assert _company != null;
            if(company.getName().equals(_company.getName())) {
                if(company.getEmployees().contains(employee)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao criar funcionário");
                    alert.setContentText("O funcionário já existe");
                    alert.showAndWait();
                    return;
                } else {
                    company.addEmployee(employee);

                    try {
                        Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                        Debug.success("Employee created successfully", true, true);
                    } catch(CouldNotSerializeException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Erro ao criar funcionário");
                        alert.setContentText("Não foi possível guardar");
                        alert.showAndWait();
                        return;
                    }

                    this.getEmployees();
                }
            }
        }
    }

    public void removeEmployee(ActionEvent event) {
        if(currentEmployee == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover funcionário");
            alert.setContentText("Selecione um funcionário");
            alert.showAndWait();
            return;
        }
        for(Company company : Storage.getStorage().getCompanies().values()) {
            if(company.getName().equals(currentEmployee.getCompany().getName())) {
                if(company.getEmployees().contains(currentEmployee)) {
                    company.removeEmployee(currentEmployee.getnif());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successo!");
                    alert.setHeaderText("Funcionário removido com sucesso!");
                    alert.setContentText("O funcionário foi removido com sucesso da empresa" + company.getName());
                    alert.showAndWait();

                    try {
                        Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                        Debug.success("Employee removed successfully", true, true);
                        this.getEmployees();
                        currentEmployee = null;
                    } catch(CouldNotSerializeException e) {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("Erro ao remover funcionário");
                        alert.setContentText("Não foi possível guardar");
                        alert.showAndWait();
                        return;
                    }

                    return;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao remover funcionário");
                    alert.setContentText("O funcionário não existe");
                    alert.showAndWait();
                    return;
                }
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
