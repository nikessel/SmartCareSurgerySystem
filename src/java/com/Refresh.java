/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author niklas
 */
public class Refresh extends HttpServlet {

    int currentUserID, selectedConsultatantID, selectedHour, selectedMinute,
            selectedYear, selectedMonth, selectedDayOfMonth, checkID, selectedTimetable,
            requestType;

    double price;

    boolean isEmployee, isAdmin, isDoctor, isNurse, isPatient, deleteConsultation, issueInvoice;
    HttpSession session;
    HttpServletRequest request;
    Cookie cookie;
    Cookie[] cookies;
    Database database;
    String loggedInAs, jspContext, viewString;
    RequestDispatcher view;
    User currentUser;
    List<Consultation> consultations;
    List<Patient> patients;
    String message, note, medication;
    Date fromDate, toDate;
    ArrayList<Employee> pendingEmployees;
    ArrayList<Integer> pendingEmployeeIDs, pendingConsultationIDs, pendingSurgeryIDs, pendingPrescriptionIDs;
    ArrayList<Consultation> pendingConsultations;
    ArrayList<Prescription> pendingPrescriptions;
    ArrayList<Surgery> pendingSurgeries;
    ArrayList<Invoice> invoices;
    ArrayList<Object> temps;
    ArrayList<Surgery> surgeries;
    ArrayList<Prescription> prescriptions;
    Object temp;
    Invoice invoice;
    Patient patient;
    Surgery surgery;
    Doctor doctor;
    Nurse nurse;
    Prescription prescription;
    Consultation consultation;
    ArrayList<Doctor> doctors;
    ArrayList<Nurse> nurses;
    Timestamp timestamp;
    String[] selectedDate, selectedTime;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    private void setPendingConsultations() {
        pendingConsultationIDs = database.getPending("consultations");
        pendingConsultations = new ArrayList<>();
        Consultation tempConsultation;

        try {

            for (int id : pendingConsultationIDs) {
                tempConsultation = database.getConsultation(id);

                if (database.isDoctor(currentUserID)) {
                    checkID = tempConsultation.getDoctor().getDoctorID();
                } else {
                    checkID = tempConsultation.getNurse().getNurseID();
                }

                if (checkID == currentUserID) {
                    pendingConsultations.add(tempConsultation);
                }
            }
            session.setAttribute("pendingConsultations", pendingConsultations);
        } catch (Exception ex) {

        }

    }

    private void setPendingSurgeries() {
        pendingSurgeryIDs = database.getPending("surgeries");
        pendingSurgeries = new ArrayList<>();
        Surgery tempSurgery;

        try {

            for (int id : pendingSurgeryIDs) {
                tempSurgery = database.getSurgery(id);

                checkID = tempSurgery.getDoctor().getDoctorID();

                if (checkID == currentUserID) {
                    pendingSurgeries.add(tempSurgery);
                }
            }
            session.setAttribute("pendingSurgeries", pendingSurgeries);
        } catch (Exception ex) {
            message = ex.toString();
        }

    }

    private void setPendingPrescriptions() {
        pendingPrescriptionIDs = database.getPending("prescriptions");
        pendingPrescriptions = new ArrayList<>();
        Prescription tempPrescription;

        try {

            for (int id : pendingPrescriptionIDs) {
                tempPrescription = database.getPrescription(id);

                checkID = tempPrescription.getDoctor().getDoctorID();

                if (checkID == currentUserID) {
                    pendingPrescriptions.add(tempPrescription);
                }
            }
            session.setAttribute("pendingPrescriptions", pendingPrescriptions);
        } catch (Exception ex) {
            message = ex.toString();
        }

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

    private void approveEmployee() {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingEmployeeSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approve"));

            if (approve) {
                database.approve(id);

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (Exception ex) {
            message = "";
        }
    }

    private void setDoctorsAndNurses() {
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

    private Boolean makeRequest() {

        try {
            selectedConsultatantID = Integer.parseInt(request.getParameterValues("selectedConsultatantID")[0]);

            requestType = (Integer) session.getAttribute("requestType");

            patient = (Patient) currentUser;

            if (database.isDoctor(selectedConsultatantID)) {
                doctor = database.getDoctor(selectedConsultatantID);

                if (requestType == 0) {
                    nurse = database.getNurse(30000);
                }
            } else {
                doctor = database.getDoctor(20000);
                nurse = database.getNurse(selectedConsultatantID);
            }

            message = String.valueOf(requestType);

            if (requestType != 2) {

                selectedTime = request.getParameter("selectedTime").split(":");
                selectedDate = request.getParameter("selectedDate").split("-");
                selectedYear = Integer.parseInt(selectedDate[0]);
                selectedMonth = Integer.parseInt(selectedDate[1]);
                selectedDayOfMonth = Integer.parseInt(selectedDate[2]);
                selectedHour = Integer.parseInt(selectedTime[0]);
                selectedMinute = Integer.parseInt(selectedTime[1]);

                timestamp = new Timestamp(selectedYear, selectedMonth, selectedDayOfMonth, selectedHour, selectedMinute, 0, 0);

                if (requestType == 0) {
                    if (request.getParameter("note") != null) {
                        note = request.getParameter("note");
                    } else {
                        note = "";
                    }

                    consultation = new Consultation(patient, doctor, nurse, timestamp, note, 10);
                    database.addObjectToDatabase(consultation);
                } else {
                    surgery = new Surgery(patient, doctor, timestamp, 10);
                    database.addObjectToDatabase(surgery);
                }
            } else {
                int prescriptionID = Integer.parseInt(request.getParameterValues("selectedPrescription")[0]);
                prescription = database.getPrescription(prescriptionID);

                prescription.setDoctor(doctor);

                database.addObjectToDatabase(prescription);

                database.setPending(prescriptionID);

                message = prescription.toString();
            }

        } catch (Exception ex) {
        }
        return false;
    }

    private boolean approvePendingConsultation() {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingConsultationSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approveConsultation"));

            if (approve) {
                database.approve(id);
                return true;

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (Exception ex) {
        }
        return false;
    }

    private boolean approvePendingSurgery() {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingSurgerySelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approveSurgery"));

            if (approve) {
                database.approve(id);
                return true;

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (Exception ex) {
        }
        return false;
    }

    private boolean approvePendingPrescription() {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingPrescriptionSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approvePrescription"));

            if (approve) {
                database.approve(id);

                Date expirationDate = Date.valueOf(request.getParameter("newPrescriptionDate"));

                prescription = database.getPrescription(id);
                
                prescription.setExpirationDate(expirationDate);
                
                database.addObjectToDatabase(prescription);

                return true;

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (Exception ex) {
        }
        return false;
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

    }

    private void issueInvoice() {

        try {
            int id = Integer.parseInt(request.getParameterValues("consultationSelection")[0]);
            consultation = database.getConsultation(id);
            patient = consultation.getPatient();
            price = database.getPrice("consultation") / 60 * consultation.getDuration();

            invoice = new Invoice(patient, price,
                    Date.valueOf(DateFormatter.formatDate(java.util.Date.from(Instant.now()), "yyyy-MM-dd")),
                    false, patient.isInsured());

            database.addObjectToDatabase(invoice);

            removeConsultation();
        } catch (Exception ex) {
            message = ex.toString();
        }

    }

    private void refreshInvoices() {

        try {
            invoices = database.getAllInvoicesWhereIDIs(currentUserID);

        } catch (Exception ex) {
            message = ex.toString();
        }

        session.setAttribute("invoices", invoices);

    }

    private void refreshPrescriptions() {

        try {
            prescriptions = database.getAllPrescriptionsWhereIDIs(currentUserID);

        } catch (Exception ex) {
            message = ex.toString();
        }

        session.setAttribute("prescriptions", prescriptions);

    }

    private void refreshSurgeries() {

        try {
            surgeries = database.getAllSurgeriesWhereIDIs(currentUserID);

            session.setAttribute("surgeries", surgeries);
        } catch (Exception ex) {
            message = ex.toString();
        }

    }

    private void payInvoice() {
        try {
            int id = Integer.parseInt(request.getParameterValues("invoiceSelection")[0]);
            invoice = database.getInvoice(id);

            invoice.setPaid(true);

            database.addObjectToDatabase(invoice);

            message = invoice.toString();
        } catch (Exception ex) {
            message = ex.toString();
        }

    }

    private boolean removeConsultation() {
        try {

            int id = Integer.parseInt(request.getParameterValues("consultationSelection")[0]);

            database.deleteObjectFromDatabase(id);

        } catch (Exception e) {

        }

        return false;
    }

    private void setPatientTable() {
        try {
            int choice = Integer.parseInt(request.getParameterValues("insuranceSelection")[0]);

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

        } catch (NumberFormatException ex) {
            patients = database.getAllPatients();
        }
    }

    private void setRequestType() {
        try {
            boolean resetBookingType = Boolean.parseBoolean(request.getParameter("resetBookingType"));

            if (resetBookingType) {
                session.setAttribute("requestType", null);
                return;
            }

            requestType = Integer.parseInt(request.getParameterValues("requestTypeSelection")[0]);

            session.setAttribute("requestType", requestType);

        } catch (Exception ex) {
            // message = ex.toString();

        }

    }

    private void setTimeTable() {
        try {
            int choice = Integer.parseInt(request.getParameterValues("timeTableSelection")[0]);

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
            surgeries = database.getAllSurgeriesWhereIDIs(currentUserID);
            session.setAttribute("surgeries", surgeries);
            session.setAttribute("consultations", consultations);
        }

    }

    protected void initialise(int currentUserID, HttpSession session, HttpServletRequest request) {
        database = (Database) request.getServletContext().getAttribute("database");
        this.request = request;
        this.session = session;
        this.currentUserID = currentUserID;
        selectedTimetable = 0;
        setBooleans();

        if (!isAdmin) {
            consultations = database.getAllConsultationsWhereIDIs(currentUserID);
            session.setAttribute("consultations", consultations);
        }

        if (isEmployee) {
            patients = database.getAllPatients();
            session.setAttribute("patients", patients);
        }

        if (isAdmin || isPatient) {
            setDoctorsAndNurses();

            if (isAdmin) {
                currentUser = database.getAdmin(currentUserID);
                loggedInAs = "n admin";
                setPendingEmployees();
            } else {
                currentUser = database.getPatient(currentUserID);
                refreshInvoices();
                refreshPrescriptions();
                loggedInAs = " patient";
            }
        }

        if (isDoctor || isNurse) {
            setPendingConsultations();

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

        setBooleans();

        if (jspContext.contains("timetable")) {
            setTimeTable();

        } else if (jspContext.contains("pendingConfirmer")) {

            if (approvePendingConsultation()) {
                refreshConsultations();
                setPendingConsultations();

            }

            if (approvePendingSurgery()) {
                refreshSurgeries();
                setPendingSurgeries();

            }

            if (approvePendingPrescription()) {
                refreshPrescriptions();
                setPendingPrescriptions();

            }

        } else if (jspContext.contains("patientTable")) {
            setPatientTable();

            session.setAttribute("patients", patients);

        } else if (jspContext.contains("requester")) {
            setRequestType();
            makeRequest();

        } else if (jspContext.contains("pendingEmployees")) {
            approveEmployee();
            setPendingEmployees();
        } else if (jspContext.contains("invoiceIssuer")) {
            issueInvoice();
            refreshConsultations();
        } else if (jspContext.contains("invoicePayer")) {
            payInvoice();
            refreshInvoices();

        }

        session.setAttribute("filterMessage", message);

        if (isEmployee) {
            viewString = "/protected/employeeDashboard.do";
        } else if (isAdmin) {
            viewString = "/protected/adminDashboard.do";
        } else if (isPatient) {
            viewString = "/protected/patientDashboard.do";
        }

        response.sendRedirect(request.getContextPath() + viewString);

        //view = getServletContext().getRequestDispatcher(viewString);
        //view.forward(request, response);
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
