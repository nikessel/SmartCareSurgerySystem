/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18034466
 *
 */
/*

The Database class contains lists of all the objects in the system.

When the system starts all objects in all classes will be initialised with
data from the database.

The Database class also contains methods for writing objects back to the database
for permanent storage.

 */
class Database {

    private static Connection connection;
    private static Statement statement;
    private static final String DATABASESTRING = "jdbc:mysql://127.0.0.1:3306";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private static final String DATABASENAME = "sql_smart_care_surgery_database";

    // System user lists
    private static final ArrayList PATIENTLIST = new ArrayList<Patient>();
    private static final ArrayList ADMINLIST = new ArrayList<Admin>();
    private static final ArrayList DOCTORLIST = new ArrayList<Doctor>();
    private static final ArrayList NURSELIST = new ArrayList<Nurse>();

    // System object lists
    // No list for the Address class as it is stored as an attribute
    private static final ArrayList BOOKINGLIST = new ArrayList<Booking>();

    // Getters for all object lists in the system
    public static ArrayList<Patient> getPatientList() {
        return PATIENTLIST;
    }

    public static ArrayList getADMINLIST() {
        return ADMINLIST;
    }

    public static ArrayList getDOCTORLIST() {
        return DOCTORLIST;
    }

    public static ArrayList getNURSELIST() {
        return NURSELIST;
    }

    public static ArrayList getBOOKINGLIST() {
        return BOOKINGLIST;
    }

    // This method connects to the database
    // IP, username and password is hardcoded
    public static void Connect() {
        try {
            connection = DriverManager.getConnection(DATABASESTRING, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Getter for statement required for writing back to the Database directly
    // from other classes
    public static Statement getStatement() {
        return statement;
    }

    // Executes read operation on the database, takes a mySQL command as input
    public static ResultSet executeQuery(String query) {
        if (!"".equals(query)) {
            try {
                ResultSet rs = statement.executeQuery(query);
                return rs;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("A query must be passed to function executeQuery()");
        }
        return null;
    }

    /*
    The object initialisation methods below query the database for object attributes.
    The method will loop through every object in the repective database tables 
    and construct objects of the respective classes.
    All objects will be added to the lists declared above, to be accessed from
    all other classes.
     */
    // System user inititialisations
    private static Address getAddress(int patientID) {

        try {
            Connect();
            executeQuery("USE " + DATABASENAME);
            ResultSet rs = Database.executeQuery("SELECT * FROM patients WHERE patient_id = " + patientID);

            // iterate through the sql resultset
            while (rs.next()) {
                String address_string = rs.getString("address");

                String[] string_array = address_string.split("-");

                String addressLine1, addressLine2, postcode, county, town, telephoneNumber;

                addressLine1 = string_array[0];
                addressLine2 = string_array[1];
                postcode = string_array[2];
                county = string_array[3];
                town = string_array[4];
                telephoneNumber = string_array[5];

                return new Address(addressLine1, addressLine2, postcode, county, town, telephoneNumber);
            }
        } catch (Exception e) {

            System.err.println("Database error receiving patient address");
        }

        return new Address("", "", "", "", "", "");
    }

    public static void initialisePatients() {

        PATIENTLIST.clear();

        try {
            executeQuery("USE " + DATABASENAME);
            ResultSet rs = Database.executeQuery("SELECT * FROM patients;");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                int patientID = rs.getInt("patient_id");
                Address address = getAddress(patientID);
                Date dateOfBirth = rs.getDate("date_of_birth");

                // print the results
                PATIENTLIST.add(new Patient(username, password, firstName, surName, patientID, dateOfBirth, address));
            }
        } catch (Exception e) {

            System.err.println("Database error getting patient");
        }
    }

    /*
    // System object initialisations
    public static void initialiseBookings() {

        BOOKINGLIST.clear();
        try {
            executeQuery("USE sql_health_care_pro_database");
            ResultSet rs = Database.executeQuery("SELECT * FROM appointments;");

            // iterate through the java resultset
            while (rs.next()) {
                int appointmentID = rs.getInt("appointment_id");
                int patientID = rs.getInt("patient_id");
                int doctorID = rs.getInt("doctor_id");
                Date bookingDate = rs.getDate("booking_date");

                BOOKINGLIST.add(new Booking(patientID, patient, doctor, appointmentDate));
            }
        } catch (Exception e) {

            System.err.println("Database error getting appointments");
        }
    }
     */

 /*
    The method saves valid SmartCareSurgery in the database.
    The input will thereafter be parsed as it the actual object.
    At this point a mySQL INSERT command will be generated based on the 
    attributes in the object.
    A ON DUBLICATE restriction will also be added, so the database will update
    exiting entries, instead of adding dublicate rows.
     */
    public static void writeObjectToDatabase(Object object) {

        Connect();
        executeQuery("USE " + DATABASENAME);

        String queryString;
        String table = "";
        String fieldString = "";
        String valueString = "";
        String updateString = "";

        if (object instanceof Patient) {
            Patient parsed = (Patient) object;

            writeObjectToDatabase(parsed.getAddress());

            Address address = getAddress(parsed.getPatientID());

            table = "patients";
            fieldString = "username, password, first_name, sur_name, patient_id, address, date_of_birth";

            valueString = parsed.getPatientID()
                    + ", '" + parsed.getUsername()
                    + "', '" + parsed.getPassword()
                    + "', '" + parsed.getFirstName()
                    + "', '" + parsed.getSurName()
                    + "', " + parsed.getPatientID()
                    + ", '" + parsed.getDateOfBirth() + "'";

            updateString = "username='" + parsed.getFirstName() + "', "
                    + "password='" + parsed.getSurName() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "patient_id=" + parsed.getPatientID() + ", "
                    + "address='" + address.toString() + "', "
                    + "date_of_birth='" + parsed.getDateOfBirth() + "'";
        } else {
            System.out.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        queryString = "INSERT INTO " + table + " (" + fieldString + ") VALUES(" + valueString + ") ON DUPLICATE KEY UPDATE " + updateString;

        System.out.println(queryString);
        try {
            statement.executeUpdate(queryString);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("Error writing object to database");
        }
    }

    /*
    This method uses the same concept as above, but will delete an object from
    the database
    
     */
    public static void deleteObjectFromDatabase(Object object) {

        Connect();
        executeQuery("USE " + DATABASENAME);

        String queryString;
        String table = "";
        String idString = "";
        String idValue = "";

        if (object instanceof Patient) {
            Patient parsed = (Patient) object;

            table = "patients";
            idString = "patient_id";
            idValue = String.valueOf(parsed.getPatientID());

        } else {
            System.out.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        queryString = "DELETE FROM " + table + " WHERE " + idString + " = " + idValue + ";";

        System.out.println(queryString);
        try {
            statement.executeUpdate(queryString);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("Error writing object to database");
        }
    }

}
