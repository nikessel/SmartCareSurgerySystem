/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Invoice extends DatabaseObject {

    private Consultation consultation;
    private double price;
    private Date dateOfInvoice;
    private boolean paid;
    private boolean insured;
    private int invoiceID;

    public Invoice() {

    }

    protected Invoice(Consultation consultation, double price, Date dateOfInvoice, boolean paid, boolean insured, int invoiceID) {
        boolean isDatabase = false;

        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()) == Database.class) {
                isDatabase = true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        if (isDatabase) {
            this.consultation = consultation;
            this.price = price;
            this.dateOfInvoice = dateOfInvoice;
            this.paid = paid;
            this.insured = insured;
            this.invoiceID = invoiceID;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }
    }

    public Invoice(Consultation consultation, double price, Date dateOfInvoice, boolean paid, boolean insured) {
        this.consultation = consultation;
        this.price = price;
        this.dateOfInvoice = dateOfInvoice;
        this.paid = paid;
        this.insured = insured;
        this.invoiceID = -1;
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
        return "Invoice{" + "invoiceID=" + invoiceID + ", consultationID=" + consultation.getConsultationID() + ", price=" + price + ", dateOfInvoice=" + dateOfInvoice + ", paid=" + paid + ", insured=" + insured + '}';
    }

}
