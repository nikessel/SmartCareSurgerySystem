/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.cj.x.protobuf.MysqlxExpect;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class TestMain {

    public static void main(String[] args) {

        Database database = new Database();

        database.connect();

        Doctor doctor = database.getDoctor(20001);

        database.addObjectToDatabase(doctor);

        database.printDatabaseTable("doctors");

        Nurse nurse = database.getNurse(30001);
        
        Patient patient = database.getPatient(40001);

        database.addObjectToDatabase(nurse);
        
        Consultation consultation = new Consultation(patient, doctor, nurse, new Timestamp(1992, 2, 2, 2, 2));

        database.addObjectToDatabase(consultation);
        
        ArrayList<Integer> arr = database.getPendingConsultations();

        for (int i : arr) {
            System.out.println(i);
        }

    }

}
