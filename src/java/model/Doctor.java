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
public class Doctor extends User{

    int doctorID;

    public Doctor(String username, String password, String firstName, String surName, int doctorID) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.doctorID = doctorID;
    }

    @Override
    public String toString() {
        return "Doctor{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", surName=" + surName + ", doctorID=" + doctorID + '}';
    }
    
    
}
