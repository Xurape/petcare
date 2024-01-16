package com.petcare.petcare.Services;

import com.petcare.petcare.Users.Company;

import java.io.Serializable;
import java.util.List;

public class Location implements Serializable {
    private String address, city, serviceType, zipcode;
    private List<ServiceType> services;
    private int phone;
    private Company company;

    /**
     * Constructs a Location object with the specified parameters.
     *
     * @param address Address
     * @param city City
     * @param zipcode Zipcode
     * @param phone Phone
     * @param serviceType Service type
     * @param company Company
     */
    public Location(String address, String city, String zipcode, int phone, String serviceType, Company company) {
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.zipcode = zipcode;
        this.serviceType = serviceType;
        this.company = company;
    }

    /**
     *
     * Get services
     *
     * @return Services
     *
     */
    public List<ServiceType> getServices() {
        return services;
    }

    /**
     *
     * Set services
     *
     * @param services Services
     *
     */
    public void setServices(List<ServiceType> services) {
        this.services = services;
    }

    /**
     *
     * Get company
     *
     * @return Company
     *
     */
    public Company getCompany() {
        return company;
    }

    /**
     *
     * Set company
     *
     * @param company Company
     *
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     *
     * Get zipcode
     *
     * @return Zipcode
     *
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     *
     * Set zipcode
     *
     * @param zipcode Zipcode
     *
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     *
     * Get address
     *
     * @return Address
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * Set address
     *
     * @param address Address
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * Get city
     *
     * @return City
     *
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * Set city
     *
     * @param city City
     *
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * Get phone
     *
     * @return Phone
     *
     */
    public int getPhone() {
        return phone;
    }

    /**
     *
     * Set phone
     *
     * @param phone Phone
     *
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     *
     * Get service type
     *
     * @return Service type
     *
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     *
     * Set service type
     *
     * @param serviceType Service type
     *
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
