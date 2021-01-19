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
        <div class="login card">
            <h1>Smart Care Surgery System Password Management</h1>
            <h2>${message}</h2>
            <br>
            <form class="paddedForm" action="${pageContext.request.contextPath}/passwordChanger.do" method="post">
                <input name="username" required="required" type="text" placeholder="Username"/>
                <br><br>
                <input name="password" required="required" type="password" placeholder="Password" />
                <br><br>
                <input name="updatePassword" required="required" type="password" placeholder="Input new password" />
                <br><br>
                <input name="repeatPassword" required="required" type="password" placeholder="Repeat password" />
                <br><br>
                <input type="submit" value="Submit" />
            </form>
            <br>
            <br>
            <form class="paddedForm" action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Back" />
            </form>

        </div>
    </body>
</html>

