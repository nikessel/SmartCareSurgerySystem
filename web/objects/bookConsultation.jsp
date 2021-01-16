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
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/bookConsultation.do" name="doctorSelector">
            <select name="selectedDoctor">
                <c:forEach var="doctor" items="${doctors}">
                    <option value="${doctor.firstName} ${doctor.surName}" >${doctor}</option>
                </c:forEach>
            </select>
        </form>

    </body>
</html>
