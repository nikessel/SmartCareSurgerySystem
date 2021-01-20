<%-- 
    Document   : prescriptionIssuer
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
        <h4>${message3}</h4>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="prescriptionIssuer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="patientSelection">
                <c:forEach var="patient" items="${patients}">
                    <option value="${patient.ID}" >${patient.firstName} ${patient.surName}</option>
                </c:forEach>
            </select>
            <label for="expirationDate">Select an expiration date</label>
            <input name="expirationDate" required="required" type="date"/>
            <label for="medication">Select medication</label>
            <input name="medication" required="required" type="text"/>

            <input class="paddedInput" type="submit" value="Issue prescription" />
        </form>

    </body>
</html>
