package com.petcare.petcare.Services;

import java.io.Serializable;

public class Location implements Serializable {
    private String address, city, serviceType, zipcode;
    private int phone;

    public Location(String address, String city, String zipcode, int phone, String serviceType) {
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.zipcode = zipcode;
        this.serviceType = serviceType;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
