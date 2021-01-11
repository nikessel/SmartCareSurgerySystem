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
            <h1>Smart Care Surgery System Add New User</h1>
            <h2>${message}</h2>
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
                <input id="firstName" name="firstName" required="required" type="text" placeholder="First name" value="${sessionScope.thisPatient.firstName}"/>
                <input id="surName" name="surName" required="required" type="text" placeholder="Sur name" value="${sessionScope.thisPatient.surName}"/><br><br>
                <label for="dateOfBirth">Date of birth</label>
                <input id="dateOfBirth" name="dateOfBirth" required="required" type="date"/>
                <input type="checkbox" name="insured" value="true"/>
                <label for="insured">Insured by NHS?</label>
                <h4>Address</h4>
                <input id="postcode" name="postcode" required="required" type="text" placeholder="Postcode" value="${sessionScope.thisAddress.postcode}"/>
                <input id="streetNumber" name="streetNumber" required="required" type="text" placeholder="Street number" value="${sessionScope.streetNumber}"/>
                <input id="addressLine1" name="addressLine1" required="required" type="text" placeholder="Street name" value="${sessionScope.thisAddress.addressLine1}"/>
                <input id="addressLine2" name="addressLine2" type="text" placeholder="Address line 2" value="${sessionScope.thisAddress.addressLine2}"/>
                <input id="county" name="county" required="required" type="text" placeholder="County" value="${sessionScope.thisAddress.county}"/>
                <input id="town" name="town" required="required" type="text" placeholder="Town" value="${sessionScope.thisAddress.town}"/>
                <input id="telephoneNumber" name="telephoneNumber" required="required" type="text" placeholder="Telephone number" value="${sessionScope.thisAddress.telephoneNumber}"/>
                <br><br>
                <input type="submit" value="Submit" />
            </form>
            <br>
            <br>
            <form action="${pageContext.request.contextPath}/logout.do" method="post">
                <input type="submit" value="Back" />
            </form>

        </div>
    </body>
</html>

