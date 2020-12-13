/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author niklas
 */
public class TestMain {

    public static void main(String[] args) {

        Database database = new Database();

        database.connect();

        Nurse nurse = new Nurse("dds", "dsd", "dsds", true);
        database.addObjectToDatabase(nurse);
        database.addObjectToDatabase(database.getNurse(30001));
        database.addObjectToDatabase(database.getConsultation(50002));



        //System.out.println(database.getAdmin(10000));
        //System.out.println(database.getConsultation(50000));
        //database.printDatabaseTable("consultations");
        ArrayList<Consultation> cons = database.getAllConsultationsWhereIs("doctor_id", "20001");

        for (Consultation co : cons) {
            System.out.println(co);
        }

        System.out.println(database.getUserID("doctor", "doctor"));
        System.out.println(database.getUserID("admin", "admin"));
        System.out.println(database.getUserID("nurse", "nurse"));
        System.out.println(database.getUserID("patient", "patient"));

        User currentUser;
        int currentUserID = -2;

        ArrayList<Integer> int_arr = new ArrayList();

        int_arr = database.getIDs();

        //currentUserID = database.getUserID("testdoctor", "password");
        System.out.println(currentUserID);

        Admin admin = new Admin("AAAAA", "dsd", "dsds", true);
        database.addObjectToDatabase(admin);

        Doctor doctor = new Doctor("dds", "dsd", "dsds", true);
        database.addObjectToDatabase(doctor);

        //Nurse nurse = new Nurse("dds", "dsd", "dsds", true);
        

        Patient patient = new Patient("fsdfs", "dsadsa", "dasdas", java.sql.Date.valueOf("2000-12-12"), new Address("sdaa", "sad", "sd", "dsaf", "sda", "asd"), false);
        database.addObjectToDatabase(patient);

        currentUserID = database.getUserID("nurse", "nurse");

        System.out.println(currentUserID);

        database.deleteObjectFromDatabase(database.getNurse(currentUserID));
        currentUserID = database.getUserID("nurse", "nurse");

        System.out.println(database.getConsultation(50002));

        database.printDatabaseTable("all");

    }

}
