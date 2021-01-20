<%-- 
    Document   : pendingConsultationsTable
    Author     : Niklas Sarup-Lytzen ID: 18036644
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
        <h3>Pending requests</h3>
        <h4>Consultations</h4>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="pendingConsultationConfirmer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="pendingConsultationSelection">
                <c:forEach var="pendingConsultation" items="${pendingConsultations}">
                    <option value="${pendingConsultation.ID}" >${pendingConsultation.patient.firstName} ${pendingConsultation.patient.surName} ${ex:formatDate(pendingConsultation["consultationTime"], "dd-MM-yyyy HH:mm")}: ${pendingConsultation.note}</option>
                </c:forEach>
            </select>
            <br><br>

            <input type="checkbox" name="approveConsultation" value="true"/>
            <label for="approveConsultation">Approve request</label>
            <input class="paddedInput" type="submit" value="Confirm" />
        </form>

        <br>
        <h4>Surgeries</h4>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="pendingSurgeryConfirmer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="pendingSurgerySelection">
                <c:forEach var="pendingSurgery" items="${pendingSurgeries}">
                    <option value="${pendingSurgery.ID}" >${pendingSurgery.patient.firstName} ${pendingSurgery.patient.surName} ${ex:formatDate(pendingSurgery["surgeryTime"], "dd-MM-yyyy HH:mm")}</option>
                </c:forEach>
            </select>

            <br><br>
            <input type="checkbox" name="approveSurgery" value="true"/>
            <label for="approveSurgery">Approve request</label>
            <input class="paddedInput" type="submit" value="Confirm" />
        </form>

        <br>
        <h4>Prescriptions</h4>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="pendingPrescriptionConfirmer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="pendingPrescriptionSelection">
                <c:forEach var="pendingPrescription" items="${pendingPrescriptions}">
                    <option value="${pendingPrescription.ID}" >${pendingPrescription.patient.firstName} 
                        ${pendingPrescription.patient.surName}, prescription for: ${pendingPrescription.medication}, expiration date: ${ex:formatDate(pendingPrescription["expirationDate"], "dd-MM-yyyy")},
                        is expired: ${ex:isInThePast(pendingPrescription["expirationDate"]) ? 'Yes' : 'No'}
                    </option>
                </c:forEach>
            </select>
            <br><br>
            <label for="newExpirationDate">Select a new expiration date</label>
            <input id="newPrescriptionDate" name="newPrescriptionDate" required="required" type="date"/>
            <input type="checkbox" name="approvePrescription" value="true"/>
            <label for="approvePrescription">Approve prescription</label>
            <input class="paddedInput" type="submit" value="Confirm" />
        </form>
    </body>
</html>
