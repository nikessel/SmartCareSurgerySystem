<%-- 
    Document   : userRemover
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
        <h3>Remove a user</h3>

        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="userRemover">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="removeUserSelection">
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
            <input class="paddedInput" type="submit" value="Remove user" />
        </form>
    </body>
</html>
