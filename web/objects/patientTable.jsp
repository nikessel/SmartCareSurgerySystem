<%-- 
    Document   : patientTable
    Created on : 15-Jan-2021, 14:56:50
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
        
        <h3>Patient table</h3> <br>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/employeeDashboard.do" name="patientListSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="insuranceSelection" onchange="this.form.submit();">
                <option disabled selected value> sort by insurance status </option>
                <option value="0">insured
                <option value="1">not insured
                <option value="2">all
            </select>
        </form>

        <div>
            <table id="patientTable">
                <thead>
                    <tr>
                        <th>Patient name</th>
                        <th>Is insured by the NHS</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${patients}" var="patient">
                        <tr>
                            <td>${patient.firstName} ${patient.surName}</td>
                            <td>${patient.insured ? 'Yes' : 'No'}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div> 

    </body>
</html>
