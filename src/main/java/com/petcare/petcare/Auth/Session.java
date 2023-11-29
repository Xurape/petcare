package com.petcare.petcare.Auth;

import java.util.concurrent.locks.ReentrantLock;

import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.User;
import com.petcare.petcare.Utils.Storage;

import impl.org.controlsfx.collections.MappingChange.Map;

public class Session {
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

    public boolean login(String username, String password) {
        if (this.getCurrentUser() != null)
            return true;

        // Loop through the getClients, getAdmins and etc from Storage class to check if user exists
        // for (Client client : Storage.getStorage().getClients()) {
        //     if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
        //         this.setCurrentUser(client);
        //         return true;
        //     }
        // }

        return false;
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
