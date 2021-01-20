<%-- 
    Document   : appointmentRemover
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <c:if test="${selectedTimeTable != '1'}">
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="consultationRemover">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <select name="removeAppointmentID">
                    <c:forEach var="consultation" items="${consultations}">
                        <c:choose>
                            <c:when test = "${not empty isNurse or not empty isDoctor}">
                                <option value="${consultation.ID}" >
                                    Consultation time: ${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}
                                    , with patient ${consultation.patient.firstName} ${consultation.patient.surName}</option>
                            </c:when>
                            <c:when test = "${consultation.doctor.ID == '20000'}">
                                <option value="${consultation.ID}" >
                                    Consultation time: ${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}
                                    , with nurse ${consultation.nurse.firstName} ${consultation.nurse.surName}</option>
                                </c:when>
                                <c:otherwise> 
                                <option value="${consultation.ID}" >
                                    Consultation time: ${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}
                                    , with doctor ${consultation.doctor.firstName} ${consultation.doctor.surName}</option>
                                </c:otherwise>
                            </c:choose> 

                    </c:forEach>
                </select>

                <input class="paddedInput" type="submit" value="Cancel consultation" />
            </form>
        </c:if>

        <c:if test="${selectedTimeTable == '1'}">
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="consultationRemover">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <select name="removeAppointmentID">
                    <c:forEach var="surgery" items="${surgeries}">
                        <c:choose>
                            <c:when test = "${not empty isDoctor}">
                                <option value="${surgery.ID}" >
                                    Surgery time: ${ex:formatDate(surgery["surgeryTime"], "dd-MM-yyyy HH:mm")}
                                    , with patient ${surgery.patient.firstName} ${surgery.patient.surName}</option>
                                </c:when>
                                <c:otherwise> 
                                <option value="${consultation.ID}" >
                                    Consultation time: ${ex:formatDate(consultation["consultationTime"], "dd-MM-yyyy HH:mm")}
                                    , with doctor ${consultation.doctor.firstName} ${consultation.doctor.surName}</option>
                                </c:otherwise>
                            </c:choose> 

                    </c:forEach>
                </select>

                <input class="paddedInput" type="submit" value="Cancel surgery" />
            </form>
        </c:if>

    </body>
</html>
