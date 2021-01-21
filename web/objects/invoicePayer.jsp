<%-- 
    Document   : invoicePayer
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
        <h3>Pay an invoice</h3>
        <h4>${message2}</h4>
        <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="invoicePayer">
            <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
            <select name="invoiceSelection">
                <c:forEach var="invoice" items="${invoices}">
                    <c:if test="${not invoice.paid}">
                        <option value="${invoice.ID}" >Invoice date: ${ex:formatDate(invoice["dateOfInvoice"], "dd-MM-yyyy")}, total amount owed: £${invoice.price}</option>
                    </c:if>
                </c:forEach>
            </select>

            <input class="paddedInput" type="submit" value="Pay invoice" />
        </form>
    </body>
</html>
