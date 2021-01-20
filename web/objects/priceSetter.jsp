<%-- 
    Document   : priceSetter
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
        <h3>Set new prices</h3> <br>
        
        <h4>Current prices: </h4>
        <h5>Doctor consultations: £${consultationPrice}, Nurse consultations: £${consultationPriceNurse}, Surgeries: £${surgeryPrice}</h5>

        <c:if test="${empty selectedPrice}">

            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="priceSelector">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <select name="priceSelection" onchange="this.form.submit();">
                    <option disabled selected value>Choose an item to set hourly price for</option>
                    <option value="0">Doctor consultations
                    <option value="1">Nurse consultations
                    <option value="2">Surgeries
                </select>
            </form>

        </c:if>


        <c:if test="${not empty selectedPrice}">

            <h4>Set price for ${setPriceFor}</h4>
            <form class="paddedForm" method="post" action="${pageContext.request.contextPath}/protected/refresh.do" name="priceSetter">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <label for="newPrice">New price:</label>
                <input type="number" name="newPrice" step="0.01"> 

                <input class="paddedInput" type="submit" value="Confirm" />
            </form>

        </c:if>

    </body>
</html>
