/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author niklas
 */
public class Invoice {

    Consultation consultation;
    double price;
    Date dateOfInvoice;
    boolean paid;
    boolean insured;
    int invoiceID;

    public Invoice(Consultation consultation, double price, Date dateOfInvoice, boolean paid, boolean insured, int invoiceID) {
        this.consultation = consultation;
        this.price = price;
        this.dateOfInvoice = dateOfInvoice;
        this.paid = paid;
        this.insured = insured;
        this.invoiceID = invoiceID;
    }

    public Invoice(Consultation consultation, double price, Date dateOfInvoice, boolean paid, boolean insured) {
        this.consultation = consultation;
        this.price = price;
        this.dateOfInvoice = dateOfInvoice;
        this.paid = paid;
        this.insured = insured;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateOfInvoice() {
        return dateOfInvoice;
    }

    public void setDateOfInvoice(Date dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    @Override
    public String toString() {
        return "Invoice{" + "invoiceID=" + invoiceID + ", consultationID=" + consultation.getConsulationID() + ", price=" + price + ", dateOfInvoice=" + dateOfInvoice + ", paid=" + paid + ", insured=" + insured + '}';
    }

}
