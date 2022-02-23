<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>

<head>
	<title>List Students</title>

	<!-- reference our style sheet -->

	<link type="text/css"
				rel="stylesheet"
				href="${pageContext.request.contextPath}/resources/css/style.css"/>

</head>

<body>

<div id="wrapper">
	<div id="header">
		<h2>SDM - Student Details Manager</h2>
	</div>
</div>

<div id="container">

	<div id="content">

		<!-- put new button: Add Customer -->

		<input type="button" value="Add Student"
					 onclick="window.location.href='showFormForAdd'; return false;"
					 class="add-button"
		/>

		<!--  add our html table here -->

		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Roll No</th>
				<th>Mobile</th>
				<th>class</th>
				<th>Action</th>
			</tr>

			<!-- loop over and print our customers -->
			<c:forEach var="tempStudent" items="${student}">

				<!-- construct an "update" link with customer id -->
				<c:url var="updateLink" value="/student/showFormForUpdate">
					<c:param name="studentId" value="${tempStudent.id}"/>
				</c:url>

				<!-- construct an "delete" link with customer id -->
				<c:url var="deleteLink" value="/student/delete">
					<c:param name="studentId" value="${tempStudent.id}"/>
				</c:url>

				<tr>
					<td> ${tempStudent.first_name} </td>
					<td> ${tempStudent.last_name} </td>
					<td> ${tempStudent.email} </td>
					<td> ${tempStudent.roll_number} </td>
					<td> ${tempStudent.mobile_number} </td>
					<td> ${tempStudent.classNumber} </td>
					<td>
						<!-- display the update link -->
						<a href="${updateLink}">Update</a>
						|
						<a href="${deleteLink}"
							 onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
					</td>

				</tr>

			</c:forEach>

		</table>

	</div>

</div>


</body>

</html>









