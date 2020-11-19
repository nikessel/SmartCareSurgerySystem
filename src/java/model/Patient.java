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
public class Patient extends User {

    private int patientID;
    java.sql.Date dateOfBirth;
    Address address;

    public Patient(String username, String password, String firstName,
            String surName, java.sql.Date dateOfBirth, Address address) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.patientID = -1;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Patient(String username, String password, String firstName,
            String surName, int patientID, java.sql.Date dateOfBirth, Address address) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.patientID = patientID;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

    }

    public int getPatientID() {
        return patientID;
    }

    public String getDateOfBirth() {
        return String.valueOf(dateOfBirth);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = Date.valueOf(dateOfBirth);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", surName=" + surName + ", patientID=" + patientID + ", dateOfBirth=" + dateOfBirth + ", address=" + address + '}';
    }

}
