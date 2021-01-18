/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Database;

/**
 *
 * @author niklas
 */
public class AuthenticationFilter implements Filter {

    private FilterConfig fc;
    private String errorMessage;
    private int currentUserID;
    private boolean isPending;
    private HttpSession session;
    private Database database;

    private boolean wrongUsername() {
        return currentUserID == -2;
    }

    private boolean wrongPassword() {
        return currentUserID == -1;
    }

    private void setBooleans() {

        if (database.isUser(currentUserID)) {
            session.setAttribute("isUser", "1");
            
            if (database.isEmployee(currentUserID)) {
                session.setAttribute("isEmployee", "1");

                if (database.isAdmin(currentUserID)) {
                    session.setAttribute("isAdmin", "1");
                } else if (database.isDoctor(currentUserID)) {
                    session.setAttribute("isDoctor", "1");
                } else if (database.isNurse(currentUserID)) {
                    session.setAttribute("isNurse", "1");
                }
            } else {
                session.setAttribute("isPatient", "1");
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setContentType("text/html;charset=UTF-8");

        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        session = httpRequest.getSession();
        database = (Database) httpRequest.getServletContext().getAttribute("database");

        errorMessage = "";

        try {
            currentUserID = database.getUserID(username, password);
            isPending = database.isUserPending(currentUserID);

            if (isPending) {
                throw new NullPointerException();
            } else {

                if (currentUserID >= 0) {
                    httpRequest.setAttribute("userID", currentUserID);
                    setBooleans();
                    chain.doFilter(httpRequest, response);
                } else {
                    throw new NullPointerException();
                }
            }

        } catch (NullPointerException e) {
            if (isPending) {
                errorMessage = "Admin approval required before this user can login";
            } else if (wrongUsername()) {
                errorMessage = "Invalid username, please try again";
            } else if (wrongPassword()) {
                errorMessage = "Invalid password, please try again";
            }

            httpRequest.getServletContext().setAttribute("message", errorMessage);
            session.setAttribute("message", errorMessage);

            RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(httpRequest, response);
        }

    }

    @Override
    public void destroy() {

    }

}
