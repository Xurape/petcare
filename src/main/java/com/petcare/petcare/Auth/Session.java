package com.petcare.petcare.Auth;

import java.util.concurrent.locks.ReentrantLock;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.Employee;
import com.petcare.petcare.Users.User;
import com.petcare.petcare.Utils.Debug;
import com.petcare.petcare.Utils.Storage;
import java.io.Serializable;

public class Session implements Serializable {
    private static Session Session = null;
    private User currentUser;

    /**
     *
     * Constructor
     *
     * @param user User
     *
     */
    private Session(User user) {
        this.currentUser = user;
    }

    /**
     *
     * Empty constructor
     *
     */
    private Session() {}

    /**
     *
     * Set current user
     *
     * @param user User
     *
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     *
     * Get current user
     *
     * @return Current user
     *
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * Get session ID
     *
     * @return Session ID
     *
     */
    public int getSessionID() {
        return currentUser.hashCode();
    }

    /**
     *
     * Register a user
     *
     * @param user User
     * @return True if user was registered, false otherwise
     *
     */
    public boolean register(User user) {
        if (user instanceof Client)
            Storage.getStorage().getClients().put(user.getnif(), (Client) user);
        else if (user instanceof Company)
            Storage.getStorage().getCompanies().put(user.getnif(), (Company) user);
        else if (user instanceof Employee)
            Storage.getStorage().getEmployees().put(user.getnif(), (Employee) user);
        else if (user instanceof Admin)
            Storage.getStorage().getAdmins().put(user.getnif(), (Admin) user);
        else
            return false;

        try {
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
            Debug.success("User " + user.getnif() + " registered successfully!", true, true);
        } catch(CouldNotSerializeException e) {
            return false;
        } 

        return true;
    }

    /**
     *
     * Login a user
     *
     * @param username Username of the user
     * @param password Password of the user
     * @return True if user was logged in, false otherwise
     *
     */
    public boolean login(String username, String password) {
        if (this.getCurrentUser() != null)
            return true;

        Iterable<Client> clients = Storage.getStorage().getClients().values();
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                this.setCurrentUser(client);
                Debug.success("Client " + client.getnif() + " logged in successfully!", true, true);
                return true;
            }
        }

        Iterable<Company> companies = Storage.getStorage().getCompanies().values();
        for (Company company : companies) {
            if (company.getUsername().equals(username) && company.getPassword().equals(password)) {
                this.setCurrentUser(company);
                Debug.success("Company " + company.getnif() + " logged in successfully!", true, true);
                return true;
            }
        }

        Iterable<Employee> employees = Storage.getStorage().getEmployees().values();
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                this.setCurrentUser(employee);
                Debug.success("Employee " + employee.getnif() + " logged in successfully!", true, true);
                return true;
            }
        }

        Iterable<Admin> admins = Storage.getStorage().getAdmins().values();
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                this.setCurrentUser(admin);
                Debug.success("Admin " + admin.getnif() + " logged in successfully!", true, true);
                return true;
            }
        }

        Debug.print("Unable to locate user with username " + username + " and password " + password + "!", true, true);
        return false;
    }

    /**
     *
     * Log out the current user
     *
     */
    public void logout() {
        Debug.success("User logged out sucessfully!", true, true);
        this.setCurrentUser(null);
    }

    /**
     *
     * Get the current session
     *
     * @return Current session object
     *
     */
    public static Session getSession() {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        if (Session == null)
            Session = new Session();
        lock.unlock();

        return Session;
    }
}
