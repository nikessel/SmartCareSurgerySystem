/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author niklas
 */
public class Booking {
    int bookingID;
    Patient patient;
    Doctor doctor;
    Date bookingDate;

    public Booking(int bookingID, Patient patient, Doctor doctor, Date bookingDate) {
        this.bookingID = bookingID;
        this.patient = patient;
        this.doctor = doctor;
        this.bookingDate = bookingDate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingID=" + bookingID + ", patient=" + patient + ", doctor=" + doctor + ", bookingDate=" + bookingDate + '}';
    }
    
    
    
}
