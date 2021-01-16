<%@ include file = "jspHeader.jsp"%>

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
            <h1>Smart Care Surgery System Add New User</h1>
            <h2>${headline}</h2>

            <c:if test = "${empty param.userType}">
                <form action="${pageContext.request.contextPath}/addUser.do" method="post">
                    <select id="userType" name="userType">
                        <option value="1">Patient</option>
                        <option value="2">Doctor</option>
                        <option value="3">Nurse</option>
                    </select>
                    <input type="submit" value="Submit" />
                </form>
            </c:if>

            <c:if test = "${param.userType eq '1'}">
                <c:import url="/objects/addPatient.jsp"/>
            </c:if>

            <c:if test = "${param.userType eq '2' or param.userType eq '3'}">
                <c:import url="/objects/addEmployee.jsp"/>
            </c:if>
            <br>
            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Back" />
            </form>

        </div>
    </body>
</html>

