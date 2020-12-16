<%-- 
    Document   : patientDashboard
    Created on : 15-Dec-2020, 23:22:34
    Author     : patdizon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Dashboard Page</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="dashboard">
            <div class="bg"></div>
            <div align="center">
                <h1>Patient Registration Form</h1>
                <form action="<%= request.getContextPath()%>/register" method="post">
                    <table style="with: 80%">
                        <tr>
                            <td>User Name</td>
                            <td><input type="text" name="username" /></td>
                        </tr>
                        <tr>
                            <td>First Name</td>
                            <td><input type="text" name="firstName" /></td>
                        </tr>
                        <tr>
                            <td>Sur Name</td>
                            <td><input type="text" name="surName" /></td>
                        </tr>
                        <tr>
                            <td>Patient ID</td>
                            <td><input type="text" name="patientID" /></td>
                        </tr>
                        <tr>
                        <tr>
                            <td>Date Of Birth</td>
                            <td><input type="text" name="dateOfBirth" /></td>
                        </tr>
                        <tr>  
                            <td>Address</td>
                            <td><input type="text" name="address" /></td>
                        </tr>
                    </table>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        </div>
    </body>
</html>