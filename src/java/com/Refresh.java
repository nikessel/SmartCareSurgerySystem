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
import model.Consultation;
import model.Database;
import model.Doctor;
import model.Employee;
import model.Invoice;
import model.Nurse;
import model.Patient;
import model.User;

/**
 *
 * @author niklas
 */
public class Refresh extends HttpServlet {

    int currentUserID, selectedConsultatantID, selectedHour, selectedMinute,
            selectedYear, selectedMonth, selectedDayOfMonth, checkID;

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
    String message, note;
    Date fromDate, toDate;
    ArrayList<Employee> pendingEmployees;
    ArrayList<Integer> pendingEmployeeIDs;
    ArrayList<Consultation> pendingConsultations;
    ArrayList<Integer> pendingConsultationIDs;
    ArrayList<Invoice> invoices;
    ArrayList<Object> temps;
    Object temp;
    Invoice invoice;
    Patient patient;
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
        pendingConsultationIDs = database.getPendingConsultations();
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

        } catch (Exception ex) {

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

    }

    private void approveEmployee() {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingEmployeeSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approve"));

            if (approve) {
                database.approveEmployee(id);

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
        } catch (Exception ex) {

        }
    }

    private Boolean requestConsultation() {

        try {
            selectedTime = request.getParameter("selectedTime").split(":");
            selectedDate = request.getParameter("selectedDate").split("-");
            selectedConsultatantID = Integer.parseInt(request.getParameterValues("selectedConsultatantID")[0]);

            if (request.getParameter("note") != null) {
                note = request.getParameter("note");
            } else {
                note = "";
            }

            selectedYear = Integer.parseInt(selectedDate[0]);
            selectedMonth = Integer.parseInt(selectedDate[1]);
            selectedDayOfMonth = Integer.parseInt(selectedDate[2]);
            selectedHour = Integer.parseInt(selectedTime[0]);
            selectedMinute = Integer.parseInt(selectedTime[1]);

            timestamp = new Timestamp(selectedYear, selectedMonth, selectedDayOfMonth, selectedHour, selectedMinute, 0, 0);

            Doctor tempDoctor;
            Nurse tempNurse;

            if (database.isDoctor(selectedConsultatantID)) {
                tempDoctor = database.getDoctor(selectedConsultatantID);
                tempNurse = database.getNurse(30000);
            } else {
                tempDoctor = database.getDoctor(20000);
                tempNurse = database.getNurse(selectedConsultatantID);
            }

            Consultation consultation = new Consultation((Patient) currentUser, tempDoctor, tempNurse, timestamp, note, 10);

            database.addObjectToDatabase(consultation);

        } catch (NumberFormatException ex) {
        }
        return false;
    }

    private boolean approvePendingConsultation() {
        try {
            int id = Integer.parseInt(request.getParameterValues("pendingConsultationSelection")[0]);
            boolean approve = Boolean.parseBoolean(request.getParameter("approve"));

            if (approve) {
                database.approveConsultation(id);
                return true;

            } else {
                database.deleteObjectFromDatabase(id);
            }

        } catch (NumberFormatException ex) {
        }
        return false;
    }

    private void refreshConsultations() {
        try {

            fromDate = Date.valueOf(request.getParameter("fromDate"));
            toDate = Date.valueOf(request.getParameter("toDate"));

            consultations = database.getAllConsultationsWhereIDIsFromTo(currentUserID, fromDate, toDate);

        } catch (Exception e) {
            consultations = database.getAllConsultationsWhereIDIs(currentUserID);
        }
        session.setAttribute("consultations", consultations);

    }

    private void setInvoice() {

        try {
            int id = Integer.parseInt(request.getParameterValues("consultationSelection")[0]);
            consultation = database.getConsultation(id);
            patient = consultation.getPatient();
            price = database.getPrice("consultation") / 60 * consultation.getDuration();

            invoice = new Invoice(consultation, patient, price,
                    Date.valueOf(DateFormatter.formatDate(java.util.Date.from(Instant.now()), "yyyy-MM-dd")),
                    false, consultation.getPatient().isInsured());

            database.addObjectToDatabase(invoice);

            message = invoice.toString();
        } catch (Exception ex) {
            message = ex.toString();
        }

    }

    private void refreshInvoices() {

        try {
            invoices = database.getAllInvoicesWhereIDIs(currentUserID);
            
            session.setAttribute("invoices", invoices);
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

    protected void initialise(int currentUserID, HttpSession session, HttpServletRequest request) {
        database = (Database) request.getServletContext().getAttribute("database");
        this.request = request;
        this.session = session;
        this.currentUserID = currentUserID;
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
            session.setAttribute("doctors", doctors);
            session.setAttribute("nurses", nurses);

            if (isAdmin) {
                currentUser = database.getAdmin(currentUserID);
                loggedInAs = "n admin";
                setPendingEmployees();
                session.setAttribute("pendingEmployees", pendingEmployees);
            } else {
                currentUser = database.getPatient(currentUserID);
                refreshInvoices();
                loggedInAs = " patient";
            }
        }

        if (isDoctor || isNurse) {
            setPendingConsultations();
            session.setAttribute("pendingConsultations", pendingConsultations);

            if (isDoctor) {
                currentUser = database.getDoctor(currentUserID);
                loggedInAs = " doctor";
            } else if (isNurse) {
                currentUser = database.getNurse(currentUserID);
                loggedInAs = " nurse";
            }
        }

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
            refreshConsultations();

        } else if (jspContext.contains("pendingConsultations")) {

            if (approvePendingConsultation()) {
                refreshConsultations();
            }

            setPendingConsultations();

            session.setAttribute("pendingConsultations", pendingConsultations);

        } else if (jspContext.contains("patientTable")) {
            setPatientTable();

            session.setAttribute("patients", patients);

        } else if (jspContext.contains("bookConsultation")) {
            requestConsultation();
        } else if (jspContext.contains("pendingEmployees")) {
            approveEmployee();
            setPendingEmployees();
            session.setAttribute("pendingEmployees", pendingEmployees);
        } else if (jspContext.contains("invoiceIssuer")) {
            setInvoice();
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
