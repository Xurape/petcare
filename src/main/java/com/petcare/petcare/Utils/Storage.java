package com.petcare.petcare.Utils;

import com.petcare.petcare.Exceptions.CouldNotDeserializeException;
import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Users.Admin;
import com.petcare.petcare.Users.Client;
import com.petcare.petcare.Users.Company;
import com.petcare.petcare.Users.Employee;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Storage implements Serializable {
    private static Storage storage = null;

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
        if (storage == null)
            storage = new Storage();
        lock.unlock();

        return storage;
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

    public void serialize(String filename) throws CouldNotSerializeException {
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + filename + "\n");
        } catch(IOException ex){
            System.out.println("ErrorSerialize: " + ex.getMessage());
        }
    }

    public static void deserialize(String filename) throws CouldNotDeserializeException {

        try{
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            storage = (Storage) in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException ex){
            System.out.println("ErrorDeserialize: " + ex.getMessage());
        } catch(ClassNotFoundException ex){
            System.out.println("Repository class not found. " + ex.getMessage());
        }
    }
}
