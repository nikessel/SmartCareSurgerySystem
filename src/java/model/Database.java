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
public class Database {

    private Connection connection;
    private Statement statement;
    private boolean displayErrors;
    private String DATABASESTRING = "jdbc:mysql://127.0.0.1:3306";
    private String USERNAME = "user";
    private String PASSWORD = "password";
    private String DATABASENAME = "sql_smart_care_surgery_database";

    // Singleton configuration
    private static Database database = new Database();

    public static Database getDatabase() {
        return database;
    }

    public void setDisplayErrors(boolean displayErrors) {
        this.displayErrors = displayErrors;
    }

    // This method connects to the database
    // IP, username and password are hardcoded
    public void connect() {
        try {
            connection = DriverManager.getConnection(DATABASESTRING, USERNAME, PASSWORD);
            statement = connection.createStatement();
            database.executeQuery("USE " + DATABASENAME);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Getter for statement required for writing back to the Database directly
    // from other classes
    public Statement getStatement() {
        return statement;
    }

    // Executes read operation on the database, takes a mySQL command as input
    public ResultSet executeQuery(String query) {
        if (!"".equals(query)) {
            try {
                ResultSet rs = statement.executeQuery(query);
                return rs;
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("A query must be passed to function database.executeQuery()");
        }
        return null;
    }

    private Address convertStringToAddress(String thisAddressString) {

        String[] stringArray = thisAddressString.split("-");

        String addressLine1, addressLine2, postcode, county, town, telephoneNumber;

        addressLine1 = stringArray[0];
        addressLine2 = stringArray[1];
        postcode = stringArray[2];
        county = stringArray[3];
        town = stringArray[4];
        telephoneNumber = stringArray[5];

        if (addressLine1.isEmpty()) {
            if (displayErrors) {
                System.err.println("Database error getting address");
            }
        }
        // Return the object
        return new Address(addressLine1, addressLine2, postcode, county, town, telephoneNumber);
    }

    private String convertAddressToString(Address thisAddress) {

        return thisAddress.getAddressLine1() + "-"
                + thisAddress.getAddressLine2() + "-"
                + thisAddress.getPostcode() + "-"
                + thisAddress.getCounty() + "-"
                + thisAddress.getTown() + "-"
                + thisAddress.getTelephoneNumber();

    }

    /*
    The object initialisation methods below query the database for object attributes.
    The method will loop through every object in the repective database tables 
    and construct objects of the respective classes.
    All objects will be added to the lists declared above, to be accessed from
    all other classes.
     */
    // System user inititialisations
    public Admin getAdmin(int adminID) {

        try {

            ResultSet rs = database.executeQuery("SELECT * FROM admins WHERE admin_id=" + adminID + ";");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("admin_id");

                // Return the object
                return new Admin(username, password, firstName, surName, isFullTime, id);
            }
        } catch (SQLException e) {

        }

        if (displayErrors) {
            System.err.println("Database error getting admin");
        }

        return new Admin("", "", "", "", false, -1);
    }

    public Doctor getDoctor(int doctorID) {

        try {
            database.executeQuery("USE " + DATABASENAME);
            ResultSet rs = Database.database.executeQuery("SELECT * FROM doctors WHERE doctor_id=" + doctorID + ";");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("doctor_id");

                // Return the object
                return new Doctor(username, password, firstName, surName, isFullTime, id);
            }
        } catch (SQLException e) {

        }

        if (displayErrors) {
            System.err.println("Database error getting doctor");
        }

        return new Doctor("", "", "", "", false, -1);
    }

    public Nurse getNurse(int nurseID) {

        try {
            database.executeQuery("USE " + DATABASENAME);
            ResultSet rs = Database.database.executeQuery("SELECT * FROM nurses WHERE nurse_id=" + nurseID + ";");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("nurse_id");

                // Return the object
                return new Nurse(username, password, firstName, surName, isFullTime, id);
            }
        } catch (SQLException e) {

        }

        if (displayErrors) {
            System.err.println("Database error getting nurse");
        }

        return new Nurse("", "", "", "", false, -1);
    }

    public Patient getPatient(int patientID) {

        try {
            ResultSet rs = database.executeQuery("SELECT * FROM patients WHERE patient_id=" + patientID + ";");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                int id = rs.getInt("patient_id");
                Address address = convertStringToAddress(rs.getString("address"));
                Date dateOfBirth = rs.getDate("date_of_birth");

                // Return the object
                return new Patient(username, password, firstName, surName, patientID, dateOfBirth, address);
            }
        } catch (SQLException e) {

        }

        if (displayErrors) {
            System.err.println("Database error getting patient");
        }

        return new Patient("", "", "", "", -1, new java.util.Date(0000 - 00 - 00), new Address("", "", "", "", "", ""));
    }

    /*
    // System object initialisations
    public void initialiseBookings() {

        BOOKINGLIST.clear();
        try {
            database.executeQuery("USE sql_health_care_pro_database");
            ResultSet rs = Database.database.executeQuery("SELECT * FROM appointments;");

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
    The method saves any valid SmartCareSurgery database object in the database.
    A mySQL INSERT command will be generated based on the 
    attributes in the object.
    A ON DUBLICATE restriction will also be added, so the database will update
    exiting entries, instead of adding dublicate rows.
     */
    public void writeObjectToDatabase(Object object) {

        String queryString;
        String table = "";
        String fieldString = "";
        String valueString = "";
        String updateString = "";

        if (object instanceof Admin) {
            Admin parsed = (Admin) object;

            table = "admins";
            fieldString = "username, password, first_name, sur_name, admin_id, is_full_time";

            valueString ="'" + parsed.getUsername()
                    + "', '" + parsed.getPassword()
                    + "', '" + parsed.getFirstName()
                    + "', '" + parsed.getSurName()
                    + "', " + parsed.getAdminID()
                    + ", " + parsed.isIsFullTime();

            updateString = "username='" + parsed.getFirstName() + "', "
                    + "password='" + parsed.getSurName() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "patient_id=" + parsed.getPatientID() + ", "
                    + "address='" + address.toString() + "', "
                    + "date_of_birth='" + parsed.getDateOfBirth() + "'";
        } else if (object instanceof Patient) {
            Patient parsed = (Patient) object;

            Address address = convertStringToAddress("");

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
    public void deleteObjectFromDatabase(Object object) {

        database.connect();
        database.executeQuery("USE " + DATABASENAME);

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
