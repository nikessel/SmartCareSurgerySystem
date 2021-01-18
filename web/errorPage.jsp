<%-- 
    Document   : errorPage
    Created on : 28-Dec-2020, 09:41:25
    Author     : niklas
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
        <div class="login">
            <h1>Error page</h1>
            <h2>${message}${pageContext.exception}${filterMessage}</h2>
            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Return to login" />
            </form>
        </div>
    </body>
</html>

