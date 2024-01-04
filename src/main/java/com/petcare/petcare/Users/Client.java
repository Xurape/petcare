package com.petcare.petcare.Users;

public class Client extends User {
    private String NIF, name, surname, email, address;
    private Integer phone;

    public Client(String username, String password) {
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
     * Get the NIF of the user
     *
     * @return NIF of the user
     *
     */
    public String getNIF() {
        return NIF;
    }

    /**
     *
     * Set the NIF of the user
     *
     * @param NIF NIF of the user
     *
     */
    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    /**
     *
     * Get the address of the user
     *
     * @return Address of the user
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
     * Get the phone of the user
     *
     * @return Phone of the user
     *
     */
    public Integer getPhone() {
        return phone;
    }

    /**
     *
     * Set the phone of the user
     *
     * @param phone Phone of the user
     *
     */
    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
