<%-- 
    Document   : timetable
    Created on : 15-Jan-2021, 14:55:23
    Author     : niklas
--%>

<%@ include file = "/objects/jspHeader.jsp"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table class="consultationTable">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Patient name</th>
                    <th>Week number</th>
                    <th>Day of week</th>
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
    </body>
</html>
