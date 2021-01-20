<%-- 
    Document   : employeeDashboard
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>

<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Employee Dashboard Page</title>
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
            <div class="wide_card" id="timetable">
                <c:import url="objects/timetable.jsp"/>
                <c:import url="objects/invoiceIssuer.jsp"/>
            </div>

            <div class="main-cards" id="patients">
                <div class="card">
                    <c:import url="objects/patientTable.jsp"/>
                </div>
                <div class="card">
                    <c:import url="objects/personalInfo.jsp"/>
                </div>
            </div>

            <div class="main-cards" id="confirmation">
                <div class="card">
                    <c:import url="objects/pendingConfirmer.jsp"/>
                </div>

                <div class="card">
                    <c:if test="${not empty isDoctor}">
                        <c:import url="objects/prescriptionIssuer.jsp"/>
                    </c:if>
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


