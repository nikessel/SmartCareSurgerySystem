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
public class AuthorizationFilter implements Filter {

    private FilterConfig fc;
    private String errorMessage;
    private int currentUserID;
    private HttpSession session;
    private Database database;
    private boolean isUser, isAdmin, isEmployee, isPatient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
    }

    private void setBooleans() {
        isUser = isAdmin = isEmployee = isPatient = false;

        if (session.getAttribute("isUser") != null) {
            isUser = true;

            if (session.getAttribute("isEmployee") != null) {
                isEmployee = true;

                if (session.getAttribute("isAdmin") != null) {
                    isAdmin = true;

                }
            } else {
                isPatient = true;
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();
        setBooleans();

        response.setContentType("text/html;charset=UTF-8");

        String[] publicUrlPatterns = {"/addUser.do",
            "/login.do", "/passwordChanger.do", "/errorPage.jsp", "/addUser.do",
            "/logout.do"};

        for (int i = 0; i < publicUrlPatterns.length; i++) {
            if (requestURI.endsWith(publicUrlPatterns[i])) {
                chain.doFilter(httpRequest, response);
                return;
            }
        }

        String[] protectedUrlPatterns = {"/protected/refresh.do", "/protected/adminDashboard.do",
            "/protected/employeeDashboard.do", "/protected/patientDashboard.do"};

        try {

            if (isUser && requestURI.endsWith(protectedUrlPatterns[0])) {

            } else if (isAdmin && requestURI.endsWith(protectedUrlPatterns[1])) {

            } else if (isEmployee && requestURI.endsWith(protectedUrlPatterns[2])) {

            } else if (isPatient && requestURI.endsWith(protectedUrlPatterns[3])) {

            } else {
                throw new Exception();
            }

            session.setAttribute("message", "");

            chain.doFilter(httpRequest, response);

        } catch (Exception e) {

            session.setAttribute("message", "You are not allowed to access this resource");

            RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("/errorPage.jsp");
            requestDispatcher.forward(httpRequest, response);
        }

    }

    @Override
    public void destroy() {

    }

}
