<%-- 
    Document   : bookConsultation
    Created on : 16-Jan-2021, 16:40:21
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
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="consultationBooker">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="selectedConsultatantID">
                <c:forEach var="doctor" items="${doctors}">
                    <option value="${doctor.ID}" >${doctor.firstName} ${doctor.surName}</option>
                </c:forEach>
                <c:forEach var="nurse" items="${nurses}">
                    <option value="${nurse.ID}" >${nurse.firstName} ${nurse.surName}</option>
                </c:forEach>
            </select>
            <input name="selectedDate" required="required" type="date"/>
            <input name="selectedTime" required="required" type="time"/>
            <input name="note" type="text"/>
            <input type="submit" value="Book consultation"/>
        </form>
    </body>
</html>
