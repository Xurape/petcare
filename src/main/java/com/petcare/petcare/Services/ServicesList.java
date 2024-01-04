package com.petcare.petcare.Services;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ServicesList {
    private SimpleStringProperty serviceType;
    private SimpleStringProperty service;
    private SimpleDoubleProperty value;

    public ServicesList(String serviceType, String service, Double value) {
        this.serviceType = new SimpleStringProperty(serviceType);
        this.service = new SimpleStringProperty(service);
        this.value = new SimpleDoubleProperty(value);
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

    public String getService() {
        return service.get();
    }

    public SimpleStringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service = new SimpleStringProperty(service);
    }

    public double getValue() {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value = new SimpleDoubleProperty(value);
    }
}
