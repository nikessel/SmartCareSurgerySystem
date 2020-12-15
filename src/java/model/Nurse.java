/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Nurse extends Employee {

    private int nurseID;

    public Nurse() {

    }

    public Nurse(String username, String firstName, String surName, boolean isFullTime) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.fullTime = isFullTime;
        this.nurseID = -1;
    }

    protected Nurse(String username, String firstName, String surName, boolean isFullTime, int nurseID) {
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
            this.fullTime = isFullTime;
            this.nurseID = nurseID;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }

    }

    public int getNurseID() {
        return nurseID;
    }

    @Override
    public String toString() {
        return "Nurse{" + "username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", isFullTime=" + fullTime + ", nurseID=" + nurseID + '}';
    }

}
