package com.petcare.petcare.Services;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private Double price;

    /**
     *
     * Constructor
     *
     * @param name Name
     * @param price Price
     */
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    /**
     *
     * Get name
     *
     * @return Name
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Set name
     *
     * @param name Name
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get price
     *
     * @return Price
     *
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * Set price
     *
     * @param price Price
     *
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
