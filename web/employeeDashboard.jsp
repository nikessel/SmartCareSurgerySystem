<%-- 
    Document   : employeeDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : niklas
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Dashboard Page</title>
        <style>
            <%@include file="/WEB-INF/css/employeeDashboardStyle.css"%>
        </style>
    </head>
    <body>
        <h1 align="center">Hello ${currentUser.firstName} ${currentUser.surName}. Welcome to your personal dashboard.</h1>
        <br>
        <br>
    <center>
        <h2>Scheduled consultations</h2>

        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Patient name</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${consultations}" var="consultation">
                    <tr>
                        <td>${ex:formatDate(consultation["consultationDate"], "dd-MM-yyyy")}</td>
                        <td>${consultation.patient.firstName} ${consultation.patient.surName}</td>
                    </tr>
                </c:forEach>   
            </tbody>
        </table>
        <br>
        <label for="birthday">Birthday:</label>
        <input type="date" id="birthday" name="birthday"> 
        <br>
        <form action="logout.do" method="post">
            <input type="submit" value="Logout" />
        </form>

    </center>
</body>
</html>


