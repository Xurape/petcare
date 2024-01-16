package com.petcare.petcare.Services;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

public class Appointments implements Serializable {
    private String client, service, date, value;
    private String location, company;
    private AppointmentsStatus status = AppointmentsStatus.PENDING;
    private String timestamp;
    private String reason;
    private Invoice invoice;

    /**
     *
     * Constructor
     *
     * @param client Client
     * @param service Service
     * @param location Location
     * @param company Company
     * @param date Date
     * @param value Value
     *
     */
    public Appointments(String client, String service, String location, String company, String date, String value) {
        this.client = client;
        this.service = service;
        this.location = location;
        this.company = company;
        this.date = date;
        this.value = value;
        Date d = new Date();

        if (d.getHours() < 10) {
            this.timestamp = "0" + d.getHours() + ":";
        } else {
            this.timestamp = d.getHours() + ":";
        }
        if (d.getMinutes() < 10) {
            this.timestamp += "0" + d.getMinutes() + ":";
        } else {
            this.timestamp += d.getMinutes() + ":";
        }
        if (d.getSeconds() < 10) {
            this.timestamp += "0" + d.getSeconds();
        } else {
            this.timestamp += d.getSeconds();
        }
    }

    /**
     *
     * Get invoice
     *
     * @return Invoice
     *
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     *
     * Set invoice
     *
     * @param invoice Invoice
     *
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     *
     * Get reason
     *
     * @return Reason
     *
     */
    public String getReason() {
        return reason;
    }

    /**
     *
     * Set reason
     *
     * @param reason Reason
     *
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *
     * Get status as string
     *
     * @return Status as string
     *
     */
    public String getStatusAsString() {
        switch (status) {
            case PENDING:
                return "Pendente";
            case PAID:
                return "Pago";
            case ACCEPTED:
                return "Aceite";
            case REJECTED:
                return "Rejeitado";
            default:
                return "Pendente";
        }
    }

    /**
     *
     * Get timestamp
     *
     * @return Timestamp
     *
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * Set timestamp
     *
     * @param timestamp Timestamp
     *
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
     * Set client as string
     *
     * @param client Client as string
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
     *
     */
    public String getService() {
        return service;
    }

    /**
     *
     * Set service
     *
     * @param service Service
     *
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     *
     * Set location
     *
     * @param location Location
     *
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * Get company
     *
     * @return Company
     *
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * Set company
     *
     * @param company Company
     *
     */
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

    /**
     *
     * Get status
     *
     * @return Status
     *
     */
    public AppointmentsStatus getStatus() {
        return status;
    }

    /**
     *
     * Set status
     *
     * @param status Status
     *
     */
    public void setStatus(AppointmentsStatus status) {
        this.status = status;
    }

    /**
     *
     * Get location
     *
     * @return Location
     *
     */
    public String getLocation() {
        return location;
    }
}
