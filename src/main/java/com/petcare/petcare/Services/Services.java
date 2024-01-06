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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
