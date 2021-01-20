<%-- 
    Document   : consultationTable
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>


<%@ include file = "/objects/jspHeader.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                            <th>Nurse name</th>
                            </c:if>
                            <c:if test="${empty isPatient}">
                            <th>Patient name</th>
                            </c:if>
                        <th>Issue</th>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${consultations}" var="consultation">
                        <tr>
                            <td>${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}</td>
                            <c:if test="${not empty isPatient}">
                                <c:choose>  
                                    <c:when test = "${consultation.doctor.ID == '20000'}">
                                        <td> </td>
                                    </c:when>
                                    <c:otherwise> 
                                        <td>${consultation.doctor.firstName} ${consultation.doctor.surName}</td>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>  
                                    <c:when test = "${consultation.nurse.ID == '30000'}">
                                        <td> </td>
                                    </c:when>
                                    <c:otherwise> 
                                        <td>${consultation.nurse.firstName} ${consultation.nurse.surName}</td>
                                    </c:otherwise>  
                                </c:choose>  

                            </c:if>
                            <c:if test="${empty isPatient}">
                                <td>${consultation.patient.firstName} ${consultation.patient.surName}</td>
                            </c:if>

                            <td>${consultation.note}</td>
                        </tr>
                    </c:forEach>   
                </tbody>
            </table>
    </body>
</html>
