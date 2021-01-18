<%-- 
    Document   : timetable
    Created on : 15-Jan-2021, 14:55:23
    Author     : niklas
--%>

<%@ include file = "/objects/jspHeader.jsp"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="centerDiv">
            <h2>Timetabled consultations and surgeries</h2>
        </div>

        <h3>${filterMessage}</h3>

        <form method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="dateSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <label for="fromDate">Show from date:</label>
            <input type="date" id="fromDate" name="fromDate"> 
            <label for="toDate">to date:</label>
            <input type="date" id="toDate" name="toDate" onchange="this.form.submit();">
        </form>


        <form method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="dateSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <input type="hidden" name="fromDate" value=""> 
            <input type="hidden" name="toDate" value="">
            <input type="submit" name="reset" value="Reset"/>
        </form>
        <br>

        <table class="consultationTable">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Patient name</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${consultations}" var="consultation">
                    <tr>
                        <td>${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}</td>
                        <td>${consultation.patient.firstName} ${consultation.patient.surName}</td>
                    </tr>
                </c:forEach>   
            </tbody>
        </table>
    </body>
</html>
