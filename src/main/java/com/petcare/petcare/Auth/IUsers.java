package com.petcare.petcare.Auth;

import java.util.List;

import com.petcare.petcare.Users.User;

public interface IUsers {
    List<User> getUsers();
    void addUser(User user);
    void removeUser(User user);
    void removeUserById(int userID);
    void updateUser(int userID, User user);
    int getUsersAmount();
    int getLastUserId();
    User getUserById(int userID);
    User getUserByUsername(String username);
    boolean userExists(User user);  
    boolean userExistsByUsername(String username);
    boolean isUserOnline(User user);
    boolean isUserOnlineById(int userID);
    boolean isUserOnlineByUsername(String username);
    boolean isUserAdmin(User user);
    boolean login(String username, String password) throws Exception;
}

