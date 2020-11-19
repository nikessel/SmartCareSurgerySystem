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

    public Nurse(String username, String password, String firstName, String surName, boolean isFullTime, int nurseID) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.isFullTime = isFullTime;
        this.nurseID = nurseID;
    }

    public int getNurseID() {
        return nurseID;
    }

    public void setNurseID(int nurseID) {
        this.nurseID = nurseID;
    }

    @Override
    public String toString() {
        return "Nurse{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", surName=" + surName + ", isFullTime=" + isFullTime + ", nurseID=" + nurseID + '}';
    }

    
    
}
