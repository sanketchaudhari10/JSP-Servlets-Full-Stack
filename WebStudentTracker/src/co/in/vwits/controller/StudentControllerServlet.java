package co.in.vwits.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import co.in.vwits.dao.StudentDbUtil;
import co.in.vwits.model.Student;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker") // dependency injection (name is matched with WebContent/META-INF/context.xml
	private DataSource datasource;

	@Override
	public void init() throws ServletException {  //executed when tomcat is intialized (like a constructor for a servlet)
		// TODO Auto-generated method stub
		super.init();
		
//		 create our student dbutil obj and pass in the conn pool/ datasource
		try {
			 studentDbUtil = new StudentDbUtil(datasource);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		List students in MVC fashion
		try {
//			1. Read the command parameter
			String theCommand = request.getParameter("command");
			System.out.println(theCommand);
			if(theCommand==null) {
				theCommand = "List";
			}
			
			switch(theCommand) {
			case "List":
				listStudents(request,response);
				break;
			
			case "load":
				System.out.println("In load switch"); 
				loadStudent(request,response);
				break;
			case "update":
				updateStudent(request,response);
				break;
			case "delete":
				deleteStudent(request,response);
			default:
				listStudents(request,response);
				break;				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String sId = request.getParameter("studentId");
		int id = Integer.parseInt(sId);
		studentDbUtil.deleteStudentById(id);
		
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("in addStudent");
//		Read the data from form submitted
		String stId = request.getParameter("id");
		int id = Integer.parseInt(stId);
		String firstname = request.getParameter("firstName");
		String lastname = request.getParameter("lastName");
		String email = request.getParameter("email");
		
//		create a new student
		Student temp = new Student(id,firstname,lastname,email);
		
//		add student to db
		studentDbUtil.updateStudent(temp);
		
//		send back to main page
		response.sendRedirect("StudentControllerServlet?Command=List");
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("in loadStudent");
		String studentIdString = request.getParameter("studentId");
		int studentId = Integer.parseInt(studentIdString);
		
//		Read the data from form db
		Student temp = studentDbUtil.selectStudentById(studentId);
		
//		passing data to update form jsp
		request.setAttribute("student", temp);
		
		// forwarding to update page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request,response);
				
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("in addStudent");
//		Read the data from form submitted
		String firstname = request.getParameter("firstName");
		String lastname = request.getParameter("lastName");
		String email = request.getParameter("email");
		
//		create a new student
		Student st = new Student(firstname,lastname,email);
		
//		add student to db
		studentDbUtil.addStudent(st);
		
//		send back to main page(student list)
//		SEND AS REDIRECT to avoid multiple-browser reload issue
//		listStudents(request,response);
		response.sendRedirect(request.getContextPath()+"/StudentControllerServlet?command=List");
		
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
	
		System.out.println("In doPost");
		try {
//			1. Read the command parameter
			String theCommand = request.getParameter("command");
			System.out.println(theCommand);
			if(theCommand==null) {
				theCommand = "List";
			}
			
			switch(theCommand) {			
			
			case "Add":
				System.out.println("In Add switch"); 
				addStudent(request,response);
				break;
			
			default:
				listStudents(request,response);
				break;				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
//		get students from db util		
		List<Student> students = studentDbUtil.getStudents();
		System.out.println(students);
//		add students to the request
		request.setAttribute("studentsList", students);
		
//		send to JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("ShowStudents.jsp");
		dispatcher.forward(request,response);
	}

}
