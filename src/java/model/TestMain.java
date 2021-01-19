/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.DateFormatter;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class TestMain {
    
    public static void main(String[] args) {
        
        Database database = new Database();
        
        database.connect();
        
        
               System.out.println(database.getPrice("surgery"));
        database.setPrice("surgery", 55.55);
        System.out.println(database.getPrice("surgery"));
        
        System.exit(0);
        
        Date fromDate = Date.valueOf("2020-12-01");
        Date toDate = Date.valueOf("2020-12-30");
        
        ArrayList<Invoice> invoices = database.getAllInvoicesFromTo(fromDate, toDate);
        
        for (Invoice inv : invoices) {
            System.out.println(inv);
        }
        
        System.exit(0);
        
        ArrayList<Prescription> arr = database.getAllPrescriptionsWhereIDIs(40000);
        
        for (Prescription pre : arr) {
            System.out.println(pre);
        }
        
        System.out.println(database.getPrescription(80001));
        
        database.setPending(80001);
        
        System.exit(0);
        Patient patient = database.getPatient(40014);
        Doctor doctor = database.getDoctor(20001);
        
        Nurse nurse = database.getNurse(30000);
        
        Consultation consultation = database.getConsultation(50077);
        double price = (database.getPrice("consultation") / 60) * consultation.getDuration();
        
        Prescription perscript = new Prescription(patient, doctor, "2132131321", Date.valueOf("2021-02-02"));
        
        database.addObjectToDatabase(perscript);
        
        database.printDatabaseTable("prescriptions");
        
        database.approve(90001);
        
        database.printDatabaseTable("prescriptions");
        
        database.deleteObjectFromDatabase(database.getPrescription(80000));
        
        ArrayList<Integer> gg = database.getPending("surgeries");
        
        for (int i : gg) {
            System.out.println(database.getSurgery(i));
        }
        
        database.printDatabaseTable("invoices");
        
        Invoice invoice = new Invoice(patient, price,
                Date.valueOf(DateFormatter.formatDate(java.util.Date.from(Instant.now()), "yyyy-MM-dd")),
                false, consultation.getPatient().isInsured());
        
        database.addObjectToDatabase(invoice);
        
        database.printDatabaseTable("invoices");
        database.printDatabaseTable("surgeries");
        System.out.println(database.getSurgery(70075));
        
        //ArrayList<Invoice> invoices = database.getAllInvoicesWhereIDIs(40000);
        
        for (Invoice i : invoices) {
            System.out.println(i);
        }
        Timestamp timestamp = new Timestamp(2021, 11, 11, 11, 11, 0, 0);
        
        int selectedConsultatantID = 20001;
        boolean bookConsultation = false;
        
        if (database.isDoctor(selectedConsultatantID)) {
            doctor = database.getDoctor(selectedConsultatantID);
            
            if (bookConsultation) {
                nurse = database.getNurse(30000);
            }
        } else {
            doctor = database.getDoctor(20000);
            nurse = database.getNurse(selectedConsultatantID);
        }
        
        if (bookConsultation) {
            
            consultation = new Consultation(patient, doctor, nurse, timestamp, "dd", 10);
            database.addObjectToDatabase(consultation);
        } else {
            Surgery surgery = new Surgery(patient, doctor, timestamp, 10);
            database.addObjectToDatabase(surgery);
            
        }
        
        database.printDatabaseTable("surgeries");
        database.printDatabaseTable("consultations");
        
        ArrayList<Integer> pendingConsultationIDs = database.getPending("consultations");
        ArrayList<Consultation> pendingConsultations = new ArrayList<Consultation>();
        
        try {
            
            for (int id : pendingConsultationIDs) {
                pendingConsultations.add(database.getConsultation(id));
            }
            
        } catch (Exception ex) {
            
        }

        //     Patient patient = database.getPatient(40012);
        //   Doctor doctor = database.getDoctor(20001);
        database.addObjectToDatabase(doctor);
        
        System.out.println(database.getPrice("consultation"));
        
        database.setPrice("consultation", 555);
        
        System.out.println(database.getPrice("consultation"));
        
        database.printDatabaseTable("all");
        
        database.addObjectToDatabase(perscript);
        
        database.printDatabaseTable("prescriptions");
        
        database.approve(80001);
        
        database.printDatabaseTable("prescriptions");
        
        database.deleteObjectFromDatabase(database.getPrescription(80001));
        
    }
}
