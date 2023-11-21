package com.petcare.petcare.Users;

public interface IUser {
    void setUsername(String username);
    String getUsername();
    void setPassword(String password) throws Exception;
    void setOnline(boolean isOnline);
    boolean isOnline();
    int getId();
    String getPassword() throws Exception;
    boolean checkPassword(String password) throws Exception;
}
