<%-- 
    Document   : pendingConsultationsTable
    Created on : 17-Jan-2021, 09:28:28
    Author     : niklas
--%>
<%@ include file = "/objects/jspHeader.jsp"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>Pending new consultation requests${message}</h3>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/refresh.do" name="pendingConsultationConfirmer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="pendingConsultationSelection">
                <c:forEach var="pendingConsultation" items="${pendingConsultations}">
                    <option value="${pendingConsultation.ID}" >${pendingConsultation.patient.firstName} ${pendingConsultation.patient.surName} ${ex:formatDate(pendingConsultation["consultationTime"], "dd-MM-yyyy HH:mm")}</option>
                </c:forEach>
            </select>
            <input type="checkbox" name="approve" value="true"/>
            <label for="approve">Approve request</label>
            <input class="paddedInput" type="submit" value="Confirm" />
        </form>
    </body>
</html>
