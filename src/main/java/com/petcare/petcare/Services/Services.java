package com.petcare.petcare.Services;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Services {
    private SimpleStringProperty client;
    private SimpleStringProperty service;
    private SimpleStringProperty serviceType;
    private SimpleStringProperty date;
    private SimpleDoubleProperty value;

    public Services(String client, String service, String serviceType, String date, Double value) {
        this.client = new SimpleStringProperty(client);
        this.service = new SimpleStringProperty(service);
        this.serviceType = new SimpleStringProperty(serviceType);
        this.date = new SimpleStringProperty(date);
        this.value = new SimpleDoubleProperty(value);
    }

    public String getClient() {
        return client.get();
    }

    public SimpleStringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client = new SimpleStringProperty(client);
    }

    public String getService() {
        return service.get();
    }

    public SimpleStringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service = new SimpleStringProperty(service);
    }

    public String getServiceType() {
        return serviceType.get();
    }

    public SimpleStringProperty serviceTypeProperty() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = new SimpleStringProperty(serviceType);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value = new SimpleDoubleProperty(value);
    }
}
