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
public class Patient extends User {

    private int patientID;
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

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientID=" + patientID + ", username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", dateOfBirth=" + dateOfBirth + ", address=" + address + ", insured=" + insured + '}';
    }

}
