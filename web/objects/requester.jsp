<%-- 
    Document   : requester
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
        <h3>Book an appointment or request a prescription extension</h3>
        <h4>${message1}</h4>
        
        <c:if test="${empty requestType}">
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="requestTypeSelector">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <select name="requestTypeSelection" onchange="this.form.submit();">
                    <option disabled selected value>Select booking type</option>
                    <option value="0">Consultation
                    <option value="1">Surgery
                    <option value="2">Prescription
                </select>
            </form>
        </c:if>

        <c:if test="${not empty requestType}">

            <h4>${requesterMessage}</h4>
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="requester">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />


                <select name="selectedConsultatantID">
                    <option disabled selected value>Select booking</option>

                    <c:if test="${requestType == '0'}">
                        <option disabled selected value>Select a doctor or a nurse</option>
                    </c:if>

                    <c:if test="${requestType != '0'}">
                        <option disabled selected value>Select a doctor</option>
                    </c:if>

                    <c:forEach var="doctor" items="${doctors}">
                        <option value="${doctor.ID}" >${doctor.firstName} ${doctor.surName}</option>
                    </c:forEach>

                    <c:if test="${requestType == '0'}">
                        <c:forEach var="nurse" items="${nurses}">
                            <option value="${nurse.ID}" >${nurse.firstName} ${nurse.surName}</option>
                        </c:forEach>
                    </c:if>

                </select>

                <c:if test="${requestType != '2'}">
                    <input name="selectedDate" required="required" type="date"/>
                    <input name="selectedTime" required="required" type="time"/>

                    <c:if test="${requestType == '0'}">
                        <input name="note" type="text"/>
                        <input type="submit" value="Book consultation"/>
                    </c:if>

                    <c:if test="${requestType == '1'}">
                        <input type="submit" value="Book surgery"/>
                    </c:if>
                </c:if>

                <c:if test="${requestType == '2'}">
                    <select name="selectedPrescription">
                        <c:forEach var="prescription" items="${prescriptions}">
                            <option value="${prescription.ID}"
                                    >Prescription for: ${prescription.medication}, expiration date: ${ex:formatDate(prescription["expirationDate"], "dd-MM-yyyy")},
                                is expired: ${ex:isInThePast(prescription["expirationDate"]) ? 'Yes' : 'No'}</option>
                            </c:forEach>
                    </select>

                    <input type="submit" value="Request prescription extension"/>

                </c:if>


            </form>


            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="reset">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <input type="hidden" name="resetBookingType" value="true" />
                <input type="submit" value="Back"/>
            </form>
        </c:if>


    </body>
</html>
