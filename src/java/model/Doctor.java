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

    public Doctor() {
        
    }
    
    public Doctor(String username,String firstName, String surName, boolean isFullTime) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.fullTime = isFullTime;
        this.doctorID = -1;
    }

    public Doctor(String username, String firstName, String surName, boolean isFullTime, int doctorID) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.fullTime = isFullTime;
        this.doctorID = doctorID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    @Override
    public String toString() {
        return "Doctor{" + "username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", isFullTime=" + fullTime + ", doctorID=" + doctorID + '}';
    }

}
