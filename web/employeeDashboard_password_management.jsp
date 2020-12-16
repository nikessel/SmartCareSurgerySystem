<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SmartCareSurgerySystem</title>
        <style>
            <%@include file="/WEB-INF/css/style.css"%>
        </style>
    </head>
    <body>
            <div class="dashboard login">
                <h1>Smart Care Surgery System Password Management</h1>
                <<form action="login.do" method="post">
                    <input id="username" name="username" required="required" type="text" placeholder="Username"/>
                    <input id="password" name="password" required="required" type="password" placeholder="Password" />
                    <input id="update_password" name="update_password" required="required" type="update_password" placeholder="update_password" />
                    <input type="submit" value="Submit" />
                </form>
                <br>
                <br>
                
            </div>
    </body>
</html>

