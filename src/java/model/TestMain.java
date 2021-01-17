/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
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

        Patient patient = database.getPatient(40014);
        Doctor doctor = database.getDoctor(20001);

        Nurse nurse = database.getNurse(30000);

        Consultation consultation = new Consultation(patient, doctor, nurse, new Timestamp(2021, 10, 10, 2, 2), "2344", 0);

        database.addObjectToDatabase(consultation);

        for (int i : database.getPendingConsultations()) {
            System.out.println(database.getConsultation(i));
        }
        System.exit(0);

        ArrayList<Integer> pendingConsultationIDs = database.getPendingConsultations();
        ArrayList<Consultation> pendingConsultations = new ArrayList<Consultation>();

        try {

            for (int id : pendingConsultationIDs) {
                pendingConsultations.add(database.getConsultation(id));
            }

        } catch (Exception ex) {

        }

        System.out.println(pendingConsultations.get(0));

        System.exit(0);
   //     Patient patient = database.getPatient(40012);
     //   Doctor doctor = database.getDoctor(20001);

        database.addObjectToDatabase(doctor);

        System.out.println(database.getPrice("consultation"));

        database.setPrice("consultation", 555);

        System.out.println(database.getPrice("consultation"));

        database.printDatabaseTable("all");

        Perscription perscript = new Perscription(patient, doctor, "2132131321");

        database.addObjectToDatabase(perscript);

        database.printDatabaseTable("perscriptions");

        database.approvePerscription(90001);

        database.printDatabaseTable("perscriptions");

        database.deleteObjectFromDatabase(database.getPerscription(90000));

    }
}
