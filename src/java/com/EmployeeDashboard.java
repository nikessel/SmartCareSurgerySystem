/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author niklas
 */
import model.*;

@WebServlet("/employeeDashboard")

public class EmployeeDashboard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String userName = "";
        HttpSession session = request.getSession();

        Cookie cookie = new Cookie("username", (String) session.getAttribute("username"));
        cookie.setMaxAge(20 * 60);
        response.addCookie(cookie);

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie thisCookie : cookies) {
                if (thisCookie.getName().equals("username")) {
                    userName = thisCookie.getValue();
                    break;
                }
            }
        }

        String sessionString = "";

        Database database = (Database) getServletContext().getAttribute("database");

        RequestDispatcher view = getServletContext().getRequestDispatcher("/employeeDashboard.jsp");

        int currentUserID = (int) session.getAttribute("userID");
        
        User currentUser;
        
        if (20000 <= currentUserID && currentUserID <= 29999) {
            currentUser = database.getDoctor(currentUserID);
        } else {
            currentUser = database.getNurse(currentUserID);
        }

        List<Consultation> consultations = database.getAllConsultationsWhereIDIs(currentUserID);

        synchronized (session) {
            session.setAttribute("consultations", consultations);
            session.setAttribute("username", userName);
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("sessionMessage", sessionString);
        }

        response.sendRedirect(request.getContextPath() + "/employeeDashboard.jsp");
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
