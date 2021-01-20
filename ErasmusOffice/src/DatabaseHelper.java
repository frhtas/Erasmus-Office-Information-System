import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHelper {
	public static final String USER = "postgres";
	public static final String PASSWORD = "1234";
	private Connection conn;
	
	public DatabaseHelper() {
        try {
			this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/erasmus_db", USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Rollback function for "transaction stopped" error
	public void rollBack() {
		try {
			this.conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get all department names from DB
	public String[] getDepartmentNames() {
		ArrayList<String> dept_names = new ArrayList<>();
		String query = "SELECT * FROM dept_names";
		
		try {
			PreparedStatement p = conn.prepareStatement(query);
			ResultSet r = p.executeQuery();
			
			while(r.next()) {
				String name = r.getString(1);
				dept_names.add(name);
			}
			p.close();
		} catch (SQLException e) {
			e.printStackTrace();
			rollBack();
		}
		
		Object[] objectArray = dept_names.toArray();
		String[] dept_names_string = Arrays.copyOf(objectArray, objectArray.length, String[].class);
		
		return dept_names_string;
	}
	
	
	// Get all university names from DB
		public String[] getUniversityNames() {
			ArrayList<String> uni_names = new ArrayList<>();
			String query = "SELECT * FROM uni_names";
			
			try {
				PreparedStatement p = conn.prepareStatement(query);
				ResultSet r = p.executeQuery();
				
				while(r.next()) {
					String name = r.getString(1);
					uni_names.add(name);
				}
				p.close();
			} catch (SQLException e) {
				e.printStackTrace();
				rollBack();
			}
			
			Object[] objectArray = uni_names.toArray();
			String[] uni_names_string = Arrays.copyOf(objectArray, objectArray.length, String[].class);
			
			return uni_names_string;
		}
	
	
	// Get all university which given department have agreement with
	public ArrayList<Object[]> getUniversities(String dname) throws SQLException {
		ArrayList<Object[]> universities = new ArrayList<>();
		String query = "SELECT u.unino, u.uname, u.country, u.ugrant\n"
					 + "FROM department d, university u, agreement a\n"
					 + "WHERE d.dno = a.deptno AND u.unino = a.unino AND d.dname = '" + dname + "'\n"
					 + "ORDER BY u.country";
		
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while(r.next()) {				
			int unino = r.getInt(1);
			String uname = r.getString(2);
			String country = r.getString(3);
			int ugrant = r.getInt(4);
			
			Object[] university = {unino, uname, country, ugrant};
			universities.add(university);
		}
		p.close();
		
		return universities;
	}
	
	
	// Get all students which study at given department
	public ArrayList<Object[]> getStudents(String dname) throws SQLException {
		ArrayList<Object[]> students = new ArrayList<>();
		String query = "SELECT s.stdno, s.fname, s.lname, s.agno, s.examscore, ch.chosenterm, u.uname, s.isgrant\n"
					 + "FROM department d, student s, choice ch, university u\n"
					 + "WHERE d.dno = s.deptno AND s.stdno = ch.stdno AND ch.unino = u.unino AND d.dname = '" + dname + "'\n"
					 + "ORDER BY s.examscore DESC";
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while(r.next()) {
			String stdno = r.getString(1);
			String sname = r.getString(2) + " " + r.getString(3);
			String agno = r.getString(4);
			String examscore = r.getString(5);
			String chosenterm = r.getString(6);
			String uname = r.getString(7);
			boolean isgrant = r.getBoolean(8);		
			String grant;
			if (isgrant)
				grant = "Hibeli";
			else
				grant = "Hibesiz";
			
			Object[] student = {stdno, sname, agno, examscore, chosenterm, uname, grant};
			students.add(student);
		}
		p.close();
		
		return students;
	}
	
	
	// Get students by given chosenterm and chosen university
	public ArrayList<Object[]> getStudentsWithIntersect(String chosenterm, String uname) throws SQLException {
		ArrayList<Object[]> students = new ArrayList<>();
		String query = "SELECT s.stdno, s.fname, s.lname, s.agno, s.examscore, s.isgrant\n"
					 + "FROM student s, choice ch\n"
					 + "WHERE s.stdno = ch.stdno AND ch.chosenterm = '" + chosenterm + "'\n"
					 + "INTERSECT\n"
					 + "SELECT s2.stdno, s2.fname, s2.lname, s2.agno, s2.examscore, s2.isgrant\n"
					 + "FROM student s2, choice ch2\n"
					 + "WHERE s2.stdno = ch2.stdno\n"
					 + "	AND ch2.unino = (SELECT unino FROM university WHERE uname = '" + uname + "')";
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while(r.next()) {
			String stdno = r.getString(1);
			String sname = r.getString(2) + " " + r.getString(3);
			String agno = r.getString(4);
			String examscore = r.getString(5);
			boolean isgrant = r.getBoolean(6);		
			String grant;
			if (isgrant)
				grant = "Hibeli";
			else
				grant = "Hibesiz";
			
			Object[] student = {stdno, sname, agno, examscore, chosenterm, uname, grant};
			students.add(student);
		}
		p.close();
		
		return students;
	}
	
	
	// Get departments which its avg examscore bigger than given examscore
	public ArrayList<Object[]> getBestDepartmentsByExam(int examscore) throws SQLException {
		ArrayList<Object[]> departments = new ArrayList<>();
		String query = "SELECT d.dname, AVG(s.examscore)\n"
					 + "FROM student s, department d\n"
					 + "WHERE s.deptno = d.dno\n"
					 + "GROUP BY d.dname\n"
					 + "HAVING AVG(s.examscore) > " + examscore + "\n"
					 + "ORDER BY AVG(s.examscore) DESC";
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while(r.next()) {
			String dname = r.getString(1);
			String avg_examscore = r.getString(2);
			
			Object[] dept = {dname, avg_examscore};
			departments.add(dept);
		}
		p.close();
		
		return departments;
	}
	
	
	// Insert university to the DB
	public void insertUniversity(Object[] newUni) throws SQLException {
		String query = "INSERT INTO university VALUES(nextval('seq'), '" + newUni[0] + "', '" + newUni[1] + "', " + newUni[2] + ")";
		
		Statement s = conn.createStatement();
		s.executeUpdate(query);
		conn.setAutoCommit(false);
        conn.commit();
        s.close();
	}
	

	// Insert agreement to the DB
	public void insertAgreement(String dname, String uname) throws SQLException {
		String query = "INSERT INTO agreement(deptno, unino)\n"
				 	 + "SELECT d.dno, u.unino\n"
				 	 + "FROM department d, university u\n"
				 	 + "WHERE d.dname = '" + dname + "' AND u.uname = '" + uname + "'";
		Statement s = conn.createStatement();
		s.executeUpdate(query);
		conn.setAutoCommit(false);
        conn.commit();
        s.close();
	}
	
	
	// Delete agreement from DB
	public int deleteAgreement(String dname, String uname) throws SQLException {
		String query = "DELETE FROM agreement\n"
				 	 + "WHERE deptno = (SELECT dno FROM department WHERE dname = '" + dname + "')\n"
				 	 + "   AND unino = (SELECT unino FROM university WHERE uname = '" + uname + "')";	
		Statement s = conn.createStatement();
		int isExist = s.executeUpdate(query);
		conn.setAutoCommit(false);
        conn.commit();
        s.close();
        return isExist;
	}
	
	
	// Update exam score of given stdno
	public int updateExamscore(String stdno, int examscore) throws SQLException {
		String query = "UPDATE student\n"
					 + "SET examscore = " + examscore + "\n"
					 + "WHERE stdno = '" + stdno + "'\n";	
		Statement s = conn.createStatement();
		int isExist = s.executeUpdate(query);
		conn.setAutoCommit(false);
		conn.commit();
		s.close();
		return isExist;
	}
	
	
	// Update chosen term of given stdno
	public int updateChosenterm(String stdno, String chosenterm) throws SQLException {
		String query = "UPDATE choice\n"
					 + "SET chosenterm = '" + chosenterm + "'\n"
					 + "WHERE stdno = '" + stdno + "'\n";	
		Statement s = conn.createStatement();
		int isExist = s.executeUpdate(query);
		conn.setAutoCommit(false);
		conn.commit();
		s.close();
		return isExist;
	}
	
	
	// Get best students for exam score by numberOfStudents
	public ArrayList<Object[]> getBestStudents(int numberOfStudents) throws SQLException {
		ArrayList<Object[]> bestStudents = new ArrayList<>();
		String query = "SELECT get_best_students(" + numberOfStudents + ")";
		
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while(r.next()) {
			String str = r.getString(1);
			str = str.replace("\"", "");
			str = str.replace("\\", "");				
			Pattern pattern = Pattern.compile("\\((.*?)\\)");
		    Matcher m = pattern.matcher(str);
		    ArrayList<String> list = new ArrayList<String>();

		    while(m.find()) {
		    	list.add(m.group(1));
		    	String[] splitArray = m.group(1).split(",");
		    	Object[] student = {splitArray[0] + " " + splitArray[1], splitArray[2], splitArray[3]};
				bestStudents.add(student);
		    }
		}
		p.close();

		return bestStudents;	
	}
	
	
	// Update grant of universities in given country
	public int updateGrant(String country, int newGrant) throws SQLException {
		String query = "SELECT update_ugrant_of_country('" + country + "', " + newGrant + ")";	
		int isExist = 0;
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			isExist = r.getInt(1);
			conn.setAutoCommit(false);
			conn.commit();
			p.close();
		}	
		return isExist;
	}
	
	
	// Get students avg examscores and agnos who going universities in given country
	public Object[] getStudentsAvgScores(String country) throws SQLException {
		Object[] countryStdInfo = new Object[3];
		String query = "SELECT get_average_agno_and_score('" + country + "')";
		
		PreparedStatement p = conn.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while(r.next()) {
			String str = r.getString(1);
			str = str.replace("\"", "");
			str = str.replace("\\", "");				
			Pattern pattern = Pattern.compile("\\((.*?)\\)");
		    Matcher m = pattern.matcher(str);
		    ArrayList<String> list = new ArrayList<String>();
		    
		    while(m.find()) {
		    	list.add(m.group(1));
		    	String[] splitArray = m.group(1).split(",");
		    	countryStdInfo[0] = splitArray[0];
		    	countryStdInfo[1] = splitArray[1];
		    	countryStdInfo[2] = splitArray[2];
		    }
		}
		p.close();
			
		return countryStdInfo;	
	}
	
}
