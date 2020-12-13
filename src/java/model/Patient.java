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
public class Patient extends User {

    private int patientID;
    private Date dateOfBirth;
    private Address address;
    private boolean insured;

    public Patient() {

    }

    public Patient(String username, String firstName,
            String surName, Date dateOfBirth, Address address, boolean insured) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.patientID = -1;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.insured = insured;
    }

    protected Patient(String username, String firstName,
            String surName, int patientID, Date dateOfBirth, Address address, boolean insured) {

        boolean isDatabase = false;

        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()) == Database.class) {
                isDatabase = true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        if (isDatabase) {
            this.username = username;
            this.firstName = firstName;
            this.surName = surName;
            this.patientID = patientID;
            this.dateOfBirth = dateOfBirth;
            this.address = address;
            this.insured = insured;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }

    }

    public int getPatientID() {
        return patientID;
    }

    public String getDateOfBirth() {
        return String.valueOf(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    @Override
    public String toString() {
        return "Patient{" + "username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", patientID=" + patientID + ", dateOfBirth=" + dateOfBirth + ", address=" + address + '}';
    }

}
