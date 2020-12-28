<%-- 
    Document   : errorPage
    Created on : 28-Dec-2020, 09:41:25
    Author     : niklas
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Error Page</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="dashboard login">
            <h1>Error page</h1>
            <h2>Exception: ${pageContext.exception}</h2>
            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Return to login" />
            </form>

        </div>
    </body>
</html>

