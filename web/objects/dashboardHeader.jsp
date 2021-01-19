<%-- 
    Document   : dashboardHeader
    Created on : 15-Jan-2021, 15:16:07
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
        <header class="header">
            <h3>Welcome, ${currentUser.firstName} ${currentUser.surName}</h3>
            <div class="inline">
                <p>You are logged in as a${loggedInAs}</p> <br>
                <form class="logout_form" action="${pageContext.request.contextPath}/logout.do" method="post">
                    <input type="submit" value="Logout" />
                </form>
            </div>

        </header>
    </body>
</html>
