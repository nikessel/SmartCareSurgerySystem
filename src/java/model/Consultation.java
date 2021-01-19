/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Consultation extends DatabaseObject {

    private Patient patient;
    private Doctor doctor;
    private Nurse nurse;
    private Timestamp consultationTime;
    private int consultationID;
    private String note;
    private int duration;

    public Consultation() {

    }

    protected Consultation(Patient patient, Doctor doctor, Nurse nurse, Timestamp consultationTime, String note, int duration, int consulationID) {
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
            this.nurse = nurse;
            this.consultationTime = consultationTime;
            this.consultationID = consulationID;
            this.note = note;
            this.duration = duration;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }
    }

    public Consultation(Patient patient, Doctor doctor, Nurse nurse, Timestamp consultationTime, String note, int duration) {
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
        this.consultationTime = consultationTime;
        this.note = note;
        this.duration = duration;
        this.consultationID = -1;
    }

    public int getConsultationID() {
        return consultationID;
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

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Timestamp getConsultationTime() {
        return consultationTime;
    }

    public void setConsultationTime(Timestamp consultationTime) {
        this.consultationTime = consultationTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getID() {
        return consultationID;
    }

    @Override
    public String toString() {
        return "Consultation{" + "patient=" + patient + ", doctor=" + doctor + 
                ", nurse=" + nurse + ", consultationTime=" + consultationTime + 
                ", note:" + note + ", duration:" + duration +
                ", consulationID=" + consultationID + '}';
    }


}
