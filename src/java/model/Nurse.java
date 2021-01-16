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
public class Nurse extends Employee {

    private int nurseID;

    public Nurse() {

    }

    public Nurse(String username, String firstName, String surName, Date dateOfBirth, Address address, boolean isFullTime) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.fullTime = isFullTime;
        this.nurseID = -1;
    }

    protected Nurse(String username, String firstName, String surName, Date dateOfBirth, Address address, boolean isFullTime, int nurseID) {
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
            this.nurseID = nurseID;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }

    }

    public int getNurseID() {
        return nurseID;
    }

    public int getID() {
        return nurseID;
    }

    @Override
    public String toString() {
        return "Nurse{" + "nurseID=" + nurseID + ", username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", dateOfBirth=" + dateOfBirth + ", address=" + address + "fullTime=" + fullTime + '}';
    }

}
