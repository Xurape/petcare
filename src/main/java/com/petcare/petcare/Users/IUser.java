package com.petcare.petcare.Users;

public interface IUser {
    void setUsername(String username);
    String getUsername();
    void setPassword(String password) throws Exception;
    void setOnline(boolean isOnline);
    boolean isOnline();
    String getPassword() throws Exception;
    boolean checkPassword(String password) throws Exception;
    public String getNIF();
    public void setNIF(String NIF);
}
