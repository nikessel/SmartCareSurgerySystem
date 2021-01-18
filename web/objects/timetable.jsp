<%-- 
    Document   : timetable
    Created on : 15-Jan-2021, 14:55:23
    Author     : niklas
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
        <div class="centerDiv">
            <h2>Timetabled consultations and surgeries</h2>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="dateSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <label for="fromDate">Show from date:</label>
            <input type="date" id="fromDate" name="fromDate"> 
            <label for="toDate">to date:</label>
            <input type="date" id="toDate" name="toDate" onchange="this.form.submit();">
        </form>


        <form method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="dateSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <input type="hidden" name="fromDate" value=""> 
            <input type="hidden" name="toDate" value="">
            <input type="submit" name="reset" value="Reset"/>
        </form>
        <br>

        <table class="consultationTable">
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
