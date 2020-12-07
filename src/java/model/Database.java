/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18034466
 *
 */
/*

 */
public class Database {

    private static Connection connection;
    private static Statement statement;
    private static final String DATABASESTRING = "jdbc:derby://localhost:1527/SmartCareSurgeryDatabase";
    private static final String USERNAME = "databaseUser";
    private static final String PASSWORD = "password";
    private static final String[] TABLENAMES = {"admins", "doctors", "nurses", "patients", "consultations", "invoices"};
    private static final String[] USERTABLENAMES = {"admins", "doctors", "nurses", "patients"};

    // Hashing variables for PBKDF2
    static byte[] hash_candidate = new byte[64];
    static byte[] check_hash = new byte[64];
    static char[] thisPassCharArray;
    static byte[] thisSalt = new byte[32];
    static int iterations = 65536;
    static int keyLength = 512;
    static KeySpec spec;
    static SecureRandom random = new SecureRandom();
    static String queryString = "";
    static String idString = "";
    static String updateString = "";
    static int thisID = -2;
    static boolean userNameFound = false;
    static String tableName = "";
    static String fieldString = "";
    static String valueString = "";
    static String idValue = "";

    // Display more infomation
    static int verbosity = 0;

    // This method connects to the database
    public static void connect() {

        try {
            connection = DriverManager.getConnection(DATABASESTRING, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Getter for statement required for writing back to the Database directly
    // from other classes
    public static Statement getStatement() {
        return statement;
    }

    public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {

        try {
            SecretKeyFactory thisFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec specification = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey thisKey = thisFactory.generateSecret(specification);
            byte[] hash = thisKey.getEncoded();
            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    public static String byteArrayToString(byte[] byteArray) {
        StringBuilder result = new StringBuilder();

        for (byte thisByte : byteArray) {
            result.append(String.format("%02x", thisByte));
        }

        return result.toString();
    }

    public static byte[] hexStringToByteArray(String string) {
        int length = string.length();

        byte[] output = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            output[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                    + Character.digit(string.charAt(i + 1), 16));
        }
        return output;
    }

    public static byte[] getRandomSalt() {
        random.nextBytes(thisSalt);

        return thisSalt;
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
            System.err.println("Database error getting address");
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

    private static String getCredentialsSQLString(String tableName) {
        idString = tableName.substring(0, tableName.length() - 1) + "_id";

        return "SELECT " + idString + ", username, password_hash, salt FROM " + tableName;

    }

    public static int getUserID(String username, String password) {

        thisPassCharArray = password.toCharArray();
        userNameFound = false;

        for (String tableName : USERTABLENAMES) {
            connect();
            queryString = getCredentialsSQLString(tableName) + " WHERE username='" + username + "'";
            ResultSet rs = executeQuery(queryString);

            try {
                while (rs.next()) {
                    userNameFound = true;
                    thisID = rs.getInt(1);
                    thisSalt = hexStringToByteArray(rs.getString("salt"));
                    check_hash = hexStringToByteArray(rs.getString("password_hash"));
                    hash_candidate = hashPassword(thisPassCharArray, thisSalt, iterations, keyLength);

                    if (Arrays.equals(check_hash, hash_candidate)) {
                        return thisID;
                    }

                }

            } catch (SQLException ex) {
                System.out.println(ex);
            } finally {
                closeConnection();
            }
        }

        if (!userNameFound) {
            return -2;
        }

        return -1;
    }

    public static ArrayList<Integer> getIDs() {
        ArrayList<Integer> output = new ArrayList();
        connect();

        try {
            for (String tableName : USERTABLENAMES) {
                idString = tableName.substring(0, tableName.length() - 1) + "_id";
                queryString = "SELECT " + idString + " FROM " + tableName;
                ResultSet rs = executeQuery(queryString);

                while (rs.next()) {
                    thisID = rs.getInt(idString);

                    output.add(thisID);
                }
            }
        } catch (SQLException ex) {

        }

        return output;
    }

    public static Admin getAdmin(int adminID) {

        try {

            connect();
            ResultSet rs = executeQuery("SELECT * FROM admins WHERE admin_id=" + adminID);

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");

                // Skip saving password hash and salt
                rs.getString("password_hash");
                rs.getString("salt");

                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("admin_id");

                // Return the object
                return new Admin(username, "", firstName, surName, isFullTime, id);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }

        System.err.println("Database error getting admin");

        return new Admin("", "", "", "", false, -1);
    }

    public static Doctor getDoctor(int doctorID) {
        connect();
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM doctors WHERE doctor_id=" + doctorID);

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");

                // Skip saving password hash and salt
                rs.getString("password_hash");
                rs.getString("salt");

                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("doctor_id");

                // Return the object
                return new Doctor(username, "", firstName, surName, isFullTime, id);
            }
        } catch (SQLException e) {
            System.out.println(e);

        } finally {
            closeConnection();
        }

        System.err.println("Database error getting doctor");

        return new Doctor("", "", "", "", false, -1);
    }

    public static Nurse getNurse(int nurseID) {
        connect();
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM nurses WHERE nurse_id=" + nurseID);

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");

                // Skip saving password hash and salt
                rs.getString("password_hash");
                rs.getString("salt");
                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                boolean isFullTime = rs.getBoolean("is_full_time");
                int id = rs.getInt("nurse_id");

                // Return the object
                return new Nurse(username, "", firstName, surName, isFullTime, nurseID);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }

        System.err.println("Database error getting nurse");

        return new Nurse("", "", "", "", false, -1);
    }

    public static Patient getPatient(int patientID) {
        connect();
        try {
            ResultSet rs = executeQuery("SELECT * FROM patients WHERE patient_id=" + patientID);

            // iterate through the sql resultset
            while (rs.next()) {
                String username = rs.getString("username");

                // Skip saving password hash and salt
                rs.getString("password_hash");
                rs.getString("salt");

                String firstName = rs.getString("first_name");
                String surName = rs.getString("sur_name");
                int id = rs.getInt("patient_id");
                Address address = convertStringToAddress(rs.getString("address"));
                java.sql.Date dateOfBirth = rs.getDate("date_of_birth");

                // Return the object
                return new Patient(username, "", firstName, surName, patientID, dateOfBirth, address);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }

        System.err.println("Database error getting patient");

        return new Patient("", "", "", "", -1, new java.sql.Date(0000 - 00 - 00), new Address("", "", "", "", "", ""));
    }

    public static Consultation getConsultation(int consultationID) {
        connect();

        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Nurse nurse = new Nurse();
        java.sql.Date consultationDate = java.sql.Date.valueOf("1970-01-01");

        try {
            ResultSet rs = executeQuery("SELECT * FROM consultations WHERE consultation_id=" + consultationID);

            // iterate through the sql resultset
            while (rs.next()) {
                patient = getPatient(rs.getInt("patient_id"));
                doctor = getDoctor(rs.getInt("doctor_id"));
                nurse = getNurse(rs.getInt("nurse_id"));
                consultationDate = rs.getDate("consultation_date");

                // Return the object
                return new Consultation(patient, doctor, nurse, consultationDate, consultationID);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }

        System.err.println("Database error getting consultation with ID: " + consultationID);

        return new Consultation(patient, doctor, nurse, consultationDate, -1);
    }
    
        public static Invoice getInvoice(int invoiceID) {
        connect();
        
        java.sql.Date invoiceDate = java.sql.Date.valueOf("1970-01-01");
        Consultation consultation = new Consultation();

        try {
            ResultSet rs = executeQuery("SELECT * FROM invoices WHERE invoice_id=" + invoiceID);

            // iterate through the sql resultset
            while (rs.next()) {
                int invoice_id = rs.getInt("invoice_id");
                double price = rs.getDouble("price");
                invoiceDate = rs.getDate("date_of_invoice");
                consultation = getConsultation(rs.getInt("consultation_id"));
                boolean paid = rs.getBoolean("paid");
                boolean insured = rs.getBoolean("insured");

                // Return the object
                return new Invoice(consultation, price, invoiceDate, paid, insured);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeConnection();
        }

        System.err.println("Database error getting invoice with ID: " + invoiceID);

        return new Invoice(consultation, -1.0, invoiceDate, false, false);
    }


    public static ArrayList<Object> getAllFromDatabase(String tableToGet) {

        ArrayList<Object> outputList = new ArrayList();

        try {
            idString = tableToGet.substring(0, tableToGet.length() - 1) + "_id";

            queryString = "SELECT " + idString
                    + " FROM " + tableToGet;

            connect();
            ResultSet rs = Database.executeQuery(queryString);

            // iterate through the sql resultset
            while (rs.next()) {
                int id = rs.getInt(idString);

                switch (tableToGet) {
                    case "admins":
                        outputList.add(getAdmin(id));
                        break;
                    case "doctors":
                        outputList.add(getDoctor(id));
                        break;
                    case "nurses":
                        outputList.add(getNurse(id));
                        break;
                    case "patients":
                        outputList.add(getPatient(id));
                        break;
                    case "consultations":
                        outputList.add(getConsultation(id));
                        break;
                    case "invoices":
                        outputList.add(getInvoice(id));
                        break;

                }

            }
        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            closeConnection();
        }

        return outputList;
    }

    public static void printDatabaseTable(String tableToPrint) {
        ArrayList<Object> thisTable = new ArrayList();

        if ("all".equals(tableToPrint)) {
            for (String TABLENAMES1 : TABLENAMES) {
                System.out.println("");
                System.out.println("---------- " + TABLENAMES1 + " ----------");
                System.out.println("");

                printDatabaseTable(TABLENAMES1);
            }
            return;
        }

        thisTable = getAllFromDatabase(tableToPrint);

        // Print the table
        for (int i = 0; i < thisTable.size(); i++) {
            System.out.println(thisTable.get(i));
        }
    }

    
    public static void writeObjectToDatabase(Object object) {

        idString = "";
        connect();

        if (object instanceof Admin) {
            Admin parsed = (Admin) object;

            tableName = "admins";
            fieldString = "username, first_name, sur_name, is_full_time";

            if (parsed.getAdminID() != -1) {
                idString = ", " + String.valueOf(parsed.getAdminID());
                fieldString = "username, first_name, sur_name, admin_id, is_full_time";
            }

            valueString = "'" + parsed.getUsername() + "'"
                    + ", '" + parsed.getFirstName() + "'"
                    + ", '" + parsed.getSurName() + "'"
                    + idString
                    + ", " + parsed.isFullTime();

            updateString = "username='" + parsed.getUsername() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "is_full_time=" + parsed.isFullTime();

        } else if (object instanceof Doctor) {
            Doctor parsed = (Doctor) object;

            tableName = "doctors";
            fieldString = "username, first_name, sur_name, is_full_time";

            if (parsed.getDoctorID() != -1) {
                idString = ", " + String.valueOf(parsed.getDoctorID());
                fieldString = "username, first_name, sur_name, doctor_id, is_full_time";
            }

            valueString = "'" + parsed.getUsername() + "'"
                    + ", '" + parsed.getFirstName() + "'"
                    + ", '" + parsed.getSurName() + "'"
                    + idString
                    + ", " + parsed.isFullTime();

            updateString = "username='" + parsed.getUsername() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "is_full_time=" + parsed.isFullTime();

        } else if (object instanceof Nurse) {
            Nurse parsed = (Nurse) object;

            tableName = "nurses";
            fieldString = "username, first_name, sur_name, is_full_time";

            if (parsed.getNurseID() != -1) {
                idString = ", " + String.valueOf(parsed.getNurseID());
                fieldString = "username, first_name, sur_name, nurse_id, is_full_time";
            }

            valueString = "'" + parsed.getUsername() + "'"
                    + ", '" + parsed.getFirstName() + "'"
                    + ", '" + parsed.getSurName() + "'"
                    + idString
                    + ", " + parsed.isFullTime();

            updateString = "username='" + parsed.getUsername() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "is_full_time=" + parsed.isFullTime();

        } else if (object instanceof Patient) {

            Patient parsed = (Patient) object;

            String address = convertAddressToString(parsed.getAddress());

            tableName = "patients";

            fieldString = "username, first_name, sur_name, date_of_birth, address";

            if (parsed.getPatientID() != -1) {
                idString = ", " + String.valueOf(parsed.getPatientID());
                fieldString = "username, first_name, sur_name, patient_id, date_of_birth, address";
            }

            valueString = "'" + parsed.getUsername() + "'"
                    + ", '" + parsed.getFirstName() + "'"
                    + ", '" + parsed.getSurName() + "'"
                    + idString
                    + ", '" + parsed.getDateOfBirth() + "'"
                    + ", '" + address + "'";

            updateString = "username='" + parsed.getFirstName() + "', "
                    + "first_name='" + parsed.getFirstName() + "', "
                    + "sur_name='" + parsed.getSurName() + "', "
                    + "date_of_birth='" + parsed.getDateOfBirth() + "', "
                    + "address='" + address + "'";

        } else if (object instanceof Consultation) {

            Consultation parsed = (Consultation) object;

            tableName = "consultations";

            fieldString = "patient_id, doctor_id, nurse_id, consultation_date";

            if (parsed.getConsulationID() != -1) {
                idString = ", " + String.valueOf(parsed.getConsulationID());
                fieldString = "patient_id, doctor_id, nurse_id, consultation_date, consultation_id";
            }

            valueString = "" + parsed.getPatient().getPatientID()
                    + ", " + parsed.getDoctor().getDoctorID()
                    + ", " + parsed.getNurse().getNurseID()
                    + ", '" + parsed.getConsulationDate() + "'"
                    + idString;

            updateString = "patient_id=" + parsed.getPatient().getPatientID() + ", "
                    + "doctor_id=" + parsed.getDoctor().getDoctorID() + ", "
                    + "nurse_id=" + parsed.getNurse().getNurseID() + ", "
                    + "consultation_date='" + parsed.getConsulationDate() + "'";

        } else {
            System.out.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        queryString = "INSERT INTO " + tableName + " (" + fieldString + ") VALUES(" + valueString + ")";

        System.out.println(queryString);
        try {
            getStatement().executeUpdate(queryString);
        } catch (SQLException e) {
            try {
                getStatement().executeUpdate("UPDATE " + updateString);
            } catch (SQLException ex) {
                System.out.println(e);
                System.err.println("Error writing object to database");
            }
        } finally {
            closeConnection();
        }
    }

    public static String setPassword(int userID, String password) {
        int lowerBound;
        int upperBound;

        if (!(10000 <= userID && userID <= 49999)) {
            return "Invalid userID";
        }

        for (int i = 0; i < USERTABLENAMES.length; i++) {
            lowerBound = (i + 1) * 10000;
            upperBound = (i + 2) * 10000 - 1;

            if (lowerBound <= userID && userID <= upperBound) {
                tableName = USERTABLENAMES[i];
                idString = tableName.substring(0, tableName.length() - 1) + "_id";
            }
        }

        byte[] salt = getRandomSalt();
        thisPassCharArray = password.toCharArray();
        byte[] passwordHash = hashPassword(thisPassCharArray, salt, iterations, keyLength);

        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE " + tableName + " SET password_hash = ?, salt = ? WHERE " + idString + " = ?");

            // set the preparedstatement parameters
            ps.setString(1, byteArrayToString(passwordHash));
            ps.setString(2, byteArrayToString(salt));
            ps.setInt(3, userID);

            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            closeConnection();
        }

        return "";
    }

    /*
    This method uses the same concept as above, but will delete an object from
    the database
    
     */
    public static void deleteObjectFromDatabase(Object object) {

        connect();
        
        if (object instanceof Admin) {
            Admin parsed = (Admin) object;

            tableName = "admins";
            idString = "admin_id";
            idValue = String.valueOf(parsed.getAdminID());

        } else if (object instanceof Doctor) {
            Doctor parsed = (Doctor) object;

            tableName = "doctors";
            idString = "doctor_id";
            idValue = String.valueOf(parsed.getDoctorID());

        } else if (object instanceof Nurse) {
            Nurse parsed = (Nurse) object;

            tableName = "nurses";
            idString = "nurse_id";
            idValue = String.valueOf(parsed.getNurseID());

        } else if (object instanceof Patient) {
            Patient parsed = (Patient) object;

            tableName = "patients";
            idString = "patient_id";
            idValue = String.valueOf(parsed.getPatientID());

        } else if (object instanceof Consultation) {
            Consultation parsed = (Consultation) object;

            tableName = "consultations";
            idString = "consultation_id";
            idValue = String.valueOf(parsed.getConsulationID());

        } else {
            System.out.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        queryString = "DELETE FROM " + tableName + " WHERE " + idString + " = " + idValue;

        System.out.println(queryString);
        try {
            statement.executeUpdate(queryString);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("Error deleting object from database (ID not found?)");
        } finally {
            closeConnection();
        }
    }

}
