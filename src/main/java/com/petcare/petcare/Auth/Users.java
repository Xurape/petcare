package com.petcare.petcare.Auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.petcare.petcare.Exceptions.UserExistsException;
import com.petcare.petcare.Exceptions.UserListIsNullException;
import com.petcare.petcare.Exceptions.UserNotFoundException;
import com.petcare.petcare.Users.*;
import com.petcare.petcare.Utils.Storage;

public class Users implements Serializable {
    private List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public Users() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return this.users;
    }

    public boolean userExists(User user) {
        for (User usr : this.users) {
            if (usr.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) throws UserExistsException {
        if (this.userExists(user)) {
            throw new UserExistsException("O utilizador já existe!");
        }

        for(User usr : this.users) {
            if(usr.getUsername().equals(user.getUsername()))
                throw new UserExistsException("O utilizador já existe!");
            else if (usr.getNIF().equals(user.getNIF()))
                throw new UserExistsException("O utilizador já existe!");
        }

        if(user instanceof Admin)
            Storage.getStorage().getAdmins().put(user.getNIF(), (Admin) user);
        else if(user instanceof Company)
            Storage.getStorage().getCompanies().put(user.getNIF(), (Company) user);
        else if(user instanceof Employee)
            Storage.getStorage().getEmployees().put(user.getNIF(), (Employee) user);
        else if(user instanceof Client)
            Storage.getStorage().getClients().put(user.getNIF(), (Client) user);

        Storage.getStorage().serialize("users");
    }

    public void removeUser(User user) throws UserNotFoundException {
        this.users.remove(user);
    }
}
