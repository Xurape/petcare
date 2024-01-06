package com.petcare.petcare.Services;

import java.io.Serializable;

public class Appointments implements Serializable {
    private String client, service, date, value;
    private String location, company;
    private AppointmentsStatus status = AppointmentsStatus.PENDING;

    public Appointments(String client, String service, String location, String company, String date, String value) {
        this.client = client;
        this.service = service;
        this.location = location;
        this.company = company;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * Get the date of the appointment
     *
     * @return Date of the appointment
     *
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * Set the date of the appointment
     *
     * @param date Date of the appointment
     *
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * Get the value of the appointment
     *
     * @return Value of the appointment
     *
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * Set the value of the appointment
     *
     * @param value Value of the appointment
     *
     */
    public void setValue(String value) {
        this.value = value;
    }

    public AppointmentsStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentsStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }
}
