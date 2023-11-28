package com.petcare.petcare.Auth;

import java.util.ArrayList;
import java.util.List;

import com.petcare.petcare.Exceptions.UserExistsException;
import com.petcare.petcare.Exceptions.UserListIsNullException;
import com.petcare.petcare.Exceptions.UserNotFoundException;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.User;
import com.petcare.petcare.Users.UserType;

public class Users implements IUsers {
    private List<User> users;
    private User currentUser;

    public Users(List<User> users) {
        this.users = users;
    }

    public Users() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void addUser(User user) throws UserExistsException {
        if (this.userExists(user)) {
            throw new UserExistsException("O utilizador já existe!");
        }

        for(User usr : this.users) {
            if(usr.getUsername().equals(user.getUsername()))
                throw new UserExistsException("O utilizador já existe!");
            else if (usr.getId() == user.getId())
                throw new UserExistsException("O utilizador já existe!");
        }

        this.users.add(user);
    }

    public void removeUser(User user) throws UserNotFoundException {
        this.users.remove(user);
    }

    public void removeUserById(int userID) throws UserNotFoundException {
        for (User user : this.users) {
            if (user.getId() == userID) {
                this.users.remove(user);
                break;
            }
        }
    }

    public void updateUser(int userID, User user) throws UserNotFoundException {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId() == userID) {
                this.users.set(i, user);
                return;
            }
        }

        throw new UserNotFoundException("Utilizador não encontrado!");
    }

    public int getUsersAmount() {
        return this.users.size();
    }

    public int getLastUserId() throws UserListIsNullException {
        return this.users.get(this.users.size() - 1).getId();
    }

    public User getUserById(int userID) throws UserNotFoundException {
        for (User user : this.users) {
            if (user.getId() == userID) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean userExists(User user) {
        return this.users.contains(user);
    }

    public boolean userExistsByUsername(String username) {
        return this.users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public boolean userExistsById(int userId) {
        return this.users.stream().anyMatch(user -> user.getId() == userId);
    }

    public boolean isUserOnline(User user) {
        for(User userObject : this.users) {
            if(userObject.equals(user)) {
                return userObject.isOnline();
            }
        }
        
        return false;
    }

    public boolean isUserOnlineById(int userID) {
        for(User user : this.users) {
            if(user.getId() == userID) {
                return user.isOnline();
            }
        }

        return false;
    }

    public boolean isUserOnlineByUsername(String username) {
        for(User user : this.users) {
            if(user.getUsername().equals(username)) {
                return user.isOnline();
            }
        }
        
        return false;
    }

    public boolean isUserAdmin(User user) {
        return user instanceof Admin;
    }

    public boolean login(String username, String password) {
        for (User user : this.users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                user.setOnline(true);
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void loginByUser(User user) throws UserNotFoundException {
        if(this.userExistsByUsername(user.getUsername()))
            throw new UserNotFoundException("O utilizador não existe.");

        user.setOnline(true);
        this.updateUser(user.getId(), user);
        this.currentUser = user;
    }

    public boolean register(User user) throws UserExistsException, UserNotFoundException {
        if (userExists(user)) {
            throw new UserExistsException("O utilizador já existe!");
        }
        this.users.add(user);
        user.setOnline(true);
        this.currentUser = user;
        return true;
    }
}
