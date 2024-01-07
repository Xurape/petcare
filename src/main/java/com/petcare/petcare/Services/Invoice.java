package com.petcare.petcare.Services;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {
    private String client, service, date, value;
    private String location, company;
    private InvoiceStatus status = InvoiceStatus.PENDING;
    private String timestamp;

    public Invoice(String client, String service, String location, String company, String value) {
        this.client = client;
        this.service = service;
        this.location = location;
        this.company = company;
        Date d = new Date();
        this.date = date;
        this.value = value;

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
