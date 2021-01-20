<%-- 
    Document   : adminDashboard
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>
<%@ include file = "/objects/jspHeader.jsp"%>

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
        <div class="mainGridContainer">    

            <c:import url="objects/keepScrollPosition.html"/>
            <c:import url="objects/dashboardHeader.jsp"/>

            <aside class="sidenav">

            </aside>                  

            <main class="main">
                <div class="wideCard">
                    <c:import url="objects/timetable.jsp"/>
                    <c:import url="objects/appointmentRemover.jsp"/>
                </div>

                <div class="mediumCardsMain">
                    <div class="mediumCard">
                        <c:import url="objects/patientTable.jsp"/>
                    </div>
                    <div class="mediumCard">
                        <c:import url="objects/pendingEmployeesConfirmer.jsp"/>
                    </div>


                </div>

                <div class="mediumCardsMain">
                    <div class="mediumCard">
                        <c:import url="objects/personalInfo.jsp"/>
                    </div>
                    <div class="mediumCard">
                        <c:import url="objects/turnoverCalculator.jsp"/> 
                    </div>
                </div>

                <div class="mediumCardsMain">
                    <div class="mediumCard">
                        <c:import url="objects/priceSetter.jsp"/>
                    </div>
                    <div class="mediumCard">
                        <c:import url="objects/userRemover.jsp"/>
                    </div>
                </div>
            </main>

        </div>
    </body>
</html>


