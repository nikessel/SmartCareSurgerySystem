/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author niklas
 */
public class Surgery extends DatabaseObject {
    private Patient patient;
    private Doctor doctor;
    private Timestamp surgeryTime;
    private int surgeryID;

    public Surgery() {

    }

    protected Surgery(Patient patient, Doctor doctor, Timestamp surgeryTime, int surgeryID) {
        boolean isDatabase = false;

        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()) == Database.class) {
                isDatabase = true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        if (isDatabase) {
            this.patient = patient;
            this.doctor = doctor;
            this.surgeryTime = surgeryTime;
            this.surgeryID = surgeryID;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }
        
        
    }

    public Surgery(Patient patient, Doctor doctor, Timestamp surgeryTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.surgeryTime = surgeryTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Timestamp getSurgeryTime() {
        return surgeryTime;
    }

    public void setSurgeryTime(Timestamp surgeryTime) {
        this.surgeryTime = surgeryTime;
    }

    public int getSurgeryID() {
        return surgeryID;
    }

    @Override
    public String toString() {
        return "Surgery{" + "patient=" + patient + ", doctor=" + doctor + ", surgeryTime=" + surgeryTime + ", surgeryID=" + surgeryID + '}';
    }

    
    
}
