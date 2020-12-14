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
        <div class="dashboard">
            <div class="bg"></div>
            <center>
                <h1>Hello ${currentUser.firstName} ${currentUser.surName}. Welcome to your personal dashboard.</h1>
                <br>
                <br>

                <h2>Scheduled consultations</h2>
                <div>
                    <label for="fromDate">Show from date:</label>
                    <input type="date" id="fromDate" name="fromDate"> 
                    <label for="toDate">to date:</label>
                    <input type="date" id="toDate" name="toDate">
                </div>
                <div>
                    <form action="employeeDashboard.do" method="post">
                        <input type="submit" value="Submit" />
                    </form>
                </div>
                <br>
                <br>
                <div>
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
                </div> 
                <br>
                <br>
                <form action="logout.do" method="post">
                    <input type="submit" value="Logout" />
                </form>
            </center>
        </div>


    </body>
</html>


