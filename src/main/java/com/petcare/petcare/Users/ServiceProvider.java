package com.petcare.petcare.Users;

import java.io.Serializable;

public class ServiceProvider extends User implements Serializable {
    private String nif, name, email, address;
    private Company company;

    public ServiceProvider(String username, String password) {
        super(username, password);
    }

    public ServiceProvider(String username, String password, String nif, String name, String email, String address) {
        super(username, password);
        this.nif = nif;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
