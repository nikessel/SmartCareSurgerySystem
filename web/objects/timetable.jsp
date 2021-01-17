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
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${consultations}" var="consultation">
                    <tr>
                        <td>${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}</td>
                        <td>${consultation.patient.firstName} ${consultation.patient.surName}</td>
                    </tr>
                </c:forEach>   
            </tbody>
        </table>
    </body>
</html>
