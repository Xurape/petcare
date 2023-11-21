package com.petcare.petcare.Users;

import com.petcare.petcare.Crypt.Crypt;

public class User {
    private String username, password;
    private boolean isOnline = false;

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
        this.password = Crypt.encrypt(password);
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return this.isOnline;
    }
}
