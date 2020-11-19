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
public class Admin extends Employee {
    
    int adminID;

    public Admin(String username, String password, String firstName, String surName, boolean isFullTime, int adminID) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.isFullTime = isFullTime;
        this.adminID = adminID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    @Override
    public String toString() {
        return "Admin{" + "username=" + username + ", password=" + password + ", firstName=" + firstName + ", surName=" + surName + ", isFullTime=" + isFullTime + ", adminID=" + adminID + '}';
    }



}
