package com.petcare.petcare.Auth;

import java.util.concurrent.locks.ReentrantLock;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.Employee;
import com.petcare.petcare.Users.User;
import com.petcare.petcare.Utils.Storage;
import java.io.Serializable;

public class Session implements Serializable {
    private static Session Session = null;
    private User currentUser;

    private Session(User user) {
        this.currentUser = user;
    }
    private Session() {}

    public void setCurrentUser(User user) {
        currentUser = user;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }

    public int getSessionID() {
        return currentUser.hashCode();
    }

    public boolean register(User user) {
        if (user instanceof Client)
            Storage.getStorage().getClients().put(user.getNIF(), (Client) user);
        else if (user instanceof Company)
            Storage.getStorage().getCompanies().put(user.getNIF(), (Company) user);
        else if (user instanceof Employee)
            Storage.getStorage().getEmployees().put(user.getNIF(), (Employee) user);
        else if (user instanceof Admin)
            Storage.getStorage().getAdmins().put(user.getNIF(), (Admin) user);
        else
            return false;

        try {
            Storage.getStorage().serialize("./src/main/resources/data/users.db");
        } catch(CouldNotSerializeException e) {
            return false;
        } 

        return true;
    }

    public boolean login(String username, String password) {
        if (this.getCurrentUser() != null)
            return true;

        Iterable<Client> clients = Storage.getStorage().getClients().values();
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                this.setCurrentUser(client);
                return true;
            }
        }

        Iterable<Company> companies = Storage.getStorage().getCompanies().values();
        for (Company company : companies) {
            if (company.getUsername().equals(username) && company.getPassword().equals(password)) {
                this.setCurrentUser(company);
                return true;
            }
        }

        Iterable<Employee> employees = Storage.getStorage().getEmployees().values();
        for (Employee employe : employees) {
            if (employe.getUsername().equals(username) && employe.getPassword().equals(password)) {
                this.setCurrentUser(employe);
                return true;
            }
        }

        Iterable<Admin> admins = Storage.getStorage().getAdmins().values();
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                this.setCurrentUser(admin);
                return true;
            }
        }

        return false;
    }

    public void logout() {
        this.setCurrentUser(null);
    }

    public static Session getSession() {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        if (Session == null)
            Session = new Session();
        lock.unlock();

        return Session;
    }
}
