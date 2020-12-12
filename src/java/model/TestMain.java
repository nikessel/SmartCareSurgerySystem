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

        database.printDatabaseTable("consultations");

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

        if (10000 <= currentUserID && currentUserID <= 19999) {
            currentUser = database.getAdmin(currentUserID);
            System.out.println("Is an admin");
        } else if (20000 <= currentUserID && currentUserID <= 29999) {
            currentUser = database.getDoctor(currentUserID);
            System.out.println("Is a doctor");
        } else {
            currentUser = new Admin("", "", "", "", true);
            System.out.println("Error: couldn't get user!");
        }

        System.out.println(currentUser);

        Admin admin = new Admin("AAAAA", "dsd", "dsd", "dsds", true);
        database.writeObjectToDatabase(admin);

        Doctor doctor = new Doctor("dds", "dsd", "dsd", "dsds", true);
        database.writeObjectToDatabase(doctor);

        Nurse nurse = new Nurse("dds", "dsd", "dsd", "dsds", true);
        database.writeObjectToDatabase(nurse);

        Patient patient = new Patient("fsdfs", "dsadsa", "dasdas", "sad", java.sql.Date.valueOf("2000-12-12"), new Address("sdaa", "sad", "sd", "dsaf", "sda", "asd"));
        database.writeObjectToDatabase(patient);

        currentUserID = database.getUserID("nurse", "nurse");

        System.out.println(currentUserID);

        //database.deleteObjectFromDatabase(database.getNurse(currentUserID));
        currentUserID = database.getUserID("nurse", "nurse");

        System.out.println(database.getConsultation(50002));

        database.printDatabaseTable("all");

    }

}
