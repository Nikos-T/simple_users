<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">

<!-- bootstrap11 -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<title>Simple Users</title>
</head>

<body>

<div class="container">
	<div class="d-flex flex-column justify-content-between">
		<div class="d-flex flex-row justify-content-center">
			<div class="p-2"><h2>Welcome to Simple Users!</h2></div>
		</div>
		<div class="d-flex flex-row justify-content-center">
			<div class="p-2"><a href="<%=request.getContextPath()%>/User"><button type="button" class="btn btn-primary">Display Users</button></a></div>
		</div>
		<div class="d-flex flex-row justify-content-center">
			<div class="p-2">or</div>
		</div>
		<div class="d-flex flex-row justify-content-center">
			<div class="p-2"><a href="<%=request.getContextPath()%>/User?action=new"> <button type="button" class="btn btn-success">Register New User</button></a></div>
		</div>
	</div>
</div>

</body>
</html>