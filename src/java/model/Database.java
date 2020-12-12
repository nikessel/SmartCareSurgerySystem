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

    private Connection connection;
    private Statement statement;
    private ResultSet rs = null;
    private final String DATABASESTRING = "jdbc:derby://localhost:1527/SmartCareSurgeryDatabase";
    private final String USERNAME = "databaseUser";
    private final String PASSWORD = "password";
    private final String[] TABLENAMES = {"admins", "doctors", "nurses", "patients", "consultations", "invoices"};
    private final String[] USERTABLENAMES = {"admins", "doctors", "nurses", "patients"};
    private boolean closeStatement = true;

    // Hashing variables for PBKDF2
    byte[] hash_candidate = new byte[64];
    byte[] check_hash = new byte[64];
    char[] thisPassCharArray;
    byte[] thisSalt = new byte[32];
    int iterations = 65536;
    int keyLength = 512;
    KeySpec spec;
    SecureRandom random = new SecureRandom();
    String queryString = "";
    String idString = "";
    String updateString = "";
    int thisID = -2;
    boolean userNameFound = false;
    String tableName = "";
    String fieldString = "";
    String valueString = "";
    String idValue = "";

    // Display more infomation
    int verbosity = 0;

    // This method connects to the database
    public void connect() {

        try {
            connection = DriverManager.getConnection(DATABASESTRING, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void closeConnection() {
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
    public Statement getStatement() {
        return statement;
    }

    private void closeRSAndStatement() {
        if (closeStatement) {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    private void closeRSAndStatement(ResultSet thisRS, Statement thisStatement) {
        if (closeStatement) {
            try {
                if (thisRS != null) {
                    thisRS.close();
                }
                if (thisStatement != null) {
                    thisStatement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {

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

    public String byteArrayToString(byte[] byteArray) {
        StringBuilder result = new StringBuilder();

        for (byte thisByte : byteArray) {
            result.append(String.format("%02x", thisByte));
        }

        return result.toString();
    }

    public byte[] hexStringToByteArray(String string) {
        int length = string.length();

        byte[] output = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            output[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                    + Character.digit(string.charAt(i + 1), 16));
        }
        return output;
    }

    public byte[] getRandomSalt() {
        random.nextBytes(thisSalt);

        return thisSalt;
    }

    // Executes read operation on the database, takes a mySQL command as input
    public ResultSet executeQuery(String query) {
        if (!"".equals(query)) {
            try {
                statement = connection.createStatement();
                rs = statement.executeQuery(query);
                return rs;
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("A query must be passed to function executeQuery()");
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {

        statement = connection.createStatement();
        statement.executeUpdate(query);

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
            System.err.println("Database error getting address");
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

    private String getCredentialsSQLString(String tableName) {
        idString = tableName.substring(0, tableName.length() - 1) + "_id";

        return "SELECT " + idString + ", username, password_hash, salt FROM " + tableName;

    }

    public int getUserID(String username, String password) {

        thisPassCharArray = password.toCharArray();
        userNameFound = false;

        for (String tableName : USERTABLENAMES) {

            queryString = getCredentialsSQLString(tableName) + " WHERE username='" + username + "'";
            this.executeQuery(queryString);

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
                closeRSAndStatement();
            }
        }

        if (!userNameFound) {
            return -2;
        }

        return -1;
    }

    public ArrayList<Integer> getIDs() {
        ArrayList<Integer> output = new ArrayList();

        try {
            for (String tableName : USERTABLENAMES) {
                idString = tableName.substring(0, tableName.length() - 1) + "_id";
                queryString = "SELECT " + idString + " FROM " + tableName;
                this.executeQuery(queryString);

                while (rs.next()) {
                    thisID = rs.getInt(idString);

                    output.add(thisID);
                }
            }
        } catch (SQLException ex) {

        }

        return output;
    }

    public Admin getAdmin(int adminID) {

        try {

            executeQuery("SELECT * FROM admins WHERE admin_id=" + adminID);

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
            closeRSAndStatement();

        }

        System.err.println("Database error getting admin");

        return new Admin("", "", "", "", false, -1);
    }

    public Doctor getDoctor(int doctorID) {

        try {
            this.executeQuery("SELECT * FROM doctors WHERE doctor_id=" + doctorID);

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
            closeRSAndStatement();

        }

        System.err.println("Database error getting doctor");

        return new Doctor("", "", "", "", false, -1);
    }

    public Nurse getNurse(int nurseID) {

        try {
            this.executeQuery("SELECT * FROM nurses WHERE nurse_id=" + nurseID);

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
            closeRSAndStatement();

        }

        System.err.println("Database error getting nurse");

        return new Nurse("", "", "", "", false, -1);
    }

    public Patient getPatient(int patientID) {

        try {
            this.executeQuery("SELECT * FROM patients WHERE patient_id=" + patientID);

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
            closeRSAndStatement();

        }

        System.err.println("Database error getting patient");

        return new Patient("", "", "", "", -1, new java.sql.Date(0000 - 00 - 00), new Address("", "", "", "", "", ""));
    }

    public Consultation getConsultation(int consultationID) {

        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Nurse nurse = new Nurse();
        java.sql.Date consultationDate = java.sql.Date.valueOf("1970-01-01");
        ResultSet consultationRS = null;
        Statement consultationStatement = null;

        try {
            consultationStatement = connection.createStatement();
            consultationRS = consultationStatement.executeQuery("SELECT * FROM consultations WHERE consultation_id=" + consultationID);

            // iterate through the sql resultset
            while (consultationRS.next()) {
                patient = getPatient(consultationRS.getInt("patient_id"));
                doctor = getDoctor(consultationRS.getInt("doctor_id"));
                nurse = getNurse(consultationRS.getInt("nurse_id"));
                consultationDate = consultationRS.getDate("consultation_date");

                // Return the object
                return new Consultation(patient, doctor, nurse, consultationDate, consultationID);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeRSAndStatement(consultationRS, consultationStatement);

        }

        System.err.println("Database error getting consultation with ID: " + consultationID);

        return new Consultation(patient, doctor, nurse, consultationDate, -1);
    }

    public Invoice getInvoice(int invoiceID) {

        java.sql.Date invoiceDate = java.sql.Date.valueOf("1970-01-01");
        Consultation consultation = new Consultation();
        ResultSet invoiceRS = null;
        Statement invoiceStatement = null;

        try {
            invoiceStatement = connection.createStatement();
            invoiceRS = invoiceStatement.executeQuery("SELECT * FROM invoices WHERE invoice_id=" + invoiceID);

            // iterate through the sql resultset
            while (invoiceRS.next()) {
                int invoice_id = invoiceRS.getInt("invoice_id");
                double price = invoiceRS.getDouble("price");
                invoiceDate = invoiceRS.getDate("date_of_invoice");
                consultation = getConsultation(invoiceRS.getInt("consultation_id"));
                boolean paid = invoiceRS.getBoolean("paid");
                boolean insured = invoiceRS.getBoolean("insured");

                // Return the object
                return new Invoice(consultation, price, invoiceDate, paid, insured);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeRSAndStatement(invoiceRS, invoiceStatement);
        }

        System.err.println("Database error getting invoice with ID: " + invoiceID);

        return new Invoice(consultation, -1.0, invoiceDate, false, false);
    }

    public ArrayList<Object> getAllFromDatabase(String tableToGet) {

        ArrayList<Object> outputList = new ArrayList();

        ResultSet idRS = null;
        Statement idStatement = null;

        try {
            idStatement = connection.createStatement();
            idString = tableToGet.substring(0, tableToGet.length() - 1) + "_id";

            queryString = "SELECT " + idString
                    + " FROM " + tableToGet;

            idRS = executeQuery(queryString);

            // iterate through the sql resultset
            while (idRS.next()) {
                int id = idRS.getInt(idString);

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
            closeRSAndStatement(idRS, idStatement);
        }

        return outputList;
    }

    public void printDatabaseTable(String tableToPrint) {
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

    public void writeObjectToDatabase(Object object) {

        idString = "";

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

            if (parsed.getConsultationID() != -1) {
                idString = ", " + String.valueOf(parsed.getConsultationID());
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
            executeUpdate(queryString);
        } catch (SQLException e) {
            try {
                executeUpdate("UPDATE " + updateString);
            } catch (SQLException ex) {
                System.out.println(e);
                System.err.println("Error writing object to database");
            }
        } finally {
            closeRSAndStatement();
        }
    }

    public String setPassword(int userID, String password) {
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
            closeRSAndStatement();
        }

        return "";
    }

    /*
    This method uses the same concept as above, but will delete an object from
    the database
    
     */
    public void deleteObjectFromDatabase(Object object) {

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
            idValue = String.valueOf(parsed.getConsultationID());

        } else {
            System.out.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        queryString = "DELETE FROM " + tableName + " WHERE " + idString + " = " + idValue;

        System.out.println(queryString);
        try {
            executeUpdate(queryString);
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("Error deleting object from database (ID not found?)");
        } finally {
            closeRSAndStatement();
        }
    }

}
