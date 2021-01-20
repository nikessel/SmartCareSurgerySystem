package model;

import java.util.Date;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Admin extends Employee {

    private int adminID;

    public Admin() {

    }

    public Admin(String username, String firstName, String surName, Date dateOfBirth, Address address, boolean isFullTime) {
        this.username = username;
        this.firstName = firstName;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.fullTime = isFullTime;
        this.adminID = -1;
    }

    protected Admin(String username, String firstName, String surName, Date dateOfBirth, Address address, boolean isFullTime, int adminID) {
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
            this.adminID = adminID;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }
    }

    public int getAdminID() {
        return adminID;
    }

    public int getID() {
        return adminID;
    }

    @Override
    public String toString() {
        return "Admin{" + "adminID=" + adminID + ", username=" + username + ", firstName=" + firstName + ", surName=" + surName + ", dateOfBirth=" + dateOfBirth + ", address=" + address + "fullTime=" + fullTime + '}';
    }


}
