<%-- 
    Document   : errorPage
    Author     : Niklas Sarup-Lytzen ID: 18036644
--%>

<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Error Page</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="login mediumCard">
            <h1>Error page</h1>
            <h2>${message}</h2>
            <form class="paddedForm" action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Return to login" />
            </form>
        </div>
    </body>
</html>

