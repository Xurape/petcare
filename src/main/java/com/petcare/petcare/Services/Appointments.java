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

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
