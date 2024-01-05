package com.petcare.petcare.Users;

import java.util.ArrayList;
import java.util.List;

public class Company extends User {
    private String nif, name, email, address;
    private List<Employee> employees = new ArrayList<>();

    /**
     *
     * Constructor
     *
     * @param username Username of the company
     * @param password Password of the company
     *
     */
    public Company(String username, String password) {
        super(username, password);
    }

    /**
     *
     * Get the name of the company
     *
     * @return Name of the company
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set the name of the company
     *
     * @param name Name of the company
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get the email of the company
     *
     * @return Email of the company
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * Set the email of the company
     *
     * @param email Email of the company
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * Get the address of the company
     *
     * @return Address of the company
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * Set the address of the company
     *
     * @param address Address of the company
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * Get the nif of the company
     *
     * @return nif of the company
     *
     */
    public String getnif() {
        return nif;
    }

    /**
     *
     * Set the nif of the company
     *
     * @param nif nif of the company
     *
     */
    public void setnif(String nif) {
        this.nif = nif;
    }

    /**
     *
     * Get the employees of the company
     *
     * @return Employees of the company
     *
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     *
     * Set the employees of the company
     *
     * @param employees Employees of the company
     *
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     *
     * Add an employee to the company
     *
     * @param employee Employee to add
     *
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     *
     * Remove an employee from the company
     *
     * @param nif nif of the employee to remove
     *
     */
    public void removeEmployee(String nif) {
        for (Employee employee : this.employees) {
            if (employee.getnif().equals(nif)) {
                this.employees.remove(employee);
                return;
            }
        }
    }
}
