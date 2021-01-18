<%-- 
    Document   : patientDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>
<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Dashboard Page</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <div class="grid-container">    

        <c:import url="objects/keepScrollPosition.html"/>
        <c:import url="objects/dashboardHeader.jsp"/>

        <aside class="sidenav">

        </aside>

        <main class="main">
            <div class="wide_card">
                <c:import url="objects/timetable.jsp"/>
                <c:import url="objects/invoicePayer.jsp"/>
            </div>


            <div class="main-cards">
                <div class="card">
                    <c:import url="objects/personalInfo.jsp"/>
                </div>
                <div class="card">
                    <c:import url="objects/bookConsultation.jsp"/>
                </div>
            </div>

        </main>

    </div>
</html>


