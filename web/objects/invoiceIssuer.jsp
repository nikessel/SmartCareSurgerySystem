<%-- 
    Document   : consultationEditor
    Created on : 18-Jan-2021, 09:31:43
    Author     : niklas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>

        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="consultationEditor">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="consultationSelection">
                <c:forEach var="consultation" items="${consultations}">
                    <option value="${consultation.ID}" >${consultation.patient.firstName} ${consultation.patient.surName} ${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}</option>
                </c:forEach>
            </select>

            <input class="paddedInput" type="submit" value="Issue invoice" />
        </form>
            
    </body>
</html>
