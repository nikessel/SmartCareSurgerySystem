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

/**
 *
 * @author niklas
 */
public class AuthorizationFilter implements Filter {

    private FilterConfig fc;
    private String message;
    private int currentUserID;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

    private boolean isAdmin() {
        return 10000 <= currentUserID && currentUserID <= 19999;
    }

    private boolean isEmployee() {
        return 20000 <= currentUserID && currentUserID <= 39999;
    }

    private boolean isPatient() {
        return 40000 <= currentUserID && currentUserID <= 49999;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        response.setContentType("text/html;charset=UTF-8");

        String[] protectedUrlPatterns = {"/protected/adminDashboard.do",
            "/protected/employeeDashboard.do", "/protected/patientDashboard.do"};

        try {
            currentUserID = (int) session.getAttribute("userID");

            if (isAdmin() && requestURI.endsWith(protectedUrlPatterns[0])) {

            } else if (isEmployee() && requestURI.endsWith(protectedUrlPatterns[1])) {

            } else if (isPatient() && requestURI.endsWith(protectedUrlPatterns[2])) {

            } else {
                throw new Exception();
            }

            chain.doFilter(httpRequest, response);

        } catch (Exception e) {

            RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(httpRequest, response);
        }
    }

    @Override
    public void destroy() {

    }

}
