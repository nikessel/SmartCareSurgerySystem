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
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/patientDashboard.do" name="consultationBooker">
            <select name="selectedDoctor">
                <c:forEach var="doctor" items="${doctors}">
                    <option value="${doctor}" >${doctor.firstName} ${doctor.surName}</option>
                </c:forEach>
            </select>
            <select name="selectedNurse">
                <c:forEach var="nurse" items="${nurses}">
                    <option value="${nurse}" >${nurse.firstName} ${nurse.surName}</option>
                </c:forEach>
            </select>
        </form>


    </body>
</html>
