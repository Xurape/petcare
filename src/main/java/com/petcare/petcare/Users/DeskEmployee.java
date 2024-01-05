package com.petcare.petcare.Users;

public class DeskEmployee extends User {
    private String nif, name, surname, email, address;

    public DeskEmployee(String username, String password) {
        super(username, password);
    }

    public DeskEmployee(String nif, String name, String surname, String email, String address) {
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
    }

    /**
     *
     * Get the name of the employee
     *
     * @return Name of the employee
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set the name of the employee
     *
     * @param name Name of the employee
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get the surname of the employee
     *
     * @return Surname of the employee
     *
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * Set the surname of the employee
     *
     * @param surname Surname of the employee
     *
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * Get the email of the employee
     *
     * @return Email of the employee
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * Set the email of the employee
     *
     * @param email Email of the employee
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * Get the address of the employee
     *
     * @return Address of the employee
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * Set the address of the user
     *
     * @param address Address of the user
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * Get the nif of the employee
     *
     * @return nif of the employee
     *
     */
    public String getnif() {
        return nif;
    }

    /**
     *
     * Set the nif of the user
     *
     * @param nif nif of the user
     *
     */
    public void setnif(String nif) {
        this.nif = nif;
    }
}
