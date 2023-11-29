package com.petcare.petcare.Utils;

import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.Employee;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    private static Storage Storage = null;

    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Company> companies = new HashMap<>();
    private Map<String, Admin> admins = new HashMap<>();
    private Map<String, Employee> employees = new HashMap<>();

    public Storage (){};

    public Map<String, Client> getClients() {return clients;}
    public Map<String, Company> getCompanies() {return companies;}
    public Map<String, Admin> getAdmins() {return admins;}
    public Map<String, Employee> getEmployees() {return employees;}

    public static Storage getStorage() {

        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        if (Storage == null)
            Storage = new Storage();
        lock.unlock();

        return Storage;
    }

    public boolean userExists(String NIF) {
        if (this.clients.containsKey(NIF))
            return true;
        else if (this.companies.containsKey(NIF))
            return true;
        else if (this.admins.containsKey(NIF))
            return true;
        else if (this.employees.containsKey(NIF))
            return true;
        else
            return false;
    }

    public void serialize(String type) {
        String filename = null;
        try {
            switch(type) {
                case "users":
                    filename = "src\\\\main\\\\resources\\\\data\\\\userStorage.data";
                    break;
                default:
                    filename = "src\\\\main\\\\resources\\\\data\\\\data.data";
                    break;
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Storage data is saved on " + filename + "\n");
        } catch(IOException e) {
            System.out.println("Error while trying to serialize: " + e.getMessage());
        }
    }

    public static void deserialize(String type) {
        String filename = null;
        try {
            switch(type) {
                case "users":
                    filename = "src\\\\main\\\\resources\\\\data\\\\userStorage.data";
                    break;
                default:
                    filename = "src\\\\main\\\\resources\\\\data\\\\data.data";
                    break;
            }
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Storage = (Storage) in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException ex){
            System.out.println("Error while trying to deserialize: " + ex.getMessage());
        } catch(ClassNotFoundException ex){
            System.out.println("Storage not found. " + ex.getMessage());
        }
    }
}
