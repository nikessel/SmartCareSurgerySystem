<%-- 
    Document   : addEmployee
    Created on : 15-Jan-2021, 16:54:20
    Author     : niklas
--%>

<%@ include file = "/objects/jspHeader.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/addUser.do" method="post">
            <h3>Credentials</h3>
            <input id="username" name="username" required="required" type="text" placeholder="Username" value="${username}"/>
            <input id="password" name="password" required="required" type="password" placeholder="Password"/>
            <input id="repeatPassword" name="repeatPassword" required="required" type="password" placeholder="Repeat password"/>
            <h3>Personal information</h3>
            <input id="firstName" name="firstName" required="required" type="text" placeholder="First name" value="${sessionScope.firstName}"/>
            <input id="surName" name="surName" required="required" type="text" placeholder="Sur name" value="${sessionScope.surName}"/><br><br>
            <input type="checkbox" name="full_time" value="true"/>
            <label for="full_time">Add as a full-time employee?</label>
            <br><br>
            <input type="submit" value="Submit" />
        </form>
        <br>
    </body>
</html>
