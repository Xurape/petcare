package com.petcare.petcare.Users;

public class Admin extends User {
    private String nif, name, surname, email;

    /**
     *
     * Constructor
     *
     * @param username Username of the user
     * @param password Password of the user
     *
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     *
     * Get the name of the user
     *
     * @return Name of the user
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set the name of the user
     *
     * @param name Name of the user
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get the surname of the user
     *
     * @return Surname of the user
     *
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * Set the surname of the user
     *
     * @param surname Surname of the user
     *
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * Get the email of the user
     *
     * @return Email of the user
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * Set the email of the user
     *
     * @param email Email of the user
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * Get the nif of the user
     *
     * @return nif of the user
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
