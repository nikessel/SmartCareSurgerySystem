<%-- 
    Document   : employeeDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : Niklas Sarup-Lytzen ID: 18036644
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
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="dashboard">
            <div class="bg"></div>
            <h1>Hello ${currentUser.firstName} ${currentUser.surName}.${message}</h1>
            <h2>Welcome to your personal dashboard.</h2>
            <br>
            <div class="leftDiv">
                <h2>Scheduled consultations</h2>
                <div>
                    <form method="post" action="${pageContext.request.contextPath}/protected/employeeDashboard.do" name="dateSelector">
                        <label for="fromDate">Show from date:</label>
                        <input type="date" id="fromDate" name="fromDate"> 
                        <label for="toDate">to date:</label>
                        <input type="date" id="toDate" name="toDate" onchange="this.form.submit();">
                    </form>
                    <br>
                    <c:if test = "${not empty param.fromDate && not empty param.toDate}">
                        <label for="dateButton">Displaying consultations from ${param.fromDate} to ${param.toDate}</label>
                        <form action="${pageContext.request.contextPath}/protected/employeeDashboard.do" method="post">
                            <input type="hidden" name="resetDates" value="true">
                            <input style="padding: 5px;" type="submit" value="Reset" />
                        </form>
                    </c:if>
                    <br>
                </div>
            </div>
            <div class="rightDiv">
                <h2>List of patients</h2>
                <form method="post" action="${pageContext.request.contextPath}/protected/employeeDashboard.do" name="patientListSelector">
                    <select name="insuranceSelection" onchange="this.form.submit();">
                        <option disabled selected value> sort by insurance status </option>
                        <option value="0">insured
                        <option value="1">not insured
                        <option value="2">all
                    </select>
                </form>
            </div>

            <br>
            <br>
            <div class="ltableDiv" id="consultationTableDiv">
                <table id="consultationTable">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Patient name</th>
                            <th>Week number</th>
                            <th>Day of month</th>
                            <th>Hour of day</th>
                            <th>Minute of day</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${consultations}" var="consultation">
                            <tr>
                                <td>${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy")}</td>
                                <td>${consultation.patient.firstName} ${consultation.patient.surName}</td>
                                <td>${consultation.consultationTime.weekNumber}</td>
                                <td>${consultation.consultationTime.day}</td>
                                <td>${consultation.consultationTime.hours}</td>
                                <td>${consultation.consultationTime.minutes}</td>
                            </tr>
                        </c:forEach>   
                    </tbody>
                </table>
            </div> 
            <div class="rtableDiv" id="patientTableDiv">
                <table id="patientTable">
                    <thead>
                        <tr>
                            <th>Patient name</th>
                            <th>Is insured by the NHS</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${patients}" var="patient">
                            <tr>
                                <td>${patient.firstName} ${patient.surName}</td>
                                <td>${patient.insured ? 'Yes' : 'No'}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div> 
            <br>
            <br>
            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Logout" />
            </form>
            <br>
            <p style="text-align: right">You are logged in as a ${loggedInAs}<p>
        </div>


    </body>
</html>


