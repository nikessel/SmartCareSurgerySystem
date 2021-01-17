<%-- 
    Document   : employeeDashboard
    Created on : 09-Dec-2020, 20:15:47
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>

<%@ include file = "/objects/jspHeader.jsp"%>


<!DOCTYPE html>
<html>
    <head>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>

    <div class="grid-container">    

        <c:import url="objects/dashboardHeader.jsp"/>

        <aside class="sidenav">

        </aside>


        <main class="main">
            <h3>dsad${message}</h3>

            <div class="wide_card">
                <div class="centerDiv">
                    <h2>Timetabled consultations and surgeries</h2>
                </div>

                <c:import url="objects/timetable.jsp"/>
            </div>


            <div class="main-cards">
                <div class="card">
                    <c:import url="objects/patientTable.jsp"/>
                </div>
                <div class="card">
                    <c:import url="objects/personalInfo.jsp"/>
                </div>
            </div>

            <div class="main-cards">
                <div class="card">
                    <c:import url="objects/pendingConsultationsConfirmer.jsp"/>
                </div>
                <div class="card">
                </div>
            </div>
            <!--
                        <div class="main-overview">
                            <div class="overviewcard">
                                <div class="overviewcard__icon">Overview</div>
                                <div class="overviewcard__info">Card</div>
                            </div>
                            <div class="overviewcard">
                                <div class="overviewcard__icon">Overview</div>
                                <div class="overviewcard__info">Card</div>
                            </div>
                            <div class="overviewcard">
                                <div class="overviewcard__icon">Overview</div>
                                <div class="overviewcard__info">Card</div>
                            </div>
                            <div class="overviewcard">
                                <div class="overviewcard__icon">Overview</div>
                                <div class="overviewcard__info">Card</div>
                            </div>
                        </div>
                                        
            -->
        </main>

    </div>
</html>


