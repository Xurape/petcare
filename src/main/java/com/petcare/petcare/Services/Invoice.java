package com.petcare.petcare.Services;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
    private Appointments appointment;
    private String client, service, date, value;
    private String location, company, method;
    private InvoiceStatus status = InvoiceStatus.PENDING;
    private String timestamp;

    /**
     * Constructs an Invoice object with the specified parameters.
     *
     * @param appointment The appointment associated with the invoice.
     * @param client      The client's name.
     * @param service     The service provided.
     * @param location    The location where the service is provided.
     * @param company     The company providing the service.
     * @param value       The value of the invoice.
     * @param method      The payment method used.
     */
    public Invoice(Appointments appointment, String client, String service, String location, String company, String value, String method) {
        this.client = client;
        this.service = service;
        this.location = location;
        this.company = company;
        Date d = new Date();
        // get date as dd/MM/yyyy
        this.date = d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900);
        this.value = value;
        this.method = method;
        this.appointment = appointment;

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
     * Retrieves the associated appointment.
     *
     * @return The appointment associated with the invoice.
     */
    public Appointments getAppointment() {
        return appointment;
    }

    /**
     * Sets the associated appointment.
     *
     * @param appointment The appointment associated with the invoice.
     */
    public void setAppointment(Appointments appointment) {
        this.appointment = appointment;
    }

    /**
     * Retrieves the client's name.
     *
     * @return The client's name.
     */
    public String getClient() {
        return client;
    }

    /**
     * Sets the client's name.
     *
     * @param client The client's name.
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Retrieves the service provided.
     *
     * @return The service provided.
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service provided.
     *
     * @param service The service provided.
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Retrieves the date of the invoice.
     *
     * @return The date of the invoice.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the invoice.
     *
     * @param date The date of the invoice.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the value of the invoice.
     *
     * @return The value of the invoice.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the invoice.
     *
     * @param value The value of the invoice.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Retrieves the location where the service is provided.
     *
     * @return The location where the service is provided.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location where the service is provided.
     *
     * @param location The location where the service is provided.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Retrieves the company providing the service.
     *
     * @return The company providing the service.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company providing the service.
     *
     * @param company The company providing the service.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Retrieves the payment method used.
     *
     * @return The payment method used.
     */
    public InvoiceStatus getStatus() {
        return status;
    }

    /**
     * Sets the payment method used.
     *
     * @param status The payment method used.
     */
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    /**
     * Retrieves the timestamp of the invoice.
     *
     * @return The timestamp of the invoice.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the invoice.
     *
     * @param timestamp The timestamp of the invoice.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the payment method used.
     *
     * @return The payment method used.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the payment method used.
     *
     * @param method The payment method used.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Retrieves the status of the invoice.
     *
     * @return The status of the invoice.
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
}
