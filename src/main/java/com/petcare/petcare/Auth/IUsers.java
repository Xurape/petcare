package com.petcare.petcare.Auth;

import java.util.List;

import com.petcare.petcare.Exceptions.UserExistsException;
import com.petcare.petcare.Exceptions.UserListIsNullException;
import com.petcare.petcare.Exceptions.UserNotFoundException;
import com.petcare.petcare.Users.User;

import java.util.List;

public interface IUsers {
    List<User> getUsers();

    void addUser(User user) throws UserExistsException;

    void removeUser(User user) throws UserNotFoundException;

    void removeUserById(int userID) throws UserNotFoundException;

    void updateUser(int userID, User user) throws UserNotFoundException;

    int getUsersAmount();

    int getLastUserId() throws UserListIsNullException;

    User getUserById(int userID) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

    boolean userExists(User user);

    boolean userExistsByUsername(String username);

    boolean isUserOnline(User user);

    boolean isUserOnlineById(int userID);

    boolean isUserOnlineByUsername(String username);

    boolean isUserAdmin(User user);

    boolean login(String username, String password);

    void loginByUser(User user) throws UserNotFoundException;

    boolean register(User user) throws UserExistsException, UserNotFoundException;
}
