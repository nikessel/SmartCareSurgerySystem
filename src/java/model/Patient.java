/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author niklas
 */
public class Patient extends User {

    int patientID;

    public Patient(String username, String password, String firstName, String surName, int patientID) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.patientID = patientID;
    }

    
    
}
