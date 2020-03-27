<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/user-list.js" language="javascript" type="application/javascript"></script> 
<!-- bootstrap -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<!-- icons -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

<title>Simple User Application</title>
</head>
<body>

<header>
  <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
  	<div><a href="http://localhost:8080/simple_users/" class="navbar-brand">Simple User Application</a></div>
  	<ul class="navbar-nav">
  	  <li><a href="<%=request.getContextPath() %>/User?action=list" class="nav-link">Users</a></li>
  	</ul>
  </nav>
</header>
<br>
<div class="row">
  <div class="container">
  	<h3 class="text-center">List of Users</h3>
  	<hr>
  	
  	
  	<table class="table table-bordered align-middle">
  	<caption>Found ${listUser.size()} users.</caption>
  	  <thead>
  	  	<tr><th class="text-center">ID</th><th>First Name</th><th>Last Name</th><th>Gender</th><th>Birth Date</th></tr>
  	  </thead>
  	  <tbody>
  	  	<c:forEach var="user" items="${listUser}">
  	  	  <tr id="userid_${user.id}">
  	  		<td class="text-center"><c:out value="${user.id}" /></td>
  	  		<td><c:out value="${user.firstName}" /></td>
  	  		<td><c:out value="${user.lastName}" /></td>
  	  		<td><c:out value="${fn:toLowerCase(user.gender)}" /></td>
  	  		<td><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy" /></td>
  	  	  	<td class="d-flex justify-content-center">
  	  	  	  <button type="button" class="btn btn-info btn-sm" style="height: 2rem;" onclick="showAddresses(${user.id})">&nbsp;&nbsp;<i class="fas fa-info"></i>&nbsp;&nbsp;</button>
  	  	  	  <div>&nbsp;&nbsp;</div>
  	  	  	  <a href="<%=request.getContextPath() %>/User?action=edit&userid=${user.id}"><button type="button" class="btn btn-primary btn-sm" style="height: 2rem;"><i class="fas fa-user-edit"></i></button></a>
  	  	  	  <div>&nbsp;&nbsp;</div>
  	  	  	  <a href="<%=request.getContextPath() %>/User?action=delete&userid=${user.id}"><button type="button" class="btn btn-danger btn-sm" style="height: 2rem;">&nbsp;<i class="fas fa-trash-alt"></i>&nbsp;</button></a>
  	  	  </tr>
  	  	</c:forEach>
  	  </tbody>
  	</table>
  	<br>
  	<div class="container text-right"><a href="<%=request.getContextPath() %>/User?action=new" class="btn btn-success">Add New User</a></div>
  </div>	<!-- ./container -->
</div>		<!-- ./row -->
</body>
</html>