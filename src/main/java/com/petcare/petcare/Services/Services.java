package com.petcare.petcare.Services;

public class Services {
    private String client, service, serviceType, date, value;

    public Services(String client, String service, String serviceType, String date, String value) {
        this.client = client;
        this.service = service;
        this.serviceType = serviceType;
        this.date = date;
        this.value = value;
    }

    /**
     *
     * Get client
     *
     * @return Client
     *
     */
    public String getClient() {
        return client;
    }

    /**
     *
     * Set client
     *
     * @param client Client
     *
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     *
     * Get service
     *
     * @return Service
     */
    public String getService() {
        return service;
    }

    /**
     *
     * Set service
     *
     * @param service Service
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     *
     * Get service type
     *
     * @return Service type
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     *
     * Set service type
     *
     * @param serviceType Service type
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     *
     * Get date
     *
     * @return Date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * Set date
     *
     * @param date Date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * Get value
     *
     * @return Value
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * Set value
     *
     * @param value Value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
