<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>

<head>
	<title>Save Student</title>

	<link type="text/css"
				rel="stylesheet"
				href="${pageContext.request.contextPath}/resources/css/style.css">

	<link type="text/css"
				rel="stylesheet"
				href="${pageContext.request.contextPath}/resources/css/add-customer-style.css">
</head>

<body>

<div id="wrapper">
	<div id="header">
		<h2>SDM - Student Details Manager</h2>
	</div>
</div>

<div id="container">
	<h3>Save Customer</h3>

	<form:form action="saveStudent" modelAttribute="student" method="POST">

		<!-- need to associate this data with customer id -->
		<form:hidden path="id"/>

		<table>
			<tbody>
			<tr>
				<td><label>First name:</label></td>
				<td><form:input path="first_name"/></td>
			</tr>

			<tr>
				<td><label>Last name:</label></td>
				<td><form:input path="last_name"/></td>
			</tr>

			<tr>
				<td><label>Email:</label></td>
				<td><form:input path="email"/></td>
			</tr>

			<tr>
				<td><label>Roll Number:</label></td>
				<td><form:input path="roll_number"/></td>
			</tr>

			<tr>
				<td><label>Mobile Number:</label></td>
				<td><form:input path="mobile_number"/></td>
			</tr>

			<tr>
				<td><label>Class:</label></td>
				<td><form:input path="classNumber"/></td>
			</tr>

			<tr>
				<td><label></label></td>
				<td><input type="submit" value="Save" class="save"/></td>
			</tr>


			</tbody>
		</table>


	</form:form>

	<div style="clear; both;"></div>

	<p>
		<a href="${pageContext.request.contextPath}/student/list">Back to List</a>
	</p>

</div>

</body>

</html>










