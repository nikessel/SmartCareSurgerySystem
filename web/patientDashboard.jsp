<%-- 
    Document   : patientDashboard
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
        <title>Patient Dashboard Page</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="dashboard">
            <div class="bg"></div>
            <h1>Hello ${sessionScope.currentUser.firstName} ${sessionScope.currentUser.surName}.${message}</h1>
            <h2>Welcome to your personal dashboard.</h2>
            <br>
            <h3>Personal information</h3>
            <h5>Date of birth: ${sessionScope.currentUser.dateOfBirth}</h5>
            <h5>Insured by the NHS: ${sessionScope.currentUser.insured ? 'Yes' : 'No'}</h5>
            <h4>Address</h4>
            <h5>${sessionScope.currentUser.address.addressLine1}</h5>
            <h5>${sessionScope.currentUser.address.addressLine2}</h5>
            <h5>${sessionScope.currentUser.address.postcode}</h5>
            <h5>${sessionScope.currentUser.address.county}</h5>
            <h5>${sessionScope.currentUser.address.town}</h5>
            <h5>${sessionScope.currentUser.address.telephoneNumber}</h5>

            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Logout" />
            </form>
            <br>
            <p style="text-align: right">You are logged in as a ${loggedInAs}<p>
        </div>


    </body>
</html>


