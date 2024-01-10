package com.petcare.petcare.Users;

public class Employee extends User {
    private String nif, name, surname, email, address;
    private Company company;
    private EmployeeType employeeType;
    private String professionalNumber;

    public Employee(String username, String password) {
        super(username, password);
    }

    public Employee(String nif, String name, String surname, String email, String address, Company company, EmployeeType employeeType) {
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.company = company;
        this.employeeType = employeeType;
    }

    public String getProfessionalNumber() {
        return professionalNumber;
    }

    public void setProfessionalNumber(String professionalNumber) {
        this.professionalNumber = professionalNumber;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public static EmployeeType convertStringToEmployeeType(String employeeType) {
        switch (employeeType) {
            case "Veterinário":
                return EmployeeType.VETERINARIO;
            case "Auxiliar":
                return EmployeeType.AUXILIAR;
            case "Tosquiador":
                return EmployeeType.TOSQUIADOR;
            case "Passeador":
                return EmployeeType.PASSEADOR;
            default:
                return null;
        }
    }



    public String getEmployeeTypeAsString() {
        switch (employeeType) {
            case VETERINARIO:
                return "Veterinário";
            case AUXILIAR:
                return "Auxiliar";
            case TOSQUIADOR:
                return "Tosquiador";
            case PASSEADOR:
                return "Passeador";
            default:
                return "Outro";
        }
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
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
     * Get the nif of the employee
     *
     * @return nif of the employee
     *
     */
    public String getnif() {
        return nif;
    }

    /**
     *
     * Set the nif of the user
     *
     * @param nif nif of the user
     *
     */
    public void setnif(String nif) {
        this.nif = nif;
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
