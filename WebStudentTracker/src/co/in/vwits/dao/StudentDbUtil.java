package co.in.vwits.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import co.in.vwits.model.Student;

public class StudentDbUtil {

	private DataSource dataSource;
	
	public  StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
//		try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            throw new IllegalStateException("Could not load JDBC driver class", e);
//        }
	}
	
	public List<Student> getStudents() throws Exception{
		
				
		List<Student> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement smt = null;
		ResultSet rs = null;
		
				
		try {
			myConn = dataSource.getConnection();
			smt = myConn.createStatement();
			String sql = "select * from students order by id";
			rs = smt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString("email");
				
				Student temp = new Student(id,firstName,lastName, email);
				students.add(temp);
			}
			return students;
		}catch(Exception exc) {
			exc.printStackTrace();
		}finally {
			close(myConn,smt,rs);
		}
		
		return students;
	}

	

	public void addStudent(Student st) {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement smt = null;		
		
		try {
			myConn = dataSource.getConnection();
			System.out.println("inside try");
			String sql = "insert into students(first_name, last_name,email) values(?,?,?)";
			smt = myConn.prepareStatement(sql);
			
			smt.setString(1,st.getFirstName());	
			smt.setString(2,st.getLastName());
			smt.setString(3,st.getEmail());
			int i = smt.executeUpdate();
			if(i>0) {
				System.out.println("Inserted");
			}else {
				System.out.println("Not Inserted");
			}
//			myConn.commit(); // not required since autocommit is true
		}catch(Exception exc) {
			exc.printStackTrace();
		}finally {
			close(myConn,smt,null);
		}
	}

	private void close(Connection myConn, Statement smt, ResultSet rs) {
		// TODO Auto-generated method stub
		try {
			if(rs!=null) {
				rs.close();
			}
			if(smt!=null) {
				smt.close();
			}
			if(myConn!=null) {
				myConn.close();
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	public Student selectStudentById(Integer studentId) {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement smt = null;
		ResultSet rs = null;				
		Student temp = new Student(studentId);
		try {
			myConn = dataSource.getConnection();
			
			String sql = "select * from students where id=?";
			smt = myConn.prepareStatement(sql);
			smt.setInt(1, studentId);
			rs = smt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString("email");
				
				temp.setEmail(email);
				temp.setFirstName(firstName);
				temp.setLastName(lastName);
				
			}
			return temp;
		}catch(Exception exc) {
			exc.printStackTrace();
		}finally {
			close(myConn,smt,rs);
		}
		
		return null;
		
		
	}

	public void updateStudent(Student st) {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement smt = null;		
		
		try {
			myConn = dataSource.getConnection();
			System.out.println("inside updateStudent");
			String sql = "UPDATE students SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
			smt = myConn.prepareStatement(sql);
			
			smt.setString(1,st.getFirstName());	
			smt.setString(2,st.getLastName());
			smt.setString(3,st.getEmail());
			smt.setInt(4, st.getId());
			System.out.println(smt);
			int i = smt.executeUpdate();
			if(i>0) {
				System.out.println("Updated");
			}else {
				System.out.println("Not Updated");
			}
//			myConn.commit(); // not required since autocommit is true
		}catch(Exception exc) {
			exc.printStackTrace();
		}finally {
			close(myConn,smt,null);
		}
	}

	public void deleteStudentById(int id) {
		// TODO Auto-generated method stub
		Connection myConn = null;
		PreparedStatement smt = null;		
		
		try {
			myConn = dataSource.getConnection();
			System.out.println("inside updateStudent");
			String sql = "DELETE FROM students where id=?";
			smt = myConn.prepareStatement(sql);
			
			smt.setInt(1,id);	
			
			int i = smt.executeUpdate();
			if(i>0) {
				System.out.println("Deleted");
			}else {
				System.out.println("Not Deleted");
			}
//			myConn.commit(); // not required since autocommit is true
		}catch(Exception exc) {
			exc.printStackTrace();
		}finally {
			close(myConn,smt,null);
		}
	}

	
	
}
