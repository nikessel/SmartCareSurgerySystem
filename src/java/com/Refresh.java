/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public class Refresh extends HttpServlet {

    // Attributes
    private int currentUserID, selectedConsultatantID, selectedHour, selectedMinute,
            selectedYear, selectedMonth, selectedDayOfMonth, checkID, selectedTimetable,
            requestType, choice, previousUserID, thisID, newUserID;
    private double price, turnoverPaid, turnoverUnpaid, newPrice, consultationPrice,
            consultationPriceNurse, surgeryPrice;
    private boolean isEmployee, isAdmin, isDoctor, isNurse, isPatient, approve,
            resetRequestType, success;

    private BigDecimal rounded;

    private String loggedInAs, jspContext, viewString, message, note, medication, of, setPriceFor;

    private Date fromDate, toDate, expirationDate;

    private Timestamp timestamp;

    private String[] selectedDate, selectedTime;

    // User objects
    private User currentUser, previousUser;

    private Doctor doctor;
    private Nurse nurse;
    private Patient patient;

    // Other database objects
    private Consultation consultation;
    private Invoice invoice;
    private Surgery surgery;
    private Prescription prescription;

    // Object ID lists
    private ArrayList<Integer> pendingEmployeeIDs, pendingConsultationIDs,
            pendingSurgeryIDs, pendingPrescriptionIDs;

    // User object lists
    private ArrayList<Doctor> doctors;
    private ArrayList<Nurse> nurses;
    private ArrayList<Patient> patients;
    private ArrayList<Employee> pendingEmployees;

    // Other database object lists
    private ArrayList<Consultation> consultations;
    private ArrayList<Consultation> pendingConsultations;
    private ArrayList<Prescription> pendingPrescriptions;
    private ArrayList<Surgery> pendingSurgeries;
    private ArrayList<Invoice> invoices;
    private ArrayList<Object> temps;
    private ArrayList<Surgery> surgeries;
    private ArrayList<Prescription> prescriptions;

    // Servlet releated objects
    private HttpSession session;
    private HttpServletRequest request;
    private RequestDispatcher view;

    private Database database;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /* --------- Refresh servlet ---------
    
    This servlet handles all of the non-public controller logic in the webapp,
    this has been done to minimise redundant code.
    
     */
    // Set the user booleans based on session attributes set previously by the authentication filter
    private void setBooleans() {

        isEmployee = isAdmin = isDoctor = isPatient = false;

        if (session.getAttribute("isEmployee") != null) {
            isEmployee = true;

            if (session.getAttribute("isAdmin") != null) {
                isAdmin = true;
            } else if (session.getAttribute("isDoctor") != null) {
                isDoctor = true;
            } else if (session.getAttribute("isNurse") != null) {
                isNurse = true;
            }
        } else {
            isPatient = true;
        }
    }

    /* ---------- Set pending lists methods ----------
    
    These methods will set lists of various pending requests as session attributes
    
     */
    private void setPendingConsultations() {
        pendingConsultationIDs = database.getPending("consultations");
        pendingConsultations = new ArrayList<>();

        try {

            for (int id : pendingConsultationIDs) {
                consultation = database.getConsultation(id);

                if (database.isDoctor(currentUserID)) {
                    checkID = consultation.getDoctor().getDoctorID();
                } else {
                    checkID = consultation.getNurse().getNurseID();
                }

                if (checkID == currentUserID) {
                    pendingConsultations.add(consultation);
                }
            }

        } catch (Exception ex) {

        }
        session.setAttribute("pendingConsultations", pendingConsultations);
    }

    private void setPendingSurgeries() {
        pendingSurgeryIDs = database.getPending("surgeries");
        pendingSurgeries = new ArrayList<>();

        try {

            for (int id : pendingSurgeryIDs) {
                surgery = database.getSurgery(id);

                checkID = surgery.getDoctor().getDoctorID();

                if (checkID == currentUserID) {
                    pendingSurgeries.add(surgery);
                }
            }

        } catch (Exception ex) {
            message = ex.toString();
        }
        session.setAttribute("pendingSurgeries", pendingSurgeries);
    }

    private void setPendingPrescriptions() {
        pendingPrescriptionIDs = database.getPending("prescriptions");
        pendingPrescriptions = new ArrayList<>();

        try {

            for (int id : pendingPrescriptionIDs) {
                prescription = database.getPrescription(id);

                checkID = prescription.getDoctor().getDoctorID();

                if (checkID == currentUserID) {
                    pendingPrescriptions.add(prescription);
                }
            }

        } catch (Exception ex) {
            message = ex.toString();
        }
        session.setAttribute("pendingPrescriptions", pendingPrescriptions);
    }

    private void setPendingEmployees() {
        pendingEmployeeIDs = database.getPendingUsers();
        pendingEmployees = new ArrayList<>();

        try {

            pendingEmployeeIDs.forEach((i) -> {
                if (database.isNurse(i)) {
                    pendingEmployees.add(database.getNurse(i));
                } else if (database.isDoctor(i)) {
                    pendingEmployees.add(database.getDoctor(i));
                }
            });

        } catch (Exception ex) {

        }

        session.setAttribute("pendingEmployees", pendingEmployees);

    }

    /* ---------- Approve selected object methods ----------
    
    These methods will ask the database to set the "pending" column of various
    objects to false. Once approved the object will not be in the pending lists
    generated by the methods above.
    
     */
    private void approveEmployee() {
        try {
            thisID = Integer.parseInt(request.getParameterValues("pendingEmployeeSelection")[0]);

            approve = Boolean.parseBoolean(request.getParameter("approve"));

            if (approve) {
                database.setPending(thisID, false);

            } else {
                database.deleteObjectFromDatabase(thisID);
            }

        } catch (Exception ex) {
            message = "";
        }
    }

    private boolean approvePendingConsultation() {
        try {
            thisID = Integer.parseInt(request.getParameterValues("pendingConsultationSelection")[0]);

            try {
                approve = Boolean.parseBoolean(request.getParameter("approveConsultation"));
            } catch (Exception ex) {
                approve = false;
            }

            if (approve) {
                database.setPending(thisID, false);
                return true;

            } else {
                database.deleteObjectFromDatabase(thisID);
            }

        } catch (Exception ex) {
        }
        return false;
    }

    private boolean approvePendingSurgery() {
        try {
            thisID = Integer.parseInt(request.getParameterValues("pendingSurgerySelection")[0]);
            try {
                approve = Boolean.parseBoolean(request.getParameter("approveSurgery"));
            } catch (Exception ex) {
                approve = false;
            }

            if (approve) {
                database.setPending(thisID, false);
                return true;

            } else {
                database.deleteObjectFromDatabase(thisID);
            }

        } catch (Exception ex) {
        }
        return false;
    }

    private boolean approvePendingPrescription() {
        try {
            thisID = Integer.parseInt(request.getParameterValues("pendingPrescriptionSelection")[0]);
            try {
                approve = Boolean.parseBoolean(request.getParameter("approvePrescription"));
            } catch (Exception ex) {
                approve = false;
            }

            if (approve) {
                database.setPending(thisID, false);

                expirationDate = Date.valueOf(request.getParameter("newPrescriptionDate"));

                prescription = database.getPrescription(thisID);

                prescription.setExpirationDate(expirationDate);

                database.addObjectToDatabase(prescription);

                return true;

            } else {
                database.deleteObjectFromDatabase(thisID);
            }

        } catch (Exception ex) {
        }
        return false;
    }

    /* ---------- Refresh lists methods ----------
    
    These methods will ask the database for the latest lists of various objects.
    Some of the methods have additional optional parameters such as starting date
    and end date.
    
    The list requested by the user will then be added as a session attribute
    
     */
    private void refreshDoctorsAndNurses() {
        doctors = new ArrayList<>();
        nurses = new ArrayList<>();

        try {
            temps = database.getAllFromDatabase("doctors");
            temps.remove(0);

            temps.forEach((doctor) -> {
                doctors.add((Doctor) doctor);
            });

            temps = database.getAllFromDatabase("nurses");
            temps.remove(0);

            temps.forEach((nurse) -> {
                nurses.add((Nurse) nurse);
            });

            session.setAttribute("doctors", doctors);
            session.setAttribute("nurses", nurses);

        } catch (Exception ex) {

        }

    }

    private void refreshConsultations() {
        try {

            fromDate = Date.valueOf(request.getParameter("fromDate"));
            toDate = Date.valueOf(request.getParameter("toDate"));

            consultations = database.getAllConsultationsWhereIDIsFromTo(currentUserID, fromDate, toDate);
            session.setAttribute("consultations", consultations);

        } catch (Exception e) {
            consultations = database.getAllConsultationsWhereIDIs(currentUserID);
        }

        session.setAttribute("consultations", consultations);

    }

    private void refreshInvoices() {

        try {
            if (!isAdmin) {
                invoices = database.getAllInvoicesWhereIDIs(currentUserID);
            } else {
                turnoverPaid = 0;
                turnoverUnpaid = 0;

                try {
                    fromDate = Date.valueOf(request.getParameter("fromDate"));
                    toDate = Date.valueOf(request.getParameter("toDate"));

                    invoices = database.getAllInvoicesFromTo(fromDate, toDate);

                } catch (Exception ex) {
                    invoices = database.getAllInvoices();
                }

                invoices.forEach((thisInvoice) -> {
                    if (thisInvoice.isPaid()) {
                        turnoverPaid += thisInvoice.getPrice();
                    } else {
                        turnoverUnpaid += thisInvoice.getPrice();
                    }
                });

                rounded = new BigDecimal(turnoverPaid);
                rounded = rounded.setScale(2, BigDecimal.ROUND_HALF_UP);
                session.setAttribute("turnoverPaid", rounded);

                rounded = new BigDecimal(turnoverUnpaid);
                rounded = rounded.setScale(2, BigDecimal.ROUND_HALF_UP);

                session.setAttribute("turnoverUnpaid", rounded);

            }

        } catch (Exception ex) {
            message = ex.toString();
        }

        session.setAttribute("invoices", invoices);

    }

    private void refreshPrescriptions() {

        try {
            prescriptions = database.getAllPrescriptionsWhereIDIs(currentUserID);

            if (prescriptions.size() == 0 && isPatient) {
                message = "You have no existing prescriptions to extend";
            }

        } catch (Exception ex) {
            message = ex.toString();
        }

        session.setAttribute("prescriptions", prescriptions);

    }

    private void refreshSurgeries() {

        try {

            fromDate = Date.valueOf(request.getParameter("fromDate"));
            toDate = Date.valueOf(request.getParameter("toDate"));

            surgeries = database.getAllSurgeriesWhereIDIsFromTo(currentUserID, fromDate, toDate);

        } catch (Exception e) {
            surgeries = database.getAllSurgeriesWhereIDIs(currentUserID);
        }

        session.setAttribute("surgeries", surgeries);

    }

    private void refreshPatientTable() {
        try {
            choice = Integer.parseInt(request.getParameterValues("insuranceSelection")[0]);

            switch (choice) {
                case 0:
                    patients = database.getAllPatientsWhereIs("insured", "true");
                    break;
                case 1:
                    patients = database.getAllPatientsWhereIs("insured", "false");
                    break;
                default:
                    patients = database.getAllPatients();
            }

            session.setAttribute("insuranceSelection", null);

        } catch (Exception ex) {
            patients = database.getAllPatients();
        }

        session.setAttribute("patients", patients);
    }

    private void refreshTimeTable() {
        try {
            choice = Integer.parseInt(request.getParameterValues("timeTableSelection")[0]);

            switch (choice) {
                case 0:
                    consultations = database.getAllConsultationsWhereIDIs(currentUserID);
                    session.setAttribute("consultations", consultations);
                    break;
                case 1:

                    surgeries = database.getAllSurgeriesWhereIDIs(currentUserID);
                    session.setAttribute("surgeries", surgeries);

                    break;
            }

            session.setAttribute("selectedTimeTable", choice);

        } catch (Exception ex) {
            consultations = database.getAllConsultationsWhereIDIs(currentUserID);
            session.setAttribute("consultations", consultations);
        }

    }

    private void refreshAllPatients() {
        patients = database.getAllPatients();
        session.setAttribute("patients", patients);
    }


    /*
    
    A request in this context means adding a new object to the database with 
    "pending" set to true.
    There are three types of requests that can be made: Consultation, surgery
    and prescription requests.
    Due to their similarity they are all handled by the same method.
    
     */
    private void makeRequest() {

        try {
            selectedConsultatantID = Integer.parseInt(request.getParameterValues("selectedConsultatantID")[0]);

            requestType = (Integer) session.getAttribute("requestType");

            // Only patients make requests
            patient = (Patient) currentUser;

            if (database.isDoctor(selectedConsultatantID)) {
                doctor = database.getDoctor(selectedConsultatantID);

                // If the request is a consultation the empty nurse should be added
                if (requestType == 0) {
                    nurse = database.getNurse(30000);
                }
                // If the selectedConsultant is a nurse, doctor should be
                // set to empty
            } else {
                doctor = database.getDoctor(20000);
                nurse = database.getNurse(selectedConsultatantID);
            }

            // Generate consultation or surgery object
            if (requestType != 2) {

                selectedTime = request.getParameter("selectedTime").split(":");
                selectedDate = request.getParameter("selectedDate").split("-");
                selectedYear = Integer.parseInt(selectedDate[0]);
                selectedMonth = Integer.parseInt(selectedDate[1]);
                selectedDayOfMonth = Integer.parseInt(selectedDate[2]);
                selectedHour = Integer.parseInt(selectedTime[0]);
                selectedMinute = Integer.parseInt(selectedTime[1]);

                timestamp = new Timestamp(selectedYear, selectedMonth, selectedDayOfMonth, selectedHour, selectedMinute, 0, 0);

                // If consultation add optional note
                if (requestType == 0) {
                    if (request.getParameter("note") != null) {
                        note = request.getParameter("note");
                    } else {
                        note = "";
                    }

                    consultation = new Consultation(patient, doctor, nurse, timestamp, note, 10);
                    database.addObjectToDatabase(consultation);
                    message = "Consultation appointment request sent";
                } else {
                    surgery = new Surgery(patient, doctor, timestamp, 10);
                    database.addObjectToDatabase(surgery);
                    message = "Surgery appointment request sent";
                }

                // Requested object is a prescription
            } else {
                thisID = Integer.parseInt(request.getParameterValues("selectedPrescription")[0]);
                prescription = database.getPrescription(thisID);

                prescription.setDoctor(doctor);

                database.addObjectToDatabase(prescription);

                database.setPending(thisID, true);
                message = "Prescription extension request sent";

            }

        } catch (Exception ex) {
        }
    }

    /*  ---------- Add new object methods ----------
    Adds various objects to the database
     */
    private boolean addNewPrescription() {
        try {
            if (isDoctor) {
                thisID = Integer.parseInt(request.getParameterValues("patientSelection")[0]);

                doctor = (Doctor) currentUser;

                patient = database.getPatient(thisID);

                expirationDate = Date.valueOf(request.getParameter("expirationDate"));

                medication = String.valueOf(request.getParameter("medication"));

                prescription = new Prescription(patient, doctor, medication, expirationDate);

                thisID = database.addObjectToDatabase(prescription);

                database.setPending(thisID, false);

                message = "The new prescription has been sent to the patient";

                return true;
            }

        } catch (Exception ex) {
        }
        return false;
    }

    private void issueInvoice() {

        try {
            thisID = Integer.parseInt(request.getParameterValues("appointmentSelection")[0]);

            if (database.isConsultation(thisID)) {
                consultation = database.getConsultation(thisID);
                patient = consultation.getPatient();

                if (consultation.getNurse().getNurseID() == 30000) {
                    price = database.getPrice("consultation") / 60 * consultation.getDuration();
                } else {
                    price = database.getPrice("consultation_nurse") / 60 * consultation.getDuration();
                }

                invoice = new Invoice(patient, price,
                        Date.valueOf(DateFormatter.formatDate(java.util.Date.from(Instant.now()), "yyyy-MM-dd")),
                        false, patient.isInsured());
            } else {
                surgery = database.getSurgery(thisID);
                patient = surgery.getPatient();

                price = database.getPrice("surgery") / 60 * surgery.getDuration();

                invoice = new Invoice(patient, price,
                        Date.valueOf(DateFormatter.formatDate(java.util.Date.from(Instant.now()), "yyyy-MM-dd")),
                        false, patient.isInsured());
            }

            database.addObjectToDatabase(invoice);

            removeAppointment(thisID);
        } catch (Exception ex) {

        }

    }

    private void payInvoice() {
        try {
            thisID = Integer.parseInt(request.getParameterValues("invoiceSelection")[0]);
            invoice = database.getInvoice(thisID);

            invoice.setPaid(true);

            database.addObjectToDatabase(invoice);

            if (invoice.isInsured()) {
                message = "Invoice paid by the NHS";
            } else {
                message = "Invoice settled from your bank account";
            }

        } catch (Exception ex) {
            message = ex.toString();
        }

    }

    private boolean removeAppointment() {
        try {

            thisID = Integer.parseInt(request.getParameterValues("removeAppointmentID")[0]);

            database.deleteObjectFromDatabase(thisID);

        } catch (Exception e) {

        }

        return false;
    }

    private boolean removeAppointment(int id) {
        try {

            database.deleteObjectFromDatabase(id);

        } catch (Exception e) {

        }

        return false;
    }

    /*  ---------- Set single attribute methods ----------   */
    private void setRequestType() {
        try {
            resetRequestType = Boolean.parseBoolean(request.getParameter("resetBookingType"));

            if (resetRequestType) {
                session.setAttribute("requestType", null);
                return;
            }

            requestType = Integer.parseInt(request.getParameterValues("requestTypeSelection")[0]);

            session.setAttribute("requestType", requestType);

            if (requestType == 2) {
                refreshPrescriptions();
            }

        } catch (Exception ex) {

        }

    }

    private void setPrice() {
        try {

            if (session.getAttribute("selectedPrice") == null) {
                choice = Integer.parseInt(request.getParameterValues("priceSelection")[0]);
            } else {
                choice = (Integer) session.getAttribute("selectedPrice");
            }

            switch (choice) {
                case 0:
                    setPriceFor = "doctor consultations";
                    break;
                case 1:
                    setPriceFor = "nurse consultations";
                    break;
                case 2:
                    setPriceFor = "surgeries";
                    break;
            }

            try {
                newPrice = Double.parseDouble(request.getParameter("newPrice"));

                switch (choice) {
                    case 0:
                        of = "consultation";
                        break;
                    case 1:
                        of = "consultation_nurse";
                        break;
                    case 2:
                        of = "surgery";
                        break;
                }

                database.setPrice(of, newPrice);

            } catch (Exception ex) {

            }
            session.setAttribute("setPriceFor", setPriceFor);
            session.setAttribute("selectedPrice", choice);

        } catch (Exception ex) {

        }
    }

    /*  ---------- Admin only methods ---------- 
    
    These methods allow an Admin to remove users and impersonate other users
    temporarily in order to show and modify their time tables
    
     */
    private void removeUser() {
        try {

            thisID = Integer.valueOf(request.getParameterValues("removeUserSelection")[0]);

            database.deleteObjectFromDatabase(thisID);

            if (database.isDoctor(thisID)) {
                refreshDoctorsAndNurses();
            } else {
                refreshAllPatients();
            }
        } catch (Exception ex) {

        }

    }

    // Impersonate another user
    private boolean adminSetUser() {
        success = false;

        try {
            // Save the previous admin ID and User
            previousUserID = currentUserID;
            previousUser = currentUser;

            try {
                currentUserID = (Integer) session.getAttribute("adminSelectedID");

                newUserID = Integer.valueOf(request.getParameterValues("userSelection")[0]);

                // Only switch if admin selected a new user
                if (currentUserID != newUserID) {
                    currentUserID = newUserID;

                    session.setAttribute("isDoctor", null);
                    session.setAttribute("isNurse", null);
                    session.setAttribute("isPatient", null);

                    if (database.isDoctor(currentUserID)) {
                        session.setAttribute("isDoctor", "1");
                    } else if (database.isNurse(currentUserID)) {
                        session.setAttribute("isNurse", "1");
                    } else if (database.isPatient(currentUserID)) {
                        session.setAttribute("isPatient", "1");
                    }

                }
                success = true;
            } catch (Exception ex) {
                currentUserID = Integer.valueOf(request.getParameterValues("userSelection")[0]);
                success = true;
                session.setAttribute("adminSelectedID", currentUserID);

            }

            if (database.isDoctor(currentUserID)) {
                currentUser = database.getDoctor(currentUserID);
                session.setAttribute("isDoctor", "1");
            } else if (database.isNurse(currentUserID)) {
                currentUser = database.getNurse(currentUserID);
                session.setAttribute("isNurse", "1");
            } else if (database.isPatient(currentUserID)) {
                currentUser = database.getPatient(currentUserID);
                session.setAttribute("isPatient", "1");
            }
            session.setAttribute("adminSelectedUser", currentUser);

        } catch (Exception ex) {

        }
        return success;
    }

    // Allows the admin to change the prices
    private void setPricesFromDatabase() {
        consultationPrice = database.getPrice("consultation");
        consultationPriceNurse = database.getPrice("consultation_nurse");
        surgeryPrice = database.getPrice("surgery");

        session.setAttribute("consultationPrice", consultationPrice);
        session.setAttribute("consultationPriceNurse", consultationPriceNurse);
        session.setAttribute("surgeryPrice", surgeryPrice);
    }

    // Restore to Admin user
    private void adminRevertUser() {

        currentUserID = previousUserID;
        currentUser = previousUser;
    }

    // This method is called when a user logs in. Only the data required for the
    // users dashboard will be refreshed
    protected void initialise(int currentUserID, HttpSession session, HttpServletRequest request) {
        database = (Database) request.getServletContext().getAttribute("database");
        this.request = request;
        this.session = session;
        this.currentUserID = currentUserID;
        selectedTimetable = 0;
        setBooleans();

        if (!isAdmin) {
            refreshConsultations();
        } else {
            // Admins will start with an empty timetable
            consultations = new ArrayList<>();
            session.setAttribute("consultations", consultations);
        }

        // Any employee will have a list of all patients
        if (isEmployee) {
            setPricesFromDatabase();
            refreshAllPatients();
        }

        // Admins and patients can see lists of doctors and nurses
        if (isAdmin || isPatient) {
            refreshDoctorsAndNurses();
            refreshInvoices();

            // Admins require the list of pending new employees
            if (isAdmin) {
                currentUser = database.getAdmin(currentUserID);
                loggedInAs = "n admin";

                setPendingEmployees();
            } else {
                currentUser = database.getPatient(currentUserID);
                loggedInAs = " patient";
            }
        }

        // Any doctor or nurse may have pending consultation requests
        if (isDoctor || isNurse) {
            setPendingConsultations();

            // Only doctors may have surgeries, pending or otherwise
            if (isDoctor) {
                currentUser = database.getDoctor(currentUserID);
                setPendingSurgeries();
                setPendingPrescriptions();
                loggedInAs = " doctor";
            } else if (isNurse) {
                currentUser = database.getNurse(currentUserID);
                loggedInAs = " nurse";
            }
        }

        session.setAttribute("selectedTimeTable", selectedTimetable);
        session.setAttribute("currentUser", currentUser);
        session.setAttribute("loggedInAs", loggedInAs);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        session = request.getSession(false);
        database = (Database) request.getServletContext().getAttribute("database");
        currentUserID = (Integer) session.getAttribute("userID");
        currentUser = (User) session.getAttribute("currentUser");
        jspContext = String.valueOf(request.getParameter("jspName"));
        this.request = request;

        // Setup local booleans from session attributes
        setBooleans();

        message = "";

        session.setAttribute("message1", "");
        session.setAttribute("message2", "");
        session.setAttribute("message3", "");

        /* --------- jspContext callers ----------
        
        The if else statement below determines which methods to call based on 
        the name of the originating .jsp. This ensures only the data required is
        refreshed.
        All non-public .jsps can be found in the /objects folder
        
         */
        if (jspContext.contains("timetable")) {
            // The admin can impersonate another user to show their timetable
            if (isAdmin) {
                adminSetUser();
            }

            try {
                refreshTimeTable();
                refreshConsultations();

                // Nurses don't need to refresh surgeries
                if (!database.isNurse(currentUserID)) {
                    refreshSurgeries();
                }
            } catch (NullPointerException ex) {

            }

            // Admin will need to revert to their own user again
            if (isAdmin) {
                adminRevertUser();
            }

        } else if (jspContext.contains("pendingConfirmer")) {

            approvePendingConsultation();

            setPendingConsultations();
            refreshConsultations();

            if (!isNurse) {
                approvePendingSurgery();
                approvePendingPrescription();
                
                setPendingSurgeries();
                refreshSurgeries();
                setPendingPrescriptions();
                refreshPrescriptions();
            }

        } else if (jspContext.contains("patientTable")) {
            refreshPatientTable();

        } else if (jspContext.contains("requester")) {
            setRequestType();
            makeRequest();

            session.setAttribute("message1", message);

        } else if (jspContext.contains("pendingEmployees")) {
            approveEmployee();
            setPendingEmployees();

        } else if (jspContext.contains("invoiceIssuer")) {
            issueInvoice();
            refreshConsultations();

        } else if (jspContext.contains("invoicePayer")) {
            payInvoice();
            refreshInvoices();

            session.setAttribute("message2", message);

        } else if (jspContext.contains("prescriptionIssuer")) {
            addNewPrescription();
            session.setAttribute("message3", message);

        } else if (jspContext.contains("turnoverCalculator")) {
            refreshInvoices();

        } else if (jspContext.contains("priceSetter")) {
            setPrice();
            setPricesFromDatabase();

        } else if (jspContext.contains("userRemover")) {
            removeUser();

        } else if (jspContext.contains("appointmentRemover")) {

            if (isAdmin) {
                adminSetUser();
            }

            try {
                removeAppointment();
                refreshConsultations();

                if (!database.isNurse(currentUserID)) {
                    refreshSurgeries();
                }
            } catch (NullPointerException ex) {

            }
            if (isAdmin) {
                adminRevertUser();
            }

        }

        // After the controller logic is complete, redirect to the user's dashboard
        if (isAdmin) {
            viewString = "/protected/adminDashboard.do";
        } else if (isEmployee) {
            viewString = "/protected/employeeDashboard.do";
        } else if (isPatient) {
            viewString = "/protected/patientDashboard.do";
        }

        response.sendRedirect(request.getContextPath() + viewString);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
