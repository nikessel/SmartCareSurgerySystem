<%@ include file = "/objects/jspHeader.jsp"%>


<!DOCTYPE html>
<html>
    <head>
        <title>SmartCareSurgerySystem</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
        <div class="wide_card login">
            <h1>Smart Care Surgery System</h1>
            <h2>Login</h2>
            <br>
            <form class="paddedForm" action="${pageContext.request.contextPath}/login.do" method="post">
                <input type="hidden" name="jspName" value="${pageScope['javax.servlet.jsp.jspPage']}" />
                <input class="paddedInput" id="username" name="username" required="required" type="text" placeholder="Username"/>
                <input class="paddedInput" id="password" name="password" required="required" type="password" placeholder="Password" />
                <input class="paddedInput" type="submit" value="Submit" />
            </form>
            <br>
            <h3>${message}</h3>
            <br>
            <form class="paddedForm" action="${pageContext.request.contextPath}/passwordChanger.jsp" method="post">
                <input class="paddedInput" type="submit" value="Change password" />
            </form>
            <form class="paddedForm" action="${pageContext.request.contextPath}/addUser.jsp" method="post">
                <input class="paddedInput" type="submit" value="Create a new user" />
            </form>

        </div>
    </body>
</html>

