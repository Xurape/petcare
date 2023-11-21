package com.petcare.petcare.Auth;

import java.util.ArrayList;
import java.util.List;

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

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void removeUserById(int userID) {
        for (User user : this.users) {
            if (user.getId() == userID) {
                this.users.remove(user);
                break;
            }
        }
    }

    public void updateUser(int userID, User user) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId() == userID) {
                this.users.set(i, user);
                break;
            }
        }
    }

    public int getUsersAmount() {
        return this.users.size();
    }

    public int getLastUserId() {
        return this.users.get(this.users.size() - 1).getId();
    }

    public User getUserById(int userID) {
        for (User user : this.users) {
            if (user.getId() == userID) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean userExists(User user) {
        return false;
    }

    public boolean userExistsByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        
        return false;
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
        if (user instanceof Admin) {
            return true;
        }

        return false;
    }

    public boolean login(String username, String password) throws Exception {
        if(!this.userExistsByUsername(username))
            return false;

        for(User user : this.users) {
            if(user.getUsername().equals(username)) {
                if(user.checkPassword(password)) {
                    user.setOnline(true);
                    return true;
                }
            }
        }

        return false;
    }

    public boolean loginByUser(User user) {
        if(!this.userExistsByUsername(user.getUsername()))
            return false;

        user.setOnline(true);
        this.updateUser(user.getId(), user);
        this.currentUser = user;

        return true;
    }

    public boolean register(User user, UserType type) {
        if(this.userExists(user)) {
            return false;
        }

        this.addUser(user);
        this.loginByUser(user);

        return false;
    }
}
