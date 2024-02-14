<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Student</title>
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>
<body>

<h2 class="title">Add Student</h2>



<div id="Container" class="hidden">
    <form action="StudentControllerServlet" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br><br>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
		
		<input type="hidden" name="command" value="Add">
        <input type="submit" value="Submit">
    </form>
    <div style ="clear: both;">
	<p>
		<a href="studentlist">Back to List</a>
	</p>
	
</div>
</div>



</body>
</html>
