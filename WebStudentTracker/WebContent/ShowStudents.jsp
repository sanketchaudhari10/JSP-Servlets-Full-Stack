<!-- <%@ page import="java.util.*, co.in.vwits.model.*, co.in.vwits.controller.*, co.in.vwits.dao.* , co.in.vwits.jdbc.* "%>-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Tracker App</title>
<style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<!--  1)  Scriplet tag
	<%
		// List<Student> students = (List<Student>) request.getAttribute("studentsList");
	%>
	
	 -->
	 <!-- USing 2) JSTL tags (with jstl we do not need this step, its available)-->
	 
	 <input type="button" onclick="window.location.href='add-student.jsp'; return false;"
class="add-student-button" value="Add Student">
	 
	<table>
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:choose>
	        <c:when test = "${not empty studentsList}" >
	        	<c:forEach var = "tempStudent" items ="${studentsList}"> 
	        	
	        	<!-- Setting up link for each student -->
	        		<!-- for update --> 
	        	<c:url var = "updateLink" value="StudentControllerServlet">
		        	<c:param name="command" value="load"/>
		        	<c:param name = "studentId" value="${tempStudent.getId() }"/>
	        	</c:url>
	        	
	        	<!-- for delete --> 
	        	<c:url var = "deleteLink" value="StudentControllerServlet">
		        	<c:param name="command" value="delete"/>
		        	<c:param name = "studentId" value="${tempStudent.getId() }"/>
	        	</c:url>
	        	<tr>
	            		<td>${ tempStudent.getFirstName()}</td>
	            		<td>${ tempStudent.getLastName()}</td>
	            		<td>${ tempStudent.getEmail()}</td>
	            		<td> <a href="${updateLink}">Update</a>&nbsp;|&nbsp;
	            			<a href="${deleteLink }" onclick="if (!confirm('Are you sure to delete: $(tempStudent.getId())?')">Delete</a>
	            		</td>
	            </tr>      		
	        		
	        		
	        	</c:forEach>
	        </c:when>
	        <c:otherwise>
	        	<h3>List is empty</h3>
	        </c:otherwise>
        </c:choose>    
            
        </tbody>
    </table>
</body>
</html>