<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>SmartCareSurgerySystem</title>
        <style><%@include file="/WEB-INF/css/loginstyle.css"%></style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="loading">
                    <div class="block"></div>
                    <div class="block"></div>
                    <div class="block"></div>
                    <div class="block"></div>
                </div>
            </div>
            <div class="main">
                <div class="login">
                    <form action="<%=request.getContextPath()%>/login" method="post">
                        <input id="username" name="username" required="required" type="text" placeholder="Username" />
                        <input id="password" name="password" required="required" type="password" placeholder="Password" />
                        <input type="submit" value="Submit" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>

