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
public class Doctor extends Employee {

    private int doctorID;

    public Doctor(String username, String password, String firstName, String surName, boolean isFullTime, int doctorID) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.isFullTime = isFullTime;
        this.doctorID = doctorID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
    
    

    @Override
    public String toString() {
        return "Doctor{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", surName=" + surName + ", isFullTime=" + isFullTime + ", doctorID=" + doctorID + '}';
    }
    

}
