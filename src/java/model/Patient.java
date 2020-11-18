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

    int patientID;
    Date dateOfBirth;
    Address address;

    public Patient(String username, String password, String firstName, 
            String surName, int patientID, Date dateOfBirth, Address address) {
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

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    
}
