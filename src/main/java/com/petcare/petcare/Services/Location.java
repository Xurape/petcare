package com.petcare.petcare.Services;

import java.io.Serializable;

public class Location implements Serializable {
    private String address, city, zipcode;
    private int phone;
    private ServiceType serviceType;

    public Location(String address, String city, String zipcode, int phone, ServiceType serviceType) {
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
        this.phone = phone;
        this.serviceType = serviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
