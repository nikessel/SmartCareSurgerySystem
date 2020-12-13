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
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                return statement.executeQuery(query);
            } catch (SQLException e) {
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

    private String getIDString(int id) {
        tableName = "";

        tableName = TABLENAMES[(int) id / 10000 + 1];

        return tableName.substring(0, tableName.length() - 1) + "_id";
    }

    private String getIDString(String tableName) {
        return tableName.substring(0, tableName.length() - 1) + "_id";
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

    private DatabaseObject getDatabaseObject(ResultSet rs) throws ClassCastException {

        boolean isUser, isEmployee, isAdmin, isDoctor, isNurse, isPatient,
                isConsultation, isInvoice, paid, insured, isFullTime;
        isUser = isEmployee = isAdmin = isDoctor = isNurse = isPatient
                = isConsultation = isInvoice = paid = insured = isFullTime = false;

        int id = -1;

        double price = -1.0;

        String username, firstName, surName;
        username = firstName = surName = "";

        java.sql.Date dateOfBirth, consultationDate, invoiceDate;
        dateOfBirth = consultationDate = invoiceDate = java.sql.Date.valueOf("1000-01-01");

        Invoice invoice = new Invoice();
        Address address = new Address();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Nurse nurse = new Nurse();
        Consultation consultation = new Consultation();

        // Get ID
        try {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            try {
                rs.beforeFirst();

            } catch (SQLException ex) {

            }
        }

        if (10000 <= id && id <= 49999) {
            isUser = true;

            if (10000 <= id && id <= 39999) {
                isEmployee = true;

                if (10000 <= id && id <= 19999) {
                    isAdmin = true;
                } else if (20000 <= id && id <= 29999) {
                    isDoctor = true;
                } else if (30000 <= id && id <= 39999) {
                    isNurse = true;
                }
            } else if (40000 <= id && id <= 49999) {
                isPatient = true;
            }
        } else if (50000 <= id && id <= 59999) {
            isConsultation = true;
        } else if (60000 <= id && id <= 69999) {
            isInvoice = true;
        }

        if (isUser) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    username = rs.getString("username");

                    // Skip saving password hash and salt
                    rs.getString("password_hash");
                    rs.getString("salt");

                    firstName = rs.getString("first_name");
                    surName = rs.getString("sur_name");
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        if (isEmployee) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    isFullTime = rs.getBoolean("is_full_time");
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        if (isPatient) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    address = convertStringToAddress(rs.getString("address"));
                    dateOfBirth = rs.getDate("date_of_birth");
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        if (isConsultation) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    patient = getPatient(rs.getInt("patient_id"));
                    doctor = getDoctor(rs.getInt("doctor_id"));
                    nurse = getNurse(rs.getInt("nurse_id"));
                    consultationDate = rs.getDate("consultation_date");
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        if (isInvoice) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    price = rs.getDouble("price");
                    invoiceDate = rs.getDate("date_of_invoice");
                    consultation = getConsultation(rs.getInt("consultation_id"));
                    paid = rs.getBoolean("paid");
                    insured = rs.getBoolean("insured");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }

        if (isUser) {
            if (isEmployee) {
                if (isAdmin) {
                    return new Admin(username, firstName, surName, isFullTime, id);
                } else if (isDoctor) {
                    return new Doctor(username, firstName, surName, isFullTime, id);
                } else if (isNurse) {
                    return new Nurse(username, firstName, surName, isFullTime, id);
                }
            } else if (isPatient) {
                return new Patient(username, firstName, surName, id, dateOfBirth, address);
            }
        } else if (isConsultation) {
            return new Consultation(patient, doctor, nurse, consultationDate, id);
        } else if (isInvoice) {
            return new Invoice(consultation, price, dateOfBirth, paid, insured, id);
        }

        return new DatabaseObject();
    }

    public ArrayList<Object> getListFromDatabase(ResultSet rs) throws ArrayIndexOutOfBoundsException {

        ArrayList<Object> outputList = new ArrayList();
        int id = -1;

        // Get ID
        try {
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            try {
                rs.beforeFirst();

            } catch (SQLException ex) {

            }
        }

        String name = TABLENAMES[(int) id / 10000 - 1];

        // iterate through the sql resultset
        try {
            rs.beforeFirst();
            while (rs.next()) {
                int thisID = rs.getInt(1);

                switch (name) {
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
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return outputList;
    }

    private ResultSet selectFromWhere(String whatToSelect, String fromTable, String where, String is) {
        String isString = "='" + is + "'";

        try {
            Double.parseDouble(is);
            isString = "=" + is;
        } catch (NumberFormatException ex) {

        }

        return executeQuery("SELECT " + whatToSelect + " FROM " + fromTable + " WHERE " + where + isString);
    }

    public Admin getAdmin(int adminID) {

        ResultSet rs1 = selectFromWhere("*", "admins", "admin_id", String.valueOf(adminID));

        try {
            return (Admin) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println("Error getting admin from database (Invalid id?)");
        }

        return new Admin();
    }

    public Doctor getDoctor(int doctorID) {
        ResultSet rs1 = selectFromWhere("*", "doctors", "doctor_id", String.valueOf(doctorID));

        try {
            return (Doctor) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println("Error getting doctor from database (Invalid id?)");
        }

        return new Doctor();
    }

    public Nurse getNurse(int nurseID) {

        ResultSet rs1 = selectFromWhere("*", "nurses", "nurse_id", String.valueOf(nurseID));

        try {
            return (Nurse) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println("Error getting nurse from database (Invalid id?)");
        }

        return new Nurse();
    }

    public Patient getPatient(int patientID) {

        ResultSet rs1 = selectFromWhere("*", "patients", "patient_id", String.valueOf(patientID));

        try {
            return (Patient) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println("Error getting patient from database (Invalid id?)");
        }

        return new Patient();
    }

    public Consultation getConsultation(int consultationID) {

        ResultSet rs1 = selectFromWhere("*", "consultations", "consultation_id", String.valueOf(consultationID));

        try {
            return (Consultation) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println("Error getting consultation from database (Invalid id?)");
        }

        return new Consultation();
    }

    public Invoice getInvoice(int invoiceID) {

        ResultSet rs1 = selectFromWhere("*", "invoices", "invoice_id", String.valueOf(invoiceID));

        try {
            return (Invoice) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println("Error getting invoice from database (Invalid id?)");
        }

        return new Invoice();
    }

    public ArrayList<Object> getAllFromDatabase(String tableToGet) {

        idString = tableToGet.substring(0, tableToGet.length() - 1) + "_id";

        queryString = "SELECT " + idString
                + " FROM " + tableToGet;

        ResultSet rs1 = executeQuery(queryString);

        try {
            return getListFromDatabase(rs1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error getting list from database (Invalid where or is?)");
        }

        return new ArrayList<Object>();

    }

    public ArrayList<Object> getAllFromDatabaseWhereIs(String tableToGet, String where, String is) {
        ResultSet rs1 = selectFromWhere("*", tableToGet, where, is);

        try {
            return getListFromDatabase(rs1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error getting list from database (Invalid where or is?)");
        }

        return new ArrayList<Object>();

    }

    public ArrayList<Consultation> getAllConsultationsWhereIs(String where, String is) {
        ArrayList<Object> objects = getAllFromDatabaseWhereIs("consultations", where, is);
        ArrayList<Consultation> output = new ArrayList();

        objects.forEach((obj) -> {
            output.add((Consultation) obj);
        });

        return output;
    }

    public ArrayList<Invoice> getAllInvoicesWhereIs(String where, String is) {
        ArrayList<Object> objects = getAllFromDatabaseWhereIs("invoices", where, is);
        ArrayList<Invoice> output = new ArrayList();

        objects.forEach((obj) -> {
            output.add((Invoice) obj);
        });

        return output;
    }

    public void printDatabaseTable(String tableToPrint) {
        if ("all".equals(tableToPrint)) {
            for (String TABLENAMES1 : TABLENAMES) {
                System.out.println("");
                System.out.println("---------- " + TABLENAMES1 + " ----------");
                System.out.println("");

                printDatabaseTable(TABLENAMES1);
            }
            return;
        }

        ArrayList<Object> thisTable = getAllFromDatabase(tableToPrint);

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
