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

        database.printDatabaseTable("doctors");

        Nurse nurse = database.getNurse(30001);
        Doctor doctor = database.getDoctor(20001);
        Patient patient = database.getPatient(40001);

        Consultation consultation = new Consultation(patient, doctor, nurse, new Timestamp(1992, 2, 4, 5, 2), "fsafwefwffwfwfwf", 10);

        database.addObjectToDatabase(consultation);



    }
}

  