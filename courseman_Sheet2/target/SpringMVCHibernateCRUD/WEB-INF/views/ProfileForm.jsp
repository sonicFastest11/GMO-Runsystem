<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<style>
.errors{
color: red;
font-style: italic;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new Contact</title>
</head>
<body>
	    
	<div align="center">
		        	
		<h1>New/Edit Profile</h1>
		<%-- <h1>${message }</h1>
		<h1>${message1 }</h1>
		         --%>
		<form:form action="saveProfile" method="post"
			modelAttribute="profile">
			<table>
				  <tr>
					<td>ID</td>
					<td><form:input path="id" readonly="true"/></td>
				</tr>
				<tr>
					<td>Name:</td>
					<td><form:input path="name" placeholder="name"/></td>
					<td><form:errors path="name" cssClass="errors"  /></td>
				</tr>
				<tr>
					<td>Address:</td>
					<td><form:input path="address" placeholder="address"/></td>
				</tr>
				<tr>
					<td>Dob:</td>
					<td><form:input path="dob" placeholder="dd/MM/yyyy"/></td>
					<td><form:errors path="dob" cssClass="errors"  /></td>
				</tr><tr>
					<td>Sex:</td>
					<td><form:input path="gender" placeholder="nam" /></td>
					<td><form:errors path="gender" cssClass="errors" /></td>
				</tr><tr>
					<td>Phone:</td>
					<td><form:input path="phone" placeholder="0987654321"/></td>
					<td><form:errors path="phone" cssClass="errors" /></td>
				</tr><tr>
					<td>Email:</td>
					<td><form:input path="email" placeholder="son@gmail.com"/></td>
				</tr><tr>
					<td>ClassCode:</td>
					<td><form:input path="classCode" placeholder="4C16"/></td>
				</tr>
				<tr>
					<td>Status:</td>
					<td><form:input path="status" placeholder="4C16"/></td>
				</tr>
				<tr>
					<td>User_ID</td>
					<td><form:input path="user.roleid.role_name" readonly="true"/></td>
					<td><form:errors path="name" cssClass="errors"  /></td>
				</tr> 
				
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Save"></td>
				</tr>
			</table>
        </form:form>   
		    
	</div>
</body>
</html>