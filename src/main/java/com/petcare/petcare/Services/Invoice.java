package com.petcare.petcare.Services;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
    private Appointments appointment;
    private String client, service, date, value;
    private String location, company, method;
    private InvoiceStatus status = InvoiceStatus.PENDING;
    private String timestamp;

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

    public Appointments getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointments appointment) {
        this.appointment = appointment;
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

    public String getLocation() {
        return location;
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

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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
}
