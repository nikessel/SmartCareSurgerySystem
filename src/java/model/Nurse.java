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

    public Nurse(String username, String firstName, String surName, boolean isFullTime, int nurseID) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.fullTime = isFullTime;
        this.nurseID = nurseID;
    }

    public int getNurseID() {
        return nurseID;
    }

    @Override
    public String toString() {
        return "Nurse{" + "username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", isFullTime=" + fullTime + ", nurseID=" + nurseID + '}';
    }

}
