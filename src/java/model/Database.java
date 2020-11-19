/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18034466
 *
 */
/*

The Database class contains lists of all the objects in the system.

When the system starts all objects in all classes will be initialised with
data from the 

The Database class also contains methods for writing objects back to the database
for permanent storage.

 */
public class Database {

    private static Connection connection;
    private static Statement statement;
    private static boolean displayErrors = true;
    private static final String DATABASESTRING = "jdbc:mysql://127.0.0.1:3306";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private static final String DATABASENAME = "sql_smart_care_surgery_database";
    private static final String[] TABLENAMES = {"admins", "doctors", "nurses", "patients"};

    // This method connects to the database
    // IP, username and password are hardcoded
    public static void connect() {

        try {
            connection = DriverManager.getConnection(DATABASESTRING, USERNAME, PASSWORD);
            statement = connection.createStatement();
            executeQuery("USE " + DATABASENAME + ";");
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
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("A query must be passed to function executeQuery()");
        }
        return null;
    }

    private static Address convertStringToAddress(String thisAddressString) {

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

    private static String convertAddressToString(Address thisAddress) {

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
    // Get specific user
    public static Admin getAdmin(int adminID) {

        try {

            connect();
            executeQuery("USE " + DATABASENAME);
            ResultSet rs = executeQuery("SELECT * FROM admins WHERE admin_id=" + adminID + ";");

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

    public static void printDatabaseTable(String tableToPrint) {
        try {
            if ("all".equals(tableToPrint)) {
                for (String TABLENAMES1 : TABLENAMES) {
                    System.out.println("");
                    System.out.println("---------- " + TABLENAMES1 + " ----------");
                    System.out.println("");
                    printDatabaseTable(TABLENAMES1);
                }
                return;
            }

            String idString = tableToPrint.substring(0, tableToPrint.length() - 1) + "_id";

            String queryString = "SELECT " + idString
                    + " FROM " + tableToPrint + ";";

            connect();
            ResultSet rs = Database.executeQuery(queryString);

            // iterate through the sql resultset
            while (rs.next()) {
                int id = rs.getInt(idString);

                switch (tableToPrint) {
                    case "admins":
                        System.out.println(getAdmin(id));
                        break;
                    case "doctors":
                        System.out.println(getDoctor(id));
                        break;
                    case "nurses":
                        System.out.println(getNurse(id));
                        break;
                    case "patients":
                        System.out.println(getPatient(id));
                        break;

                }

            }
        } catch (SQLException e) {
            if (displayErrors) {
                System.out.println(e);
            }
        }
    }

    public static Doctor getDoctor(int doctorID) {
        connect();
        try {
            executeQuery("USE " + DATABASENAME);
            ResultSet rs = Database.executeQuery("SELECT * FROM doctors WHERE doctor_id=" + doctorID + ";");

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

    public static Nurse getNurse(int nurseID) {
        connect();
        try {
            executeQuery("USE " + DATABASENAME);
            ResultSet rs = Database.executeQuery("SELECT * FROM nurses WHERE nurse_id=" + nurseID + ";");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("nurse_id");

                // Return the object
                return new Nurse(username, password, firstName, surName, isFullTime, nurseID);
            }
        } catch (SQLException e) {

        }

        if (displayErrors) {
            System.err.println("Database error getting nurse");
        }

        return new Nurse("", "", "", "", false, -1);
    }

    public static Patient getPatient(int patientID) {
        connect();
        try {
            ResultSet rs = executeQuery("SELECT * FROM patients WHERE patient_id=" + patientID + ";");

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                int id = rs.getInt("patient_id");
                Address address = convertStringToAddress(rs.getString("address"));
                java.sql.Date dateOfBirth = rs.getDate("date_of_birth");

                // Return the object
                return new Patient(username, password, firstName, surName, patientID, dateOfBirth, address);
            }
        } catch (SQLException e) {

        }

        if (displayErrors) {
            System.err.println("Database error getting patient");
        }

        return new Patient("", "", "", "", -1, new java.sql.Date(0000 - 00 - 00), new Address("", "", "", "", "", ""));
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
    The method saves any valid SmartCareSurgery database object in the 
    A mySQL INSERT command will be generated based on the 
    attributes in the object.
    A ON DUBLICATE restriction will also be added, so the database will update
    exiting entries, instead of adding dublicate rows.
     */
    public static void writeObjectToDatabase(Object object) {

        String queryString;
        String table = "";
        String fieldString = "";
        String valueString = "";
        String updateString = "";
        String idString = "";
        connect();

        if (object instanceof Admin) {
            Admin parsed = (Admin) object;

            table = "admins";
            fieldString = "username, password, first_name, sur_name, is_full_time";

            if (parsed.getAdminID() != -1) {
                idString = String.valueOf(parsed.getAdminID()) + ", ";
                fieldString = "username, password, first_name, sur_name, admin_id, is_full_time";
            }

            valueString = "'" + parsed.getUsername()
                    + "', '" + parsed.getPassword()
                    + "', '" + parsed.getFirstName()
                    + "', '" + parsed.getSurName()
                    + "', " + parsed.isFullTime();

            updateString = "username='" + parsed.getUsername() + "', "
                    + "password='" + parsed.getPassword() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "is_full_time=" + parsed.isFullTime();

        } else if (object instanceof Doctor) {
            Doctor parsed = (Doctor) object;

            table = "doctors";
            fieldString = "username, password, first_name, sur_name, doctor_id, is_full_time";

            if (parsed.getDoctorID()!= -1) {
                idString = String.valueOf(parsed.getDoctorID()) + ", ";
                fieldString = "username, password, first_name, sur_name, admin_id, is_full_time";
            }

            valueString = "'" + parsed.getUsername()
                    + "', '" + parsed.getPassword()
                    + "', '" + parsed.getFirstName()
                    + "', '" + parsed.getSurName()
                    + "', " + parsed.getDoctorID()
                    + ", " + parsed.isFullTime();

            updateString = "username='" + parsed.getUsername() + "', "
                    + "password='" + parsed.getPassword() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "doctor_id=" + parsed.getDoctorID() + ", "
                    + "is_full_time=" + parsed.isFullTime();

        } else if (object instanceof Nurse) {
            Nurse parsed = (Nurse) object;

            table = "nurses";
            fieldString = "username, password, first_name, sur_name, nurse_id, is_full_time";

            valueString = "'" + parsed.getUsername()
                    + "', '" + parsed.getPassword()
                    + "', '" + parsed.getFirstName()
                    + "', '" + parsed.getSurName()
                    + "', " + parsed.getNurseID()
                    + ", " + parsed.isFullTime();

            updateString = "username='" + parsed.getUsername() + "', "
                    + "password='" + parsed.getPassword() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "nurse_id=" + parsed.getNurseID() + ", "
                    + "is_full_time=" + parsed.isFullTime();

        } else if (object instanceof Patient) {
            Patient parsed = (Patient) object;

            String address = convertAddressToString(parsed.getAddress());

            table = "patients";
            fieldString = "username, password, first_name, sur_name, patient_id, address, date_of_birth";

            valueString = "'" + parsed.getUsername()
                    + "', '" + parsed.getPassword()
                    + "', '" + parsed.getFirstName()
                    + "', '" + parsed.getSurName()
                    + "', " + parsed.getPatientID()
                    + ", '" + address
                    + "', '" + parsed.getDateOfBirth() + "'";

            updateString = "username='" + parsed.getFirstName() + "', "
                    + "password='" + parsed.getSurName() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "patient_id=" + parsed.getPatientID() + ", "
                    + "address='" + address + "', "
                    + "date_of_birth='" + parsed.getDateOfBirth() + "'";

        } else {
            System.out.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        queryString = "INSERT INTO " + table + " (" + fieldString + ") VALUES(" + valueString + ") ON DUPLICATE KEY UPDATE " + updateString;

        System.out.println(queryString);
        try {
            getStatement().executeUpdate(queryString);
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

        connect();
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
