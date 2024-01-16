package com.petcare.petcare.Utils;

import com.petcare.petcare.Exceptions.CouldNotDeserializeException;
import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Services.Appointments;
import com.petcare.petcare.Services.Invoice;
import com.petcare.petcare.Services.Location;
import com.petcare.petcare.Services.Service;
import com.petcare.petcare.Users.*;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Storage implements Serializable {
    private static Storage storage = null;

    private Double petcareBalance = 0.0;

    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Company> companies = new HashMap<>();
    private Map<String, ServiceProvider> serviceProviders = new HashMap<>();
    private Map<String, Admin> admins = new HashMap<>();
    private Map<String, Employee> employees = new HashMap<>();
    private List<DeskEmployee> deskEmployees = new ArrayList<>();
    private List<Service> services = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private List<Appointments> appointments = new ArrayList<>();
    private List<Invoice> invoices = new ArrayList<>();

    public Storage (){};

    /**
     *
     * Getters for each object
     *
     * @see #clients
     * @see #companies
     * @see #admins
     * @see #employees
     * @see #services
     * @return Map of objects
     *
     */
    public Map<String, Client> getClients() {return clients;}

    /**
     * Get the companies
     *
     * @return Map of companies
     */
    public Map<String, Company> getCompanies() {return companies;}

    /**
     *
     * Get the service providers
     *
     * @return Map of service providers
     *
     */
    public Map<String, ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    /**
     *
     * Get the admins
     *
     * @return Map of admins
     *
     */
    public Map<String, Admin> getAdmins() {return admins;}

    /**
     *
     * Get the employees
     *
     * @return Map of employees
     *
     */
    public Map<String, Employee> getEmployees() {return employees;}

    /**
     *
     * Get the desk employees
     *
     * @return List of desk employees
     *
     */
    public List<DeskEmployee> getDeskEmployees() {
        return deskEmployees;
    }

    /**
     *
     * Get the services
     *
     * @return List of services
     *
     */
    public List<Service> getServices() {return services;}

    /**
     *
     * Get the locations
     *
     * @return List of locations
     *
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     *
     * Get the appointments
     *
     * @return List of appointments
     *
     */
    public List<Invoice> getInvoices() {
        return invoices;
    }

    /**
     *
     * Get the appointments
     *
     * @return List of appointments
     */
    public List<Appointments> getAppointments() {
        return appointments;
    }

    /**
     *
     * Get petcare balance
     *
     * @return Petcare balance
     *
     */
    public Double getPetcareBalance() {
        return petcareBalance;
    }

    /**
     *
     * Set petcare balance
     *
     * @param petcareBalance Petcare balance
     *
     */
    public void setPetcareBalance(Double petcareBalance) {
        this.petcareBalance = petcareBalance;
    }

    /**
     *
     * Get the storage instance
     *
     * @see #Storage()
     * @return Storage object
     *
     */
    public static Storage getStorage() {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        if (storage == null)
            storage = new Storage();
        lock.unlock();

        return storage;
    }

    public Employee getEmployeeByName(String name, String surname) {
        for (Company company : this.companies.values()) {
            for(Employee employee : company.getEmployees()) {
                if (employee.getName().equals(name) && employee.getSurname().equals(surname))
                    return employee;
            }
        }
        return null;
    }

    public Company getCompanyByName(String name) {
        for (Company company : this.companies.values()) {
            if (company.getName().equals(name))
                return company;
        }
        return null;
    }

    public DeskEmployee getDeskEmployeeByName(String name, String surname) {
        for(DeskEmployee deskEmployee : Storage.getStorage().getDeskEmployees()) {
            if (deskEmployee.getName().equals(name) && deskEmployee.getSurname().equals(surname))
                return deskEmployee;
        }
        return null;
    }

    public Location getLocationByAddress(String address) {
        for(Location location : Storage.getStorage().getLocations()) {
            if (location.getAddress().equals(address))
                return location;
        }
        return null;
    }

    /**
     *
     * Check if the user exists in the storage
     *
     * @param NIF NIF of the user
     * @return True if the user exists, false otherwise
     *
     */
    public boolean userExists(String NIF) {
        if (this.clients.containsKey(NIF))
            return true;
        else if (this.companies.containsKey(NIF))
            return true;
        else if (this.admins.containsKey(NIF))
            return true;
        else return this.employees.containsKey(NIF);
    }

    /**
     *
     * Add a new client to the storage
     *
     * @param services List of services
     *
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     *
     * Serialize all content in the storage object
     *
     * @param filename Name of the file to serialize
     * @throws CouldNotSerializeException Could not serialize exception
     *
     */
    public void serialize(String filename) throws CouldNotSerializeException {
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            Debug.success("Serialized data is saved in " + filename + "\n", true, true);
        } catch(IOException ex){
            throw new CouldNotSerializeException("ErrorSerialize: " + ex.getMessage());
        }
    }

    /**
     *
     * Deserialize all content in the storage object
     *
     * @param filename Name of the file to deserialize
     * @throws CouldNotDeserializeException Could not deserialize exception
     * @throws ClassNotFoundException Class not found exception
     *
     */
    public static void deserialize(String filename) throws CouldNotDeserializeException, ClassNotFoundException {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            storage = (Storage) in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException ex){
            throw new CouldNotDeserializeException("ErrorDeserialize: " + ex.getMessage());
        } catch(ClassNotFoundException ex){
            throw new ClassNotFoundException("Storage class not found. " + ex.getMessage());
        }
    }
}
