<%-- 
    Document   : employeeDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : niklas
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Dashboard Page</title>
        <style>
            body {
                background-image: url('images/mountain.jpg');
            }
        </style>
    </head>
    <body>
        <h1 align="center">Employee Dashboard</h1>
        <%out.println(request.getAttribute("user_id"));%> 
    </body>
</html>


