package com.petcare.petcare.Users;

import java.util.List;

public class Company extends User {
    private String NIF, name, email, address;
    private List<Employee> employees;

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
     * Get the NIF of the company
     *
     * @return NIF of the company
     *
     */
    public String getNIF() {
        return NIF;
    }

    /**
     *
     * Set the NIF of the company
     *
     * @param NIF NIF of the company
     *
     */
    public void setNIF(String NIF) {
        this.NIF = NIF;
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
     * @param NIF NIF of the employee to remove
     *
     */
    public void removeEmployee(String NIF) {
        for (Employee employee : this.employees) {
            if (employee.getNIF().equals(NIF)) {
                this.employees.remove(employee);
                return;
            }
        }
    }
}
