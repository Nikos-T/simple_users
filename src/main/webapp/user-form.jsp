<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- bootstrap -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<title>Simple User Application</title>
</head>
<body>
<header>
  <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
  	<div><a href="http://localhost:8080/simple_users/" class="navbar-brand">Simple User Application</a></div>
	<ul class="navbar-nav">
	  <li><a href="<%=request.getContextPath()%>/User?action=list" class="nav-link">Users</a></li>
	</ul>
  </nav>
</header>
<br>
<div class="container col-md-5">
  <div class="card">
  	<div class="card-body">
  	<!-- TODO: two c:if -->
  	  <form action="?action=${user == null ? 'insert': 'update&userid='.concat(user.id)}" method="post">
  	  	<h2>${user == null? 'Add New User' : 'Update User'}</h2>
		<fieldset>
		  <legend>User Details</legend>
		  <fieldset class="form-group">
			<label>First Name</label>
			<input type="text" value="<c:out value='${user.firstName}' />" class="form-control" name="firstName" required="required">
		  </fieldset>
		  <fieldset class="form-group">
			<label>Last Name</label>
			<input type="text" value="<c:out value='${user.lastName}' />" class="form-control" name="lastName" required="required">
		  </fieldset>
		  <fieldset>
			<label>Gender</label>
			<!-- TODO: with for -->
			<select name="gender" required="required">
			  <option disabled="disabled" ${user == null ? 'selected="selected"' : '' }> -- select a value -- </option>
			  <option value="FEMALE" ${user.gender == 'FEMALE'? 'selected="selected"' : ''}>female</option>
			  <option value="MALE" ${user.gender == 'MALE'? 'selected="selected"' : ''}>male</option>
			  <option value="OTHER" ${user.gender == 'OTHER'? 'selected="selected"' : ''}>other</option>
			</select>
		  </fieldset>
		  <fieldset>
			<label>Birth Date</label>
			<input type="date" value="${user.birthDate}" class="form-control" name="birthDate" required="required" pattern="dd/MM/yyyy">
		  </fieldset>
  	  	</fieldset>
  	  	<fieldset>
  	  	  <legend><br/>Address</legend>
  	  	  <fieldset>
	  	    <label>Home Address</label>
	  	    <input type="text" value="${user.getHomeAddress().getAddress()}" class="form-control" name="homeAddress">
  	  	  </fieldset>
  	  	  <fieldset>
	  	    <label>Work Address</label>
	  	    <input type="text" value="${user.getWorkAddress().getAddress()}" class="form-control" name="workAddress">
  	  	  </fieldset>
  	  	</fieldset>
  	  	<br/><br/>
  	  	<div class="d-flex flex-row-reverse">
  	  	  <button type="submit" class="btn btn-success justify-right">Save User</button>
  	  	</div>
  	  </form>
  	</div>	<!-- ./card-body -->
  </div>	<!-- ./card -->
</div>		<!-- ./container -->
</body>
</html>