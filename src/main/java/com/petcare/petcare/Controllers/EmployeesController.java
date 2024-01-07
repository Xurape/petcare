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
    private TextField editNIF, editName, editSurname, editEmail, editAddress, editUsername, editPassword;

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
        currentEmployee.setnif(editNIF.getText());
        currentEmployee.setName(editName.getText());
        currentEmployee.setSurname(editSurname.getText());
        currentEmployee.setEmail(editEmail.getText());
        currentEmployee.setAddress(editAddress.getText());
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
