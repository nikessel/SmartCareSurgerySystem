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
            <h1>Smart Care Surgery System Add New User</h1>
            <h2>${headline}</h2>

            <c:if test = "${empty userType}">
                <form action="${pageContext.request.contextPath}/addUser.do" method="post">
                    <select id="userType" name="userType">
                        <option value="1">Patient</option>
                        <option value="2">Doctor</option>
                        <option value="3">Nurse</option>
                    </select>
                    <input type="submit" value="Submit" />
                </form>
            </c:if>

            <c:if test = "${not empty userType}">
                <form action="${pageContext.request.contextPath}/addUser.do" method="post">

                    <input id="lookupPostcode" name="lookupPostcode" required="required" type="text" placeholder="Lookup postcode"/>
                    <input type="submit" value="Submit" />

                </form>
                <br>
                <form action="${pageContext.request.contextPath}/addUser.do" method="post">
                    <h3>Credentials</h3>
                    <input id="username" name="username" required="required" type="text" placeholder="Username" value="${username}"/>
                    <input id="password" name="password" required="required" type="password" placeholder="Password"/>
                    <input id="repeatPassword" name="repeatPassword" required="required" type="password" placeholder="Repeat password"/>
                    <h3>Personal information</h3>
                    <input id="firstName" name="firstName" required="required" type="text" placeholder="First name" value="${firstName}"/>
                    <input id="surName" name="surName" required="required" type="text" placeholder="Sur name" value="${surName}"/><br><br>
                    <label for="dateOfBirth">Date of birth</label>
                    <input id="dateOfBirth" name="dateOfBirth" required="required" type="date"/>
                    <c:if test = "${userType eq '1'}">
                        <input type="checkbox" name="insured" value="true"/>
                        <label for="insured">Insured by NHS?</label>
                    </c:if>

                    <c:if test = "${userType eq '2' or userType eq ''}">
                        <input type="checkbox" name="full_time" value="true"/>
                        <label for="full_time">Add as a full-time employee?</label>
                    </c:if>
                    <h4>Address</h4>
                    <input id="postcode" name="postcode" required="required" type="text" placeholder="Postcode" value="${address.postcode}"/>
                    <input id="streetNumber" name="streetNumber" required="required" type="text" placeholder="Street number"/>
                    <input id="addressLine1" name="addressLine1" required="required" type="text" placeholder="Street name" value="${address.addressLine1}"/>
                    <input id="addressLine2" name="addressLine2" type="text" placeholder="Address line 2" value="${address.addressLine2}"/>
                    <input id="county" name="county" required="required" type="text" placeholder="County" value="${address.county}"/>
                    <input id="town" name="town" required="required" type="text" placeholder="Town" value="${address.town}"/>
                    <input id="telephoneNumber" name="telephoneNumber" required="required" type="text" placeholder="Telephone number" value="${address.telephoneNumber}"/>
                    <br><br>
                    <input type="submit" value="Submit" />
                </form>
                <br>
            </c:if>
            <br>
            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Back" />
            </form>

        </div>
    </body>
</html>

