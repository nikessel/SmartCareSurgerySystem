<%-- 
    Document   : pendingEmployeesTable
    Created on : 15-Jan-2021, 15:53:45
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

        <h3>Pending new employee requests</h3>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="pendingEmployeeConfirmer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="pendingEmployeeSelection">
                <c:forEach var="pendingEmployee" items="${pendingEmployees}">
                    <option value="${pendingEmployee.ID}" >${pendingEmployee.firstName} ${pendingEmployee.surName}, ID: ${pendingEmployee.ID}</option>
                </c:forEach>
            </select>
            <input type="checkbox" name="approve" value="true"/>
            <label for="approve">Approve request</label>
            <input class="paddedInput" type="submit" value="Confirm" />
        </form>
    </body>
</html>