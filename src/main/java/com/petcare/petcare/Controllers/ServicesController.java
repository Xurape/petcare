package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.Location;
import com.petcare.petcare.Services.Product;
import com.petcare.petcare.Services.Service;
import com.petcare.petcare.Users.Company;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import com.petcare.petcare.Auth.Session;

public class ServicesController {
    Stage thisStage;

    @FXML
    private ListView<String> servicesList, productList;
    @FXML
    private TextField createName, createDescription, createPrice, editName, editDescription, editPrice, productName, productPrice;
    @FXML
    private ChoiceBox<String> createType, editType, createCompany, editCompany;

    private Service currentService;

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
        thisStage.setTitle("PetCare - Serviços");
        thisStage.show();
    }

    /**
     *
     * Initializes the controller class.
     *
     */
    public void initialize() {
        this.getServicesList();

        if(createType != null) {
            createType.getItems().addAll("Banho", "Tosquia", "Hotel", "Passeio", "Veterinário", "Treino", "Babysitting", "Daycare", "Spa", "Transporte", "Outro");
        }

        if(editType != null) {
            editType.getItems().addAll("Banho", "Tosquia", "Hotel", "Passeio", "Veterinário", "Treino", "Babysitting", "Daycare", "Spa", "Transporte", "Outro");
        }

        servicesList.setStyle("-fx-control-inner-background: #012B49;");
        productList.setStyle("-fx-control-inner-background: #012B49;");

        if(Session.getSession().isAdmin()) {
            if(createCompany != null) {
                createCompany.setStyle("-fx-control-inner-background: #012B49;");

                for(Company company : Storage.getStorage().getCompanies().values()) {
                    createCompany.getItems().add(company.getName());
                }

                editCompany.getItems().addAll(createCompany.getItems());
            }
        }

        servicesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null && !newValue.equals("Não existem serviços")) {
                    for(Service service : Storage.getStorage().getServices()) {
                        if(service.getName().equals(newValue)) {
                            currentService = service;
                            break;
                        }
                    }
                    editName.setText(currentService.getName());
                    editDescription.setText(currentService.getDescription());
                    editType.getSelectionModel().select(Service.getTypeString(currentService.getType()));
                    editPrice.setText(String.valueOf(currentService.getPrice()));
                    editCompany.getSelectionModel().select(currentService.getCompany().getName());
                    editCompany.setDisable(true);
                    for(Product product : currentService.getProducts()) {
                        productList.getItems().add(product.getName());
                    }
                }
            }
        });

        productList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String old, String newValue) {
                if(newValue != null) {
                    for(Product product : currentService.getProducts()) {
                        if(product.getName().equals(newValue)) {
                            productName.setText(product.getName());
                            productPrice.setText(String.valueOf(product.getPrice()));
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     *
     * Get the services list
     *
     */
    public void getServicesList() {
        boolean isEmpty = false;

        ObservableList<String> services = FXCollections.observableArrayList();
        if(Storage.getStorage().getServices().isEmpty()) {
            isEmpty = true;
            servicesList.setStyle("-fx-control-inner-background: #012B49;");
            servicesList.getItems().clear();
            servicesList.getItems().addAll("Não existem serviços");
        } else {
            for(Service service : Storage.getStorage().getServices()) {
                services.add(service.getName());
            }
        }

        if(!isEmpty) {
            servicesList.getItems().clear();
            servicesList.getItems().addAll(services);
        }

        servicesList.getSelectionModel().clearSelection();
    }

    @FXML
    protected void createService(ActionEvent event) {
        String name = createName.getText();
        String description = createDescription.getText();
        String type = (String) createType.getSelectionModel().getSelectedItem();
        double price = Double.parseDouble(createPrice.getText());

        Company company = null;

        if(Session.getSession().isAdmin()) {
            company = Storage.getStorage().getCompanyByName(createCompany.getSelectionModel().getSelectedItem());
        } else {
            company = Session.getSession().getCurrentUserAsServiceProvider().getCompany();
        }

        Service service = new Service(name, description, Service.getTypeFromString(type), price, company);
        Storage.getStorage().getServices().add(service);

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Service created successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar serviço");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getServicesList();
    }

    @FXML
    protected void editService(ActionEvent event) {
        if(currentService == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar serviço");
            alert.setContentText("Selecione um serviço");
            alert.showAndWait();
            return;
        }

        String name = editName.getText();
        String description = editDescription.getText();
        String type = (String) editType.getSelectionModel().getSelectedItem();
        double price = Double.parseDouble(editPrice.getText());

        currentService.setName(name);
        currentService.setDescription(description);
        currentService.setType(Service.getTypeFromString(type));
        currentService.setPrice(price);

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Service edited successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao editar serviço");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getServicesList();
    }

    @FXML
    protected void removeService(ActionEvent event) {
        if(currentService == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover serviço");
            alert.setContentText("Selecione um serviço");
            alert.showAndWait();
            return;
        }
        for(Service service : Storage.getStorage().getServices()) {
            if(service.equals(currentService)) {
                Storage.getStorage().getServices().remove(service);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successo!");
                alert.setHeaderText("Serviço removido com sucesso!");
                alert.setContentText("O serviço foi removido com sucesso!");
                alert.showAndWait();

                try {
                    Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                    Debug.success("Service removed successfully", true, true);
                    this.getServicesList();
                    currentService = null;
                } catch(CouldNotSerializeException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao remover serviço");
                    alert.setContentText("Não foi possível guardar");
                    alert.showAndWait();
                    return;
                }
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao remover serviço");
                alert.setContentText("O serviço não existe");
                alert.showAndWait();
                return;
            }
        }
    }

    @FXML
    public void product(ActionEvent event) {
        if(currentService == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar produto");
            alert.setContentText("Selecione um serviço");
            alert.showAndWait();
            return;
        }

        String name = productName.getText();
        double price = Double.parseDouble(productPrice.getText());

        Product product = null;

        for(Product p : currentService.getProducts()) {
            if(p.getName().equals(name)) {
                product = p;
            }
        }

        if(product != null) {
            product.setPrice(price);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Produto editado com sucesso");
            alert.setContentText("O produto foi editado com sucesso");
            alert.showAndWait();
            return;
        }

        product = new Product(name, price);
        currentService.getProducts().add(product);

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("Product added successfully", true, true);
        } catch(CouldNotSerializeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar produto");
            alert.setContentText("Não foi possível guardar");
            alert.showAndWait();
            return;
        }

        this.getServicesList();
    }

    @FXML
    public void removeProduct(ActionEvent event) {
        if(currentService == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao remover produto");
            alert.setContentText("Selecione um serviço");
            alert.showAndWait();
            return;
        }

        String name = productName.getText();

        for(Product product : currentService.getProducts()) {
            if(product.getName().equals(name)) {
                currentService.getProducts().remove(product);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successo!");
                alert.setHeaderText("Produto removido com sucesso!");
                alert.setContentText("O produto foi removido com sucesso!");
                alert.showAndWait();

                try {
                    Storage.getStorage().serialize("./src/main/resources/data/storage.db");
                    Debug.success("Product removed successfully", true, true);
                    this.getServicesList();
                    currentService = null;
                } catch(CouldNotSerializeException e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Erro ao remover produto");
                    alert.setContentText("Não foi possível guardar");
                    alert.showAndWait();
                    return;
                }
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao remover produto");
                alert.setContentText("O produto não existe");
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
