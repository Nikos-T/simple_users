<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
</head>
<body>

<div class="d-flex flex-row justify-content-center">
  <h1>Error</h1>
</div>
<div class="d-flex flex-row justify-content-center">
  <h2><%=exception.getMessage() %></h2>
</div>

</body>
</html>