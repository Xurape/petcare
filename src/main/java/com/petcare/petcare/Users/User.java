package com.petcare.petcare.Users;
import java.io.Serializable;

public class User implements IUser, Serializable {
    private String NIF, username, password, address;
    private boolean isOnline = false;

    public User() {}

    /**
     *
     * Constructor
     *
     * @param username Username of the user
     * @param password Password of the user
     *
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     * Set the username of the user
     *
     * @param username Username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * Get the user username
     *
     * @return Username of the user
     *
     */
    public String getUsername() {
        return this.username; 
    }

    /**
     *
     * Set the password of the user
     *
     * @param password Password of the user
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * Set the online status of the user
     *
     * @param isOnline Online status of the user
     *
     */
    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     *
     * Get the online status of the user
     *
     * @return Online status of the user
     *
     */
    public boolean isOnline() {
        return this.isOnline;
    }

    /**
     *
     * Get the password of the user
     *
     * @return Password of the user
     *
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * Check if the user's password is correct
     *
     * @param password Password to check
     * @return True if the password is correct, false otherwise
     *
     */
    public boolean checkPassword(String password) {
        return password.equals(this.password);
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
}
