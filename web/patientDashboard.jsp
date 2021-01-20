<%-- 
    Document   : patientDashboard
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
    <div class="mainGridContainer">    

        <c:import url="objects/keepScrollPosition.html"/>
        <c:import url="objects/dashboardHeader.jsp"/>

        <main class="main">

            <div class="mediumCardsMain">
                <div class="mediumCard">
                    <c:import url="objects/timetable.jsp"/>
                    <c:import url="objects/appointmentRemover.jsp"/>

                </div>
                <div class="smallCard">
                    <c:import url="objects/requester.jsp"/>
                </div>


                <div class="smallCard">
                    <c:import url="objects/invoicePayer.jsp"/>
                </div>
                <div class="extraPadding flexDisplay smallCard">
                    <c:import url="objects/personalInfo.jsp"/>
                </div>
            </div>

        </main>

    </div>
</html>


