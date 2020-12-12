<%-- 
    Document   : employeeDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : niklas
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Dashboard Page</title>
        <style>
            <%@include file="/WEB-INF/css/employeeDashboardStyle.css"%>
        </style>
    </head>
    <body>
        <h1 align="center">Employee Dashboard</h1>
        <br>
        <br>
    <center>
        <%out.println(request.getAttribute("userID"));%> 
        <form action="employeeDashboard.do" method="post">
            <input type="submit" value="Submit" />
        </form>
        <h2>Consultations</h2>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                </tr>
            </thead>
            
            <tbody>
                <c:forEach items="${consultations}" var="consultation">
                <tr>
                    <td>${consultation.consultationID}</td>
                </tr>
                </c:forEach>   
            </tbody>
        </table>
    </center>
</body>
</html>


