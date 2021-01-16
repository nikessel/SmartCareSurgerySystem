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
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.pendingEmployees}" var="pendingEmployee">
                <tr>
                    <td>${pendingEmployee.ID}</td>
                    <td>${pendingEmployee.firstName} ${pendingEmployee.surName}</td>
                </tr>
            </c:forEach>   
        </tbody>
    </table>
</body>
</html>
