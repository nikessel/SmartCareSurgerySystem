<%-- 
    Document   : consultationEditor
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>

        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="appointmentSelector">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="appointmentSelection">

                <c:if test="${selectedTimeTable == '0'}">
                    <c:forEach var="consultation" items="${consultations}">
                        <option value="${consultation.ID}" >${consultation.patient.firstName} ${consultation.patient.surName} ${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}</option>
                    </c:forEach>
                </c:if>
                <c:if test="${selectedTimeTable == '1'}">
                    <c:forEach var="surgery" items="${surgeries}">
                        <option value="${surgery.ID}" >${surgery.patient.firstName} ${surgery.patient.surName} ${ex:formatDate(surgery["surgeryTime"], "dd-MM-yyyy HH:mm")}</option>
                    </c:forEach>
                </c:if>
            </select>

            <input class="paddedInput" type="submit" value="Issue invoice" />
        </form>


    </body>
</html>
