package com.petcare.petcare.Users;

public class Employee extends User {
    private String NIF, name, surname, email, address;
    private Company company;

    public Employee(String username, String password) {
        super(username, password);
    }

    /**
     *
     * Get the name of the employee
     *
     * @return Name of the employee
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set the name of the employee
     *
     * @param name Name of the employee
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get the surname of the employee
     *
     * @return Surname of the employee
     *
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * Set the surname of the employee
     *
     * @param surname Surname of the employee
     *
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * Get the email of the employee
     *
     * @return Email of the employee
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * Set the email of the employee
     *
     * @param email Email of the employee
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * Get the address of the employee
     *
     * @return Address of the employee
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * Set the address of the user
     *
     * @param address Address of the user
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * Get the NIF of the employee
     *
     * @return NIF of the employee
     *
     */
    public String getNIF() {
        return NIF;
    }

    /**
     *
     * Set the NIF of the user
     *
     * @param NIF NIF of the user
     *
     */
    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    /**
     *
     * Get the company of the employee
     *
     * @return Company of the employee
     *
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * Set the company of the Employee
     *
     * @param company Company of the employee
     *
     */
    public void setCompany(Company company) {
        this.company = company;
    }
}
