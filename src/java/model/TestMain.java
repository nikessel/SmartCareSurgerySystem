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
public class TestMain {

    public static void print_all_objects() {
        Database database = Database.getDatabase();
        database.setDisplayErrors(false);

        int i = 1;

        Admin thisAdmin = database.getAdmin(i);

        while (thisAdmin.getAdminID() != -1) {
            System.out.println(thisAdmin);

            i++;
            thisAdmin = database.getAdmin(i);
        }
        
        i = 1;

        Doctor thisDoctor = database.getDoctor(i);

        while (thisDoctor.getDoctorID() != -1) {
            System.out.println(thisDoctor);

            i++;
            thisDoctor = database.getDoctor(i);
        }
        
        i = 1;

        Nurse thisNurse = database.getNurse(i);

        while (thisNurse.getNurseID() != -1) {
            System.out.println(thisNurse);

            i++;
            thisNurse = database.getNurse(i);
        }
        
        i = 1;

        Patient thisPatient = database.getPatient(i);

        while (thisPatient.getPatientID() != -1) {
            System.out.println(thisPatient);

            i++;
            thisPatient = database.getPatient(i);
        }
        
        database.setDisplayErrors(true);

    }

    public static void main(String[] args) {
        Database.getDatabase().connect();

        print_all_objects();
    }

}
