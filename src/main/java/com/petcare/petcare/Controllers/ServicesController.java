package com.petcare.petcare.Controllers;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.Service;
import com.petcare.petcare.Services.ServiceType;
import com.petcare.petcare.Services.Services;
import com.petcare.petcare.Services.ServicesList;
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

public class ServicesController {
    Stage thisStage;

    @FXML
    private TableView<Services> servicesTable;
    @FXML
    private TableColumn<Services, String> columnCliente;
    @FXML
    private TableColumn<Services, String> columnServico;
    @FXML
    private TableColumn<Services, String> columnTipoServico;
    @FXML
    private TableColumn<Services, String> columnData;
    @FXML
    private TableColumn<Services, Integer> columnValor;

    @FXML
    private TableView<ServicesList> serviceListTable;
    @FXML
    private TableColumn<ServicesList, String> _columnTipoServico;
    @FXML
    private TableColumn<ServicesList, String> _columnServico;
    @FXML
    private TableColumn<ServicesList, Integer> _columnValor;


    @FXML
    private Pane createServicePane;
    @FXML
    private TextField _nomeServico;
    @FXML
    private TextField _descricaoServico;
    @FXML
    private TextField _valorServico;
    @FXML
    private ChoiceBox<String> _tipoServico;

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
        _columnTipoServico.setCellValueFactory(new PropertyValueFactory<>("ServiceType"));
        _columnServico.setCellValueFactory(new PropertyValueFactory<>("Service"));
        _columnValor.setCellValueFactory(new PropertyValueFactory<>("Value"));

        this.getServicesList();

        if(_tipoServico != null) {
            _tipoServico.getItems().addAll("Banho", "Tosquia", "Hotel", "Passeio", "Veterinário", "Treino", "Babysitting", "Daycare", "Spa", "Transporte", "Outro");
        }
    }

    /**
     *
     * Get the requested services list
     *
     */
    public void getServices() {
        // TODO all
        // TODO Apenas fazer quando a parte do cliente estiver terminada
    }

    /**
     *
     * Get the services list
     *
     */
    public void getServicesList() {
        ObservableList<ServicesList> servicesData = FXCollections.observableArrayList();
        for(Service service : Storage.getStorage().getServices()) {
            servicesData.add(new ServicesList(service.getType().toString(), service.getName(), service.getPrice()));
        }
        serviceListTable.setItems(servicesData);
    }

    /**
     *
     * Initializes the controller class.
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
            System.err.println("Resource 'employees.fxml' not found.");
        }
    }

    @FXML
    protected void createServiceToggle(ActionEvent event) {
        if(createServicePane.getOpacity() == 0) {
            createServicePane.setOpacity(1);
        } else {
            createServicePane.setOpacity(0);
        }
    }

    @FXML
    protected void editService(ActionEvent event) {

    }

    @FXML
    protected void createService(ActionEvent event) {
        String name = _nomeServico.getText();
        String description = _descricaoServico.getText();
        String price = _valorServico.getText();
        double _price = Double.parseDouble(price);
        String type = (String) _tipoServico.getValue();

        if(name.isEmpty() || description.isEmpty() || price.isEmpty() || type == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao criar serviço");
            alert.setContentText("Por favor preencha todos os campos.");
            alert.showAndWait();
        } else {
            ServiceType _type;
            switch(type) {
                case "Banho":
                    _type = ServiceType.BATHING;
                    break;
                case "Tosquia":
                    _type = ServiceType.GROOMING;
                    break;
                case "Hotel":
                    _type = ServiceType.HOTEL;
                    break;
                case "Passeio":
                    _type = ServiceType.WALKING;
                    break;
                case "Veterinário":
                    _type = ServiceType.VETERINARY;
                    break;
                case "Treino":
                    _type = ServiceType.TRAINING;
                    break;
                case "Babysitting":
                    _type = ServiceType.SITTING;
                    break;
                case "Daycare":
                    _type = ServiceType.DAYCARE;
                    break;
                case "Spa":
                    _type = ServiceType.SPA;
                    break;
                case "Transporte":
                    _type = ServiceType.TRANSPORT;
                    break;
                default:
                    _type = ServiceType.OTHER;
                    break;
            }
            Service service = new Service(name, description, _type, _price);
            Storage.getStorage().getServices().add(service);
            try {
                Debug.warning("Serializing data into storage...", true, true);
                Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            } catch (CouldNotSerializeException e) {
                Debug.print("Could not serialize storage! " + e.getMessage(), true, true);
                return;
            } finally {
                Debug.success("Data serialized successfully!", true, true);
            }
            this.getServicesList();
            createServiceToggle(event);
        }
    }
}
