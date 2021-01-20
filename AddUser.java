/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Address;
import model.Database;
import model.Geocoding;
import model.Patient;
import java.sql.Date;
import model.Doctor;
import model.Nurse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 * Phone number validation by Callum Gill
 *
 */
public class AddUser extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Attribute declarations
    private String username, password, repeatPassword, message, addressLine1, addressLine2,
            postcode, county, town, telephoneNumber, lookupPostcode, headline, firstName, surName;
    private Date dateOfBirth;
    private int currentUserID, streetNumber, userType;
    private boolean isFullTime, redirectToLogin, insured;
    private Address address;

    // Temporary User objects
    private Patient patient;
    private Doctor doctor;
    private Nurse nurse;

    private Database database;
    private HttpSession session;
    private HttpServletRequest request;
    private RequestDispatcher view;

    /*  This method will attempt to add a new user with the parameters given, 
        Based on whether or not it is successful, it will determine whether or 
        Not to redirect the user to the login page or forward to the addUser page */
    private boolean attemptAddUser() {
        currentUserID = 0;
        database = (Database) getServletContext().getAttribute("database");
        currentUserID = database.getUserID(username, "");

        if (currentUserID != -2) {
            message = "Username already exists, please login instead";
            redirectToLogin = true;
        } else if (!password.equals(repeatPassword)) {
            message = "The entered passwords does not match";
        } else {
            redirectToLogin = true;

            switch (userType) {
                case 1:
                    patient = new Patient(username, firstName, surName, dateOfBirth, address, insured);
                    database.addObjectToDatabase(patient);
                    database.addPasswordToUser(patient, password);
                    message = "User added succesfully, please login";
                    break;
                case 2:
                    doctor = new Doctor(username, firstName, surName, dateOfBirth, address, isFullTime);
                    database.addObjectToDatabase(doctor);
                    database.addPasswordToUser(doctor, password);
                    message = "Request to add a doctor has been forwarded to admin for approval";
                    break;
                case 3:
                    nurse = new Nurse(username, firstName, surName, dateOfBirth, address, isFullTime);
                    database.addObjectToDatabase(nurse);
                    database.addPasswordToUser(nurse, password);
                    message = "Request to add a nurse has been forwarded to admin for approval";
                    break;
                default:
                    break;
            }
            return true;
        }

        return false;
    }

    // Attempt to get attributes common for all Users
    private void getUserAttributes() {
        username = request.getParameter("username");
        password = request.getParameter("password");
        repeatPassword = request.getParameter("repeatPassword");
        firstName = request.getParameter("firstName");
        surName = request.getParameter("surName");

        dateOfBirth = Date.valueOf(request.getParameter("dateOfBirth"));
        addressLine1 = request.getParameter("streetNumber") + " " + request.getParameter("addressLine1");
        addressLine2 = request.getParameter("addressLine2");
        county = request.getParameter("county");
        town = request.getParameter("town");
        telephoneNumber = request.getParameter("telephoneNumber");
        
        address = new Address(addressLine1, addressLine2, postcode, county, town, telephoneNumber);
        
        if (validatePhoneNumber() == false){
            message = "Phone number is invalid";
        }
    }
    private boolean validatePhoneNumber(){
        Pattern mobilePattern = Pattern.compile("\\\\d{3}-\\\\d{7}");              
        Matcher matcher = mobilePattern.matcher(telephoneNumber);      
        if (matcher.matches() == false){
            Pattern telephonePattern = Pattern.compile("^(?=(?:[8-9]){1})(?=[0-9]{8}).*");
            matcher = telephonePattern.matcher(telephoneNumber);
            if (matcher.matches() == false){
                return false;
            }
        }
        return true;
    }

    // Get patient specific attribute
    private void getPatientAttributes() {
        insured = Boolean.parseBoolean(request.getParameter("insured"));
    }

    // Get employee specific attribute
    private void getEmployeeAttributes() {
        isFullTime = Boolean.parseBoolean(request.getParameter("isFullTime"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        message = "";
        redirectToLogin = false;
        session = request.getSession();
        this.request = request;
        view = getServletContext().getRequestDispatcher("/addUser.jsp");
        address = new Address("", "", "", "", "", "");

        // Attempt to determine the userType from previous user selection in session
        try {
            userType = (Integer) session.getAttribute("userType");

        } catch (Exception ex1) {
            // If not found then user has just chosen the userType, set it as
            // an attribute in the session
            try {
                userType = Integer.parseInt(request.getParameter("userType"));
                session.setAttribute("userType", String.valueOf(userType));

                switch (userType) {
                    case 1:
                        headline = "Patient";
                        break;
                    case 2:
                        headline = "Doctor";
                        break;
                    case 3:
                        headline = "Nurse";
                        break;
                }

                session.setAttribute("headline", headline);

            } catch (Exception ex2) {

            }
        }

        if (request.getParameter("lookupPostcode") != null) {
            
            // Attempt to lookup the postcode, if found, set as a session attribute
            // To autofill the relevant boxes
            if (Geocoding.validateGeocode(request.getParameter("lookupPostcode"))) {

                address = Geocoding.getAddress();
                session.setAttribute("address", address);

            }
        } else {

            // Else try to get entered user attributes and add the user to the database
            try {
                getUserAttributes();

                if (userType == 1) {
                    getPatientAttributes();
                } else {
                    getEmployeeAttributes();
                }

                attemptAddUser();

            } catch (Exception ex) {
            }
        }

        session.setAttribute("message", message);

        if (redirectToLogin) {
            session.setAttribute("userType", null);

            response.sendRedirect(request.getContextPath() + "/login.do");
        } else {
            view.forward(request, response);
        }

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
