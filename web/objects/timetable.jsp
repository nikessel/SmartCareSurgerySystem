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

        <c:if test="${not empty isAdmin}">
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="userSelector">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <select name="userSelection">
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient.ID}" >${patient.firstName} ${patient.surName}</option>
                    </c:forEach>
                    <c:forEach var="doctor" items="${doctors}">
                        <option value="${doctor.ID}" >${doctor.firstName} ${doctor.surName}</option>
                    </c:forEach>
                    <c:forEach var="nurse" items="${nurses}">
                        <option value="${nurse.ID}" >${nurse.firstName} ${nurse.surName}</option>
                    </c:forEach>
                </select>

                <input class="paddedInput" type="submit" value="Select user" />
            </form>
        </c:if>



        <form method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="dateSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <label for="fromDate">Show from date:</label>
            <input type="date" name="fromDate"> 
            <label for="toDate">to date:</label>
            <input type="date" name="toDate" onchange="this.form.submit();">
        </form>


        <form method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="dateSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <input type="hidden" name="fromDate" value=""> 
            <input type="hidden" name="toDate" value="">
            <input type="submit" name="reset" value="Reset"/>
        </form>
        <br>

        <c:if test="${empty isNurse}">
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="timeTableSelector">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <select name="timeTableSelection" onchange="this.form.submit();">
                    <option disabled selected value>Choose events to display </option>
                    <option value="0">Consultations
                    <option value="1">Surgeries
                </select>
            </form>
        </c:if>


        <c:if test="${selectedTimeTable == '0'}">
            <h5>Consultation timetable</h5>
            <c:import url="/objects/consultationTable.jsp"/>
        </c:if>

        <c:if test="${selectedTimeTable == '1'}">
            <h5>Surgery timetable</h5>
            <c:import url="/objects/surgeryTable.jsp"/>
        </c:if>
    </body>
</html>
