package model;

import java.sql.Timestamp;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Surgery {
    private Patient patient;
    private Doctor doctor;
    private Timestamp surgeryTime;
    private int surgeryID;
    private int duration;

    public Surgery() {

    }

    protected Surgery(Patient patient, Doctor doctor, Timestamp surgeryTime, int duration, int surgeryID) {
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
            this.duration = duration;
            this.surgeryID = surgeryID;
        } else {
            System.out.println("Constructor with ID can only be called by the Database class");
        }
        
        
    }

    public Surgery(Patient patient, Doctor doctor, Timestamp surgeryTime, int duration) {
        this.patient = patient;
        this.doctor = doctor;
        this.surgeryTime = surgeryTime;
        this.duration = duration;
        this.surgeryID = -1;
                
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public int getID() {
        return surgeryID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        return "Surgery{" + "patient=" + patient + ", doctor=" + doctor + ", surgeryTime=" + surgeryTime + ", duration=" + duration + ", surgeryID=" + surgeryID + '}';
    }

    
    
}
