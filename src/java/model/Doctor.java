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
public class Doctor extends Employee {

    private int doctorID;

    public Doctor() {

    }

    public Doctor(String username, String firstName, String surName, Date dateOfBirth, Address address, boolean isFullTime) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.fullTime = isFullTime;
        this.doctorID = -1;
    }

    protected Doctor(String username, String firstName, String surName, Date dateOfBirth, Address address, boolean isFullTime, int doctorID) {
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
            this.dateOfBirth = dateOfBirth;
            this.address = address;
            this.fullTime = isFullTime;
            this.doctorID = doctorID;

        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }

    }

    public int getDoctorID() {
        return doctorID;
    }

    public int getID() {
        return doctorID;
    }

    @Override
    public String toString() {
        return "Doctor{" + "doctorID=" + doctorID + ", username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", dateOfBirth=" + dateOfBirth + ", address=" + address + "fullTime=" + fullTime + '}';
    }

}
