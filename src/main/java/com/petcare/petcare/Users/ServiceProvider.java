package com.petcare.petcare.Users;

import java.io.Serializable;

public class ServiceProvider extends User implements Serializable {
    private String nif, name, surname, email, address;
    private int phone;
    private Company company;

    /**
     * Constructor
     *
     * @param username Username of the user
     * @param password Password of the user
     */
    public ServiceProvider(String username, String password) {
        super(username, password);
    }

    /**
     * Constructor
     *
     * @param nif NIF
     * @param name Name
     * @param email Email
     * @param address Address
     */
    public ServiceProvider(String username, String password, String nif, String name, String email, String address) {
        super(username, password);
        this.nif = nif;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     *
     * Get surname
     * @return Surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * Set surname
     * @param surname Surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * Get phone
     * @return Phone
     */
    public int getPhone() {
        return phone;
    }

    /**
     *
     * Set phone
     * @param phone Phone
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     *
     * Get NIF
     * @return NIF
     */
    public String getNif() {
        return nif;
    }

    /**
     *
     * Set NIF
     * @param nif NIF
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     *
     * Get address
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * Set address
     * @param address Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * Get company
     * @return Company
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * Set company
     * @param company Company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * Get name
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set name
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get email
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * Set email
     * @param email Email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
