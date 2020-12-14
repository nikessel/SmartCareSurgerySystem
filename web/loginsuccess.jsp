<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h1>You have successfully logged in</h1>
                
      <%out.println(request.getAttribute("name"));%> 
      
	</div>
</body>
</html>