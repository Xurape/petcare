package com.petcare.petcare.Services;

import java.io.Serializable;

public class Service implements Serializable {
    private String name, description;
    private ServiceType type;
    private double price;

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
    public Service(String name, String description, ServiceType type, double price) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
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
