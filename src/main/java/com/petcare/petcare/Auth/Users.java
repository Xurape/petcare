package com.petcare.petcare.Auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Exceptions.UserExistsException;
import com.petcare.petcare.Exceptions.UserListIsNullException;
import com.petcare.petcare.Exceptions.UserNotFoundException;
import com.petcare.petcare.Users.*;
import com.petcare.petcare.Utils.Storage;

public class Users implements Serializable {
    private List<User> users;

    /**
     *
     * Constructor
     *
     * @param users List of users
     *
     */
    public Users(List<User> users) {
        this.users = users;
    }

    /**
     *
     * Constructor
     *
     */
    public Users() {
        this.users = new ArrayList<>();
    }

    /**
     *
     * Get users
     *
     * @return List of users
     *
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     *
     * See if user exists
     *
     * @param user User
     * @return True if user exists, false otherwise
     *
     */
    public boolean userExists(User user) {
        for (User usr : this.users) {
            if (usr.equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Add user to the users list
     *
     * @param user User
     * @throws UserExistsException UserExistsException
     * @throws CouldNotSerializeException CouldNotSerializeException
     *
     */
    public void addUser(User user) throws UserExistsException, CouldNotSerializeException {
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

        Storage.getStorage().serialize("./src/main/resources/data/storage.db");
    }

    /**
     *
     * Remove user from the users list
     *
     * @param user User
     * @throws UserNotFoundException UserNotFoundException
     *
     */
    public void removeUser(User user) throws UserNotFoundException {
        this.users.remove(user);
    }
}
