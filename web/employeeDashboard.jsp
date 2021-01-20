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



    <div class="mainGridContainer">  


        <c:import url="objects/keepScrollPosition.html"/>
        <c:import url="objects/dashboardHeader.jsp"/>

        <main class="main">


            <div class="mediumCardsMain">

                <div class="mediumCard">
                    <c:import url="objects/timetable.jsp"/>
                    <c:import url="objects/invoiceIssuer.jsp"/>
                </div>

                <div class="mediumCard">
                    <c:import url="objects/patientTable.jsp"/>
                </div>
                <div class="mediumCard">
                    <c:import url="objects/pendingConfirmer.jsp"/>
                </div>
                <div class="flexDisplay smallCard">
                    <c:import url="objects/personalInfo.jsp"/>
                </div>
                <c:if test="${not empty isDoctor}">
                    <div class="smallCard">
                        <c:import url="objects/prescriptionIssuer.jsp"/>
                    </div>
                </c:if>
            </div>


        </main>

    </div>
</html>


