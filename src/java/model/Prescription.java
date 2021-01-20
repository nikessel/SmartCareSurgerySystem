package model;

import java.util.Date;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Prescription {

    private Patient patient;
    private Doctor doctor;
    private String medication;
    private Date expirationDate;
    private int prescriptionID;

    public Prescription() {

    }

    public Prescription(Patient patient, Doctor doctor, String medication, Date expirationDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.medication = medication;
        this.expirationDate = expirationDate;
        this.prescriptionID = -1;
    }

    public Prescription(Patient patient, Doctor doctor, String medication, Date expirationDate, int prescriptionID) {
        boolean isDatabase = false;

        try {
            if (Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()) == Database.class) {
                isDatabase = true;
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

        if (isDatabase) {
            this.prescriptionID = prescriptionID;
            this.patient = patient;
            this.doctor = doctor;
            this.medication = medication;
            this.expirationDate = expirationDate;
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

    public int getID() {
        return prescriptionID;
    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Prescription{" + "patient=" + patient + ", doctor=" + doctor + ", medication=" + medication + ", expirationDate=" + expirationDate + ", prescriptionID=" + prescriptionID + '}';
    }

}
