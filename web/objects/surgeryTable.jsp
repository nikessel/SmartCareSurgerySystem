<%-- 
    Document   : surgeryTable
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "/objects/jspHeader.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table class="timeTable">

            <thead>
                <tr>
                    <th>Date</th>
                        <c:if test="${not empty isPatient}">
                        <th>Doctor name</th>
                        </c:if>
                        <c:if test="${empty isPatient}">
                        <th>Patient name</th>
                        </c:if>
                </tr>
            </thead>
            <tbody>

                <c:forEach items="${surgeries}" var="surgery">
                    <tr>
                        <td>${ex:formatDate(surgery["surgeryTime"], "dd-MM-yyyy HH:mm")}</td>
                        <c:if test="${not empty isPatient}">

                            <td>${surgery.doctor.firstName} ${surgery.doctor.surName}</td>

                        </c:if>
                        <c:if test="${empty isPatient}">
                            <td>${surgery.patient.firstName} ${surgery.patient.surName}</td>
                        </c:if>
                    </tr>
                </c:forEach>   
            </tbody>
        </table>
    </body>
</html>
