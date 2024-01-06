package com.petcare.petcare.Services;

import com.petcare.petcare.Users.Company;

import java.io.Serializable;

public class Service implements Serializable {
    private String name, description;
    private ServiceType type;
    private double price;
    private Company company;

    /**
     *
     * Constructor
     *
     * @param name Name of the service
     * @param description Description of the service
     * @param type Type of the service
     * @param price Price of the service
     *
     */
    public Service(String name, String description, ServiceType type, double price, Company company) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static ServiceType getTypeFromString(String type) {
        switch(type) {
            case "Banho":
                return ServiceType.BATHING;
            case "Creche":
                return ServiceType.DAYCARE;
            case "Tosquia":
                return ServiceType.GROOMING;
            case "Hotel":
                return ServiceType.HOTEL;
            case "Babysitting":
                return ServiceType.SITTING;
            case "Spa":
                return ServiceType.SPA;
            case "Transporte":
                return ServiceType.TRANSPORT;
            case "Veterinário":
                return ServiceType.VETERINARY;
            case "Passeio":
                return ServiceType.WALKING;
            case "Treino":
                return ServiceType.TRAINING;
            default:
                return ServiceType.OTHER;
        }
    }

    /**
     *
     * Get the name of the service
     *
     * @return Name of the service
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set the name of the service
     *
     * @param name Name of the service
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get the description of the service
     *
     * @return Description of the service
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * Set the description of the service
     *
     * @param description Description of the service
     *
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * Get the type of the service
     *
     * @return Type of the service
     *
     */
    public ServiceType getType() {
        return type;
    }

    /**
     *
     * Set the type of the service
     *
     * @param type Type of the service
     *
     */
    public void setType(ServiceType type) {
        this.type = type;
    }

    /**
     *
     * Get the type of the service as a string
     *
     * @param type Type of the service
     * @return Type of the service as a string
     *
     */
    public static String getTypeString(ServiceType type) {
        switch(type) {
            case BATHING:
                return "Banho";
            case DAYCARE:
                return "Creche";
            case GROOMING:
                return "Tosquia";
            case HOTEL:
                return "Hotel";
            case SITTING:
                return "Babysitting";
            case SPA:
                return "Spa";
            case TRANSPORT:
                return "Transporte";
            case VETERINARY:
                return "Veterinário";
            case WALKING:
                return "Passeio";
            case TRAINING:
                return "Treino";
            default:
                return "Outro";
        }
    }

    /**
     *
     * Get the price of the service
     *
     * @return Price of the service
     *
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * Set the price of the service
     *
     * @param price Price of the service
     *
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
