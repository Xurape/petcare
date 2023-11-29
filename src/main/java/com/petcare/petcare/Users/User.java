package com.petcare.petcare.Users;

public class User implements IUser {
    private String NIF, username, password, address;
    private boolean isOnline = false;

    public User() {}
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username; 
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
