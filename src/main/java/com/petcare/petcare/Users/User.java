package com.petcare.petcare.Users;
import java.io.Serializable;

public class User implements IUser, Serializable {
    private String nif, username, password, address, citizenNumber;
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
     * Get the NIF
     *
     * @return NIF
     *
     */
    public String getNif() {
        return nif;
    }

    /**
     *
     * Set the NIF
     *
     * @param nif NIF
     *
     */
    public void setNif(String nif) {
        this.nif = nif;
    }

    /**
     *
     * Get the citizen number of the user
     *
     * @return Citizen number of the user
     *
     */
    public String getCitizenNumber() {
        return citizenNumber;
    }

    /**
     *
     * Set the citizen number of the user
     *
     * @param citizenNumber Citizen number of the user
     *
     */
    public void setCitizenNumber(String citizenNumber) {
        this.citizenNumber = citizenNumber;
    }
}
