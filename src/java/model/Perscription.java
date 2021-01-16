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
public class Perscription extends DatabaseObject {

    private Patient patient;
    private Doctor doctor;
    private String medication;
    private int perscriptionID;

    public Perscription() {
        
    }
    
    public Perscription(Patient patient, Doctor doctor, String medication) {
        this.patient = patient;
        this.doctor = doctor;
        this.medication = medication;
        this.perscriptionID = -1;
    }

    public Perscription(Patient patient, Doctor doctor, String medication, int perscriptionID) {
        boolean isDatabase = false;

        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()) == Database.class) {
                isDatabase = true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        if (isDatabase) {
            this.perscriptionID = perscriptionID;
            this.patient = patient;
            this.doctor = doctor;
            this.medication = medication;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }

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

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public int getPerscriptionID() {
        return perscriptionID;
    }

    public void setPerscriptionID(int perscriptionID) {
        this.perscriptionID = perscriptionID;
    }

    @Override
    public String toString() {
        return "Perscription{" + "patient=" + patient + ", doctor=" + doctor + ", medication=" + medication + ", perscriptionID=" + perscriptionID + '}';
    }
    
    

}
