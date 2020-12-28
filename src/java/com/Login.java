/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author Genius
 */
@WebServlet("/login")

public class Login extends HttpServlet {

    private String message;
    private int currentUserID;

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

        HttpSession session = null;
        Cookie cookie = null;

        try {
            currentUserID = (int) request.getAttribute("userID");

            // username  password validation 
            if (10000 <= currentUserID && currentUserID <= 19999) {
                session = request.getSession();

                session.setAttribute("userID", currentUserID);
                request.getRequestDispatcher("/protected/adminDashboard.do").forward(request, response);
            } else if (20000 <= currentUserID && currentUserID <= 39999) {
                session = request.getSession();
                
                session.setAttribute("userID", currentUserID);
                request.getRequestDispatcher("/protected/employeeDashboard.do").forward(request, response);
            } else if (40000 <= currentUserID && currentUserID <= 49999) {
                session = request.getSession();

                session.setAttribute("userID", currentUserID);
                request.getRequestDispatcher("/protected/patientDashboard.do").forward(request, response);
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            response.sendRedirect("/login.jsp");

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
