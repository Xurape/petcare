package com.petcare.petcare.Services;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LocationModel {
    private SimpleStringProperty address, city, zipcode, serviceType;
    private SimpleIntegerProperty phone;

    public LocationModel(String address, String city, String zipcode, String serviceType, Integer phone) {
        this.address = new SimpleStringProperty(address);
        this.city = new SimpleStringProperty(city);
        this.zipcode = new SimpleStringProperty(zipcode);
        this.serviceType = new SimpleStringProperty(serviceType);
        this.phone = new SimpleIntegerProperty(phone);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city = new SimpleStringProperty(city);
    }

    public String getZipcode() {
        return zipcode.get();
    }

    public SimpleStringProperty zipcodeProperty() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = new SimpleStringProperty(zipcode);
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

    public int getPhone() {
        return phone.get();
    }

    public SimpleIntegerProperty phoneProperty() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = new SimpleIntegerProperty(phone);
    }
}
