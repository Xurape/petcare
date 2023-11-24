package com.petcare.petcare.Users;

public class User implements IUser {
    private String username, password;
    private boolean isOnline = false;
    private int id;

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

    public void setPassword(String password) throws Exception {
        this.password = password;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return this.isOnline;
    }
    
    public int getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }
}
