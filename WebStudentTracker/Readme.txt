
starts from StudentControllerServlet
	init - to initialize the servlet
		- inside init we create the dao(StudentDbUtil) object
		
	- as soon as the servlet endpoint is hit(here / for /StudentControllerServlet as mentioned in web.xml)
		the doGet method is executed.
	-  thus the flow is
	- the context.xml consists of the db configurations
	- on fetching the data from db then we dispatch it to the showStudents.jsp using RequestDisatcher.
	
* the name="command" and value="" is used in input tag to tell the servlet what operation is intented.
* the TestStudent is a non linked file to the project

To run:
	1. download
	2. setup tomcat 
	3. add user and pwd in context.xml for sql workbench and setup db table
	4. right click on project or controller servlet -> run as -> run on server.