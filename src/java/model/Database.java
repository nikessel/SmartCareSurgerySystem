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
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Database {

    private Connection connection;
    private Statement statement;
    private ResultSet rs = null;
    private final String DATABASEPATH = "jdbc:derby://localhost:1527/SmartCareSurgeryDatabase";
    private final String USERNAME = "databaseUser";
    private final String PASSWORD = "password";
    private final String[] TABLENAMES = {"admins", "doctors", "nurses", "patients", "consultations", "invoices", "surgeries", "prices"};
    private final String[] USERTABLENAMES = {"admins", "doctors", "nurses", "patients"};

    // Hashing variables for PBKDF2
    byte[] hash_candidate = new byte[64];
    byte[] check_hash = new byte[64];
    char[] thisPassCharArray;
    byte[] thisSalt = new byte[32];
    int iterations = 65536;
    int keyLength = 512;
    KeySpec spec;
    SecureRandom random = new SecureRandom();

    private final Calendar calendar = Calendar.getInstance(Locale.UK);
    int currentWeekNumber;

    String queryString = "";
    String idString = "";
    int thisID = -2;
    boolean userNameFound = false;
    String tableName = "";
    String idValue = "";

    // Display more infomation
    int verbosity = 0;

    // This method connects to the database
    public void connect() {

        try {
            connection = DriverManager.getConnection(DATABASEPATH, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    // Getter for statement required for writing back to the Database directly
    // from other classes
    public Statement getStatement() {
        return statement;
    }

    private void closeRSAndStatement() {

        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    private void closeRSAndStatement(ResultSet thisRS, Statement thisStatement) {

        try {
            if (thisRS != null) {
                thisRS.close();
            }
            if (thisStatement != null) {
                thisStatement.close();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
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
                System.err.println(e);
            }
        } else {
            System.err.println("A query must be passed to function executeQuery()");
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {

        statement = connection.createStatement();
        statement.executeUpdate(query);

    }

    public int getCurrentWeekNumber() {
        this.currentWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        return currentWeekNumber;
    }

    private String getIDString(int id) {
        if (id == -1) {
            return "";
        }

        tableName = TABLENAMES[(int) id / 10000 - 1];

        if (tableName.equals("surgeries")) {
            return tableName.substring(0, tableName.length() - 3) + "y_id";
        } else {
            return tableName.substring(0, tableName.length() - 1) + "_id";
        }
    }

    private String getIDString(String tableName) {
        if (tableName.equals("surgeries")) {
            return tableName.substring(0, tableName.length() - 3) + "y_id";
        } else {
            return tableName.substring(0, tableName.length() - 1) + "_id";
        }
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
        idString = getIDString(tableName);

        return "SELECT " + idString + ", username, password_hash, salt FROM " + tableName;

    }

    public boolean isUser(int thisID) {
        return 10000 <= thisID && thisID <= 49999;
    }

    public boolean isEmployee(int thisID) {
        return 10000 <= thisID && thisID <= 39999;
    }

    public boolean isAdmin(int thisID) {
        return 10000 <= thisID && thisID <= 19999;
    }

    public boolean isDoctor(int thisID) {
        return 20000 <= thisID && thisID <= 29999;
    }

    public boolean isNurse(int thisID) {
        return 30000 <= thisID && thisID <= 39999;
    }

    public boolean isPatient(int thisID) {
        return 40000 <= thisID && thisID <= 49999;
    }

    public boolean isConsultation(int thisID) {
        return 50000 <= thisID && thisID <= 59999;
    }

    public boolean isInvoice(int thisID) {
        return 60000 <= thisID && thisID <= 69999;
    }

    public boolean isSurgery(int thisID) {
        return 70000 <= thisID && thisID <= 79999;
    }

    public int getUserID(String username, String password) {

        thisPassCharArray = password.toCharArray();
        userNameFound = false;

        for (String tableName : USERTABLENAMES) {

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
                System.err.println(ex);
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
                idString = getIDString(tableName);
                queryString = "SELECT " + idString + " FROM " + tableName;
                ResultSet rs = executeQuery(queryString);

                while (rs.next()) {
                    thisID = rs.getInt(idString);

                    output.add(thisID);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return output;
    }

    public ArrayList<Integer> getPendingUsers() {
        ArrayList<Integer> output = new ArrayList();

        try {
            for (int i = 1; i < 3; i++) {
                tableName = USERTABLENAMES[i];
                idString = getIDString(tableName);
                ResultSet rs1 = selectFromWhere("*", tableName, "pending", "true");

                while (rs1.next()) {
                    thisID = rs1.getInt(idString);

                    output.add(thisID);
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return output;
    }

    public ArrayList<Integer> getPendingConsultations() {
        ArrayList<Integer> output = new ArrayList();

        try {
            tableName = TABLENAMES[4];
            idString = getIDString(tableName);
            ResultSet rs1 = selectFromWhere("*", tableName, "pending", "true");

            while (rs1.next()) {
                thisID = rs1.getInt(idString);

                output.add(thisID);
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return output;
    }

    public int getPrice(String of) {
        tableName = TABLENAMES[7];

        try {
            ResultSet rs1 = executeQuery("SELECT " + of + "_hourly FROM prices");

            while (rs1.next()) {
                return rs1.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Unable to get surgery price");

        }
        return -1;
    }

    public boolean isUserPending(int id) {

        try {
            for (int i = 1; i < 3; i++) {
                tableName = USERTABLENAMES[i];
                idString = getIDString(tableName);
                ResultSet rs1 = selectFromWhere("*", tableName, idString, String.valueOf(id));

                while (rs1.next()) {
                    return rs1.getBoolean("pending");
                }
            }

        } catch (SQLException ex) {

        }

        return false;
    }

    public void approveEmployee(int id) {
        idString = getIDString(id);

        if (isDoctor(id)) {
            tableName = USERTABLENAMES[1];
        } else if (isNurse(id)) {
            tableName = USERTABLENAMES[2];
        }

        try {

            queryString = "UPDATE " + tableName + " SET pending=false WHERE " + idString + "=" + id;

            System.out.println(queryString);
            executeUpdate(queryString);

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            closeRSAndStatement();
        }

    }

    public void approveConsultation(int id) {
        idString = getIDString(id);

        tableName = TABLENAMES[4];
        try {

            queryString = "UPDATE " + tableName + " SET pending=false WHERE " + idString + "=" + id;

            System.out.println(queryString);
            executeUpdate(queryString);

        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            closeRSAndStatement();
        }

    }

    private DatabaseObject getDatabaseObject(ResultSet rs) throws ClassCastException {

        boolean paid, insured, isFullTime;
        paid = insured = isFullTime = false;

        int id, duration;

        id = duration = -1;
        double price = -1.0;

        String username, firstName, surName, note;
        username = firstName = surName = note = "";

        java.util.Date dateOfBirth, invoiceDate;
        dateOfBirth = invoiceDate = new java.util.Date();

        Timestamp consultationTime, surgeryTime;
        consultationTime = surgeryTime = new Timestamp(0);

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

        if (isUser(id)) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    username = rs.getString("username");

                    // Skip saving password hash and salt
                    rs.getString("password_hash");
                    rs.getString("salt");

                    firstName = rs.getString("first_name");
                    surName = rs.getString("sur_name");
                    address = convertStringToAddress(rs.getString("address"));
                    dateOfBirth = rs.getDate("date_of_birth");
                }

                if (isEmployee(id)) {
                    try {
                        rs.beforeFirst();

                        if (rs.next()) {
                            isFullTime = rs.getBoolean("is_full_time");
                        }

                    } catch (SQLException ex) {
                        System.err.println(ex);
                    }
                } else if (isPatient(id)) {
                    try {
                        rs.beforeFirst();

                        if (rs.next()) {
                            insured = rs.getBoolean("insured");
                        }

                    } catch (SQLException ex) {
                        System.err.println(ex);
                    }
                }

            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

        else if (isConsultation(id)) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    patient = getPatient(rs.getInt("patient_id"));
                    doctor = getDoctor(rs.getInt("doctor_id"));
                    nurse = getNurse(rs.getInt("nurse_id"));
                    consultationTime = Timestamp.convertToMyTimestamp(rs.getTimestamp("consultation_time"));
                    note = rs.getString("note");
                    duration = rs.getInt("duration");
                }

            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

        else if (isInvoice(id)) {
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
                System.err.println(ex);
            }
        }

        else if (isSurgery(id)) {
            try {
                rs.beforeFirst();

                if (rs.next()) {
                    doctor = getDoctor(rs.getInt("doctor_id"));
                    patient = getPatient(rs.getInt("patient_id"));
                    surgeryTime = Timestamp.convertToMyTimestamp(rs.getTimestamp("surgery_time"));

                }
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

        if (isUser(id)) {
            if (isEmployee(id)) {
                if (isAdmin(id)) {
                    return new Admin(username, firstName, surName, dateOfBirth, address, isFullTime, id);
                } else if (isDoctor(id)) {
                    return new Doctor(username, firstName, surName, dateOfBirth, address, isFullTime, id);
                } else if (isNurse(id)) {
                    return new Nurse(username, firstName, surName, dateOfBirth, address, isFullTime, id);
                }
            } else if (isPatient(id)) {
                return new Patient(username, firstName, surName, id, dateOfBirth, address, insured);
            }
        } else if (isConsultation(id)) {
            return new Consultation(patient, doctor, nurse, consultationTime, note, duration, id);
        } else if (isInvoice(id)) {
            return new Invoice(consultation, price, invoiceDate, paid, insured, id);
        } else if (isSurgery(id)) {
            return new Surgery(patient, doctor, surgeryTime, id);
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
                        outputList.add(getAdmin(thisID));
                        break;
                    case "doctors":
                        outputList.add(getDoctor(thisID));
                        break;
                    case "nurses":
                        outputList.add(getNurse(thisID));
                        break;
                    case "patients":
                        outputList.add(getPatient(thisID));
                        break;
                    case "consultations":
                        outputList.add(getConsultation(thisID));
                        break;
                    case "invoices":
                        outputList.add(getInvoice(thisID));
                        break;
                    case "surgeries":
                        outputList.add(getSurgery(thisID));
                        break;

                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return outputList;
    }

    private ResultSet selectFromWhere(String whatToSelect, String fromTable, String where, String is) {
        String isString = "='" + is + "'";
        boolean isNumber, isBoolean;
        isNumber = isBoolean = false;

        try {
            Double.parseDouble(is);
            isNumber = true;
        } catch (NumberFormatException ex) {

        }

        try {
            if (!(is.equals("true") || is.equals("false"))) {
                throw new NumberFormatException();
            }

            Boolean.parseBoolean(is);
            isBoolean = true;
        } catch (NumberFormatException ex1) {

        }

        if (isNumber || isBoolean) {
            isString = "=" + is;
        }

        String queryString = "SELECT " + whatToSelect + " FROM " + fromTable + " WHERE " + where + isString;

        return executeQuery(queryString);
    }

    public Admin getAdmin(int adminID) {

        ResultSet rs1 = selectFromWhere("*", "admins", "admin_id", String.valueOf(adminID));

        try {
            return (Admin) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.err.println("Error getting admin from database (Invalid id?)");
        }

        return new Admin();
    }

    public Doctor getDoctor(int doctorID) {
        ResultSet rs1 = selectFromWhere("*", "doctors", "doctor_id", String.valueOf(doctorID));

        try {
            return (Doctor) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.err.println("Error getting doctor from database (Invalid id?)");
        }

        return new Doctor();
    }

    public Nurse getNurse(int nurseID) {

        ResultSet rs1 = selectFromWhere("*", "nurses", "nurse_id", String.valueOf(nurseID));

        try {
            return (Nurse) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.err.println("Error getting nurse from database (Invalid id?)");
        }

        return new Nurse();
    }

    public Patient getPatient(int patientID) {

        ResultSet rs1 = selectFromWhere("*", "patients", "patient_id", String.valueOf(patientID));

        try {
            return (Patient) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.err.println("Error getting patient from database (Invalid id?)");
        }

        return new Patient();
    }

    public Consultation getConsultation(int consultationID) {

        ResultSet rs1 = selectFromWhere("*", "consultations", "consultation_id", String.valueOf(consultationID));

        try {
            return (Consultation) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.err.println("Error getting consultation from database (Invalid id?)");
        }

        return new Consultation();
    }

    public Invoice getInvoice(int invoiceID) {

        ResultSet rs1 = selectFromWhere("*", "invoices", "invoice_id", String.valueOf(invoiceID));

        try {
            return (Invoice) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.err.println("Error getting invoice from database (Invalid id?)" + "id: " + invoiceID);
        }

        return new Invoice();
    }

    public Surgery getSurgery(int surgeryID) {

        ResultSet rs1 = selectFromWhere("*", "surgeries", "surgery_id", String.valueOf(surgeryID));

        try {
            return (Surgery) getDatabaseObject(rs1);
        } catch (ClassCastException ex) {
            System.out.println(ex);
            System.err.println("Error getting surgery from database (Invalid id?)");
        }

        return new Surgery();
    }

    public ArrayList<Object> getAllFromDatabase(String tableToGet) {

        idString = getIDString(tableToGet);

        queryString = "SELECT " + idString
                + " FROM " + tableToGet;

        ResultSet rs1 = executeQuery(queryString);

        try {
            return getListFromDatabase(rs1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error getting list from database (Invalid where or is?)");
        }

        return new ArrayList<>();

    }

    public ArrayList<Patient> getAllPatients() {
        ResultSet rs1 = executeQuery("SELECT * FROM patients");

        ArrayList<Patient> output = new ArrayList();

        getAllFromDatabase("patients").forEach((obj) -> {
            output.add((Patient) obj);
        });

        return output;

    }

    public ArrayList<Object> getAllFromDatabaseWhereIs(String tableToGet, String where, String is) {
        ResultSet rs1 = selectFromWhere("*", tableToGet, where, is);

        try {
            return getListFromDatabase(rs1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error getting list from database (Invalid where or is?)");
        }

        return new ArrayList<>();

    }

    public ArrayList<Consultation> getAllConsultationsWhereIDIs(int id) {

        idString = getIDString(id);

        ArrayList<Object> objects = getAllFromDatabaseWhereIs("consultations", idString, String.valueOf(id));
        ArrayList<Consultation> output = new ArrayList();

        objects.forEach((obj) -> {
            output.add((Consultation) obj);
        });

        return output;
    }

    public ArrayList<Consultation> getAllConsultationsWhereIDIsFromTo(int id, Date fromDate, Date toDate) {

        idString = getIDString(id);

        ArrayList<Object> objects = getAllFromDatabaseWhereIs("consultations", idString, String.valueOf(id));
        ArrayList<Consultation> output = new ArrayList();

        objects.forEach((obj) -> {
            Consultation thisConsultation = (Consultation) obj;

            if (thisConsultation.getConsultationTime().after(fromDate)
                    && thisConsultation.getConsultationTime().before(toDate)) {
                output.add(thisConsultation);
            }

        });

        return output;
    }

    public ArrayList<Surgery> getAllSurgeriesWhereIDIs(int id) {

        idString = getIDString(id);

        ArrayList<Object> objects = getAllFromDatabaseWhereIs("surgeries", idString, String.valueOf(id));
        ArrayList<Surgery> output = new ArrayList();

        objects.forEach((obj) -> {
            output.add((Surgery) obj);
        });

        return output;
    }

    public ArrayList<Invoice> getAllInvoicesWhereIDIs(int id) {

        idString = getIDString(id);

        ArrayList<Object> objects = getAllFromDatabaseWhereIs("invoices", idString, String.valueOf(id));
        ArrayList<Invoice> output = new ArrayList();

        objects.forEach((obj) -> {
            output.add((Invoice) obj);
        });

        return output;
    }

    public ArrayList<Patient> getAllPatientsWhereIs(String where, String is) {
        ResultSet rs1 = selectFromWhere("*", "patients", where, is);
        ArrayList objects = new ArrayList();
        ArrayList<Patient> output = new ArrayList();

        try {
            objects = getListFromDatabase(rs1);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error getting patient list from database (Invalid where or is?)");
        }

        for (Object obj : objects) {
            output.add((Patient) obj);
        }

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

    public int getLastEntryID(String tableName) {
        idString = getIDString(tableName);
        int output = -1;
        String queryString = "SELECT * FROM " + tableName + " ORDER BY " + idString;
        System.out.println(queryString);
        ResultSet rs1 = executeQuery(queryString);

        try {
            while (rs1.next()) {
                output = rs1.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return output;
    }

    public void addObjectToDatabase(Object object) {
        StringBuilder namesString = new StringBuilder();
        StringBuilder valuesString = new StringBuilder();
        StringBuilder updateString = new StringBuilder();

        String insertPrefix = "INSERT INTO ";
        String valuesPrefix = "VALUES (";
        String updatePrefix = "UPDATE ";

        tableName = "";

        namesString.append("(");

        if (object instanceof User) {
            User user = (User) object;

            namesString.append("username, first_name, sur_name, date_of_birth, address, ");

            valuesString.append("'" + user.getUsername() + "', ");
            valuesString.append("'" + user.getFirstName() + "', ");
            valuesString.append("'" + user.getSurName() + "', ");
            valuesString.append("'" + user.getDateOfBirth() + "', ");
            valuesString.append("'" + convertAddressToString(user.getAddress()) + "', ");

            if (user instanceof Employee) {
                Employee employee = (Employee) object;

                namesString.append("is_full_time, ");

                valuesString.append(employee.isFullTime() + ", ");

                if (employee instanceof Admin) {
                    Admin admin = (Admin) object;
                    tableName = TABLENAMES[0];
                    thisID = admin.getAdminID();

                } else if (employee instanceof Doctor) {
                    Doctor doctor = (Doctor) object;
                    tableName = TABLENAMES[1];

                    thisID = doctor.getDoctorID();
                } else if (employee instanceof Nurse) {
                    Nurse nurse = (Nurse) object;
                    tableName = TABLENAMES[2];

                    thisID = nurse.getNurseID();
                }

                if (!(employee instanceof Admin) && thisID == -1) {
                    namesString.append("pending, ");

                    valuesString.append("true, ");
                }

            } else if (object instanceof Patient) {
                Patient patient = (Patient) object;
                tableName = TABLENAMES[3];
                namesString.append("insured, ");

                valuesString.append(patient.isInsured() + ", ");

                thisID = patient.getPatientID();
            }

        } else if (object instanceof Consultation) {
            Consultation consultation = (Consultation) object;
            tableName = TABLENAMES[4];
            thisID = consultation.getConsultationID();

            namesString.append("patient_id, doctor_id, nurse_id, consultation_time, ");
            valuesString.append(consultation.getPatient().getPatientID() + ", ");
            valuesString.append(consultation.getDoctor().getDoctorID() + ", ");
            valuesString.append(consultation.getNurse().getNurseID() + ", '");
            valuesString.append(consultation.getConsultationTime() + "', ");

            if (thisID == -1) {
                namesString.append("pending, ");

                valuesString.append("true, ");
            }

            namesString.append("note, ");
            valuesString.append("'" + consultation.getNote() + "', ");

            namesString.append("duration, ");
            valuesString.append(consultation.getDuration() + ", ");

        } else if (object instanceof Invoice) {
            Invoice invoice = (Invoice) object;
            tableName = TABLENAMES[5];

            namesString.append("consultation_id, price, date_of_invoice, paid, insured, ");
            valuesString.append(invoice.getConsultation().getConsultationID() + ", ");
            valuesString.append(invoice.getPrice() + ", ");
            valuesString.append("'" + invoice.getDateOfInvoice() + "', ");
            valuesString.append(invoice.isPaid() + ", ");
            valuesString.append(invoice.isInsured() + ", ");

            thisID = invoice.getInvoiceID();
        } else if (object instanceof Surgery) {
            Surgery surgery = (Surgery) object;
            tableName = TABLENAMES[6];

            namesString.append("surgery_id, doctor_id, patient_id, surgery_time, ");
            valuesString.append(surgery.getSurgeryID() + ", ");
            valuesString.append(surgery.getDoctor().getDoctorID() + ", ");
            valuesString.append(surgery.getPatient().getPatientID() + ", ");
            valuesString.append("'" + surgery.getSurgeryTime() + "', ");

            thisID = surgery.getSurgeryID();
        }

        idString = getIDString(thisID);

        namesString.delete(namesString.length() - 2, namesString.length());
        valuesString.delete(valuesString.length() - 2, valuesString.length());

        namesString.append(") ");
        valuesString.append(")");

        String nameStringAsString = namesString.toString();
        String valuesStringAsString = valuesString.toString();

        queryString = insertPrefix + tableName + " " + nameStringAsString + valuesPrefix + valuesStringAsString;

        System.out.println(queryString);
        if (thisID == -1) {
            try {
                executeUpdate(queryString);
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        } else {

            namesString.deleteCharAt(0);
            namesString.delete(namesString.length() - 2, namesString.length());

            valuesString.deleteCharAt(valuesString.length() - 1);
            nameStringAsString = namesString.toString();
            valuesStringAsString = valuesString.toString();

            String[] splitOne = nameStringAsString.split(", ");
            String[] splitTwo = valuesStringAsString.split(", ");

            for (int i = 0; i < splitOne.length; i++) {
                updateString.append(splitOne[i] + "=" + splitTwo[i] + ", ");
            }

            updateString.delete(updateString.length() - 2, updateString.length());

            queryString = updatePrefix + tableName + " SET " + updateString.toString() + " WHERE " + idString + "=" + thisID;

            System.out.println(queryString);
            try {
                executeUpdate(queryString);
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }

    }

    public void addPasswordToUser(User user, String password) {
        String username = user.getUsername();

        if (user instanceof Admin) {
            tableName = TABLENAMES[1];
        } else if (user instanceof Doctor) {
            tableName = TABLENAMES[1];
        } else if (user instanceof Nurse) {
            tableName = TABLENAMES[2];
        } else if (user instanceof Patient) {
            tableName = TABLENAMES[3];
        }

        idString = getIDString(tableName);

        ResultSet rs1 = selectFromWhere(idString, tableName, "username", username);

        try {
            if (rs1.next()) {
                thisID = rs1.getInt(idString);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.err.println("Error adding password to user");
        }

        setPassword(thisID, password);
    }

    public void setPassword(int userID, String password) {
        int lowerBound;
        int upperBound;

        if (!(10000 <= userID && userID <= 49999)) {
            return;
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
            System.err.println(ex);
        } finally {
            closeRSAndStatement();
        }
    }

    public void setPrice(String of, double to) {
        tableName = TABLENAMES[7];

        queryString = "UPDATE prices SET " + of + "_hourly=" + to + " WHERE price_id=80000";

        try {
            executeUpdate(queryString);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    /*
    This method uses the same concept as above, but will delete an object from
    the database
    
     */
    public void deleteObjectFromDatabase(Object object) {

        int id = -1;

        if (object instanceof Admin) {
            id = ((Admin) object).getAdminID();

        } else if (object instanceof Doctor) {
            id = ((Doctor) object).getDoctorID();

        } else if (object instanceof Nurse) {
            id = ((Nurse) object).getNurseID();

        } else if (object instanceof Patient) {
            id = ((Patient) object).getPatientID();

        } else if (object instanceof Consultation) {
            id = ((Consultation) object).getConsultationID();

        } else if (object instanceof Invoice) {
            id = ((Invoice) object).getInvoiceID();

        } else if (object instanceof Surgery) {
            id = ((Surgery) object).getSurgeryID();

        } else {
            System.err.println("Error writing object to database, "
                    + "object type not found.");
            return;
        }

        try {
            tableName = TABLENAMES[id / 10000 - 1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Error deleting object from database (ID not found?)");
            return;
        }
        idString = getIDString(id);

        queryString = "DELETE FROM " + tableName + " WHERE " + idString + " = " + id;

        System.out.println(queryString);
        try {
            executeUpdate(queryString);

        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Error deleting object from database (ID not found?)");
        } finally {
            closeRSAndStatement();
        }
    }

}
