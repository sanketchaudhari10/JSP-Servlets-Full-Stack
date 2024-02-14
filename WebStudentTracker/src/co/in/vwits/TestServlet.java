package co.in.vwits;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
//		step0: setContentType
		response.setContentType("text/html");
		
//		step1: setup printwriter
		PrintWriter out = response.getWriter();
		
//		step2: Get a connection to the database
		Connection myConn = null;
		Statement smt = null;
		ResultSet rs = null;
		
		try {
			myConn = dataSource.getConnection();
			
//		step3: create a sql statement
			String sql = "select * from students";
			smt = myConn.createStatement();
//		step4: execute sql statement
			rs = smt.executeQuery(sql);
			
//		step5: Process the result set
			while(rs.next()) {
				String email = rs.getString("email");
				out.println(email+ "<br>");
			}
		}
		catch (Exception exc) {
			 exc.printStackTrace();
		}

	}

	

}
