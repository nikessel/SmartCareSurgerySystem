<%-- 
    Document   : adminDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard Page</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="dashboard">
            <div class="bg"></div>
            
            <h1>Hello ${currentUser.firstName} ${currentUser.surName}.${message}</h1>
            <h2>Welcome to your personal dashboard.</h2>
            
            <br>
            <form action="logout.do" method="post">
                <input type="submit" value="Logout" />
            </form>
            <br>
            <p style="text-align: right">You are logged in as an ${loggedInAs}<p>
        </div>


    </body>
</html>


