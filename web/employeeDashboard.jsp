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
                background-image: url('https://images.unsplash.com/photo-1508923567004-3a6b8004f3d7?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=matteo-catanese-4KrQq8Z6Y5c-unsplash.jpg&w=1920');
            }
        </style>
    </head>
    <body>
        <h1 align="center">Employee Dashboard</h1>
        <%out.println(request.getAttribute("name"));%> 
    </body>
</html>
