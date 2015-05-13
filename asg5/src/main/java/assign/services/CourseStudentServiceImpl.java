package assign.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.ws.rs.PathParam;

import org.apache.commons.dbcp.BasicDataSource;

import assign.domain.Course;
import assign.domain.Project;
import assign.domain.Meeting;

public class CourseStudentServiceImpl implements CourseStudentService {

	String dbURL = "";
	String dbUsername = "";
	String dbPassword = "";
	DataSource ds;

	// DB connection information would typically be read from a config file. - i have set the db url, name and pw in web.xml
	public CourseStudentServiceImpl(String dbUrl, String username, String password) {
		this.dbURL = dbUrl;
		this.dbUsername = username;
		this.dbPassword = password;
		
		ds = setupDataSource();
	}
	
	public DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        
        //url, user and pw all set already 
        ds.setUsername(this.dbUsername);
        ds.setPassword(this.dbPassword);
        ds.setUrl(this.dbURL);
        
        //do we keep this class?
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        return ds;
    }
	
    //should do it efficiently and use the given parameters, do not do the
    //insert statement by hand

	/** GET FULL PROJECT USING NAME **/
	public Project getProject(String project_name) throws SQLException{
		//open connection
		Connection conn = ds.getConnection();
		
		//new project declaration
		Project p = new Project();
		
		//query format: select * from projects where name = ?;
		String query = "SELECT * FROM projects WHERE name = '" + project_name + "';";
		
		//prepare statement with the string query
		PreparedStatement stmt = conn.prepareStatement(query);
		
		//result then execute query
		ResultSet r = stmt.executeQuery();
		
		//getresults
		if (!r.next()) {
			return null;
		}
		
		//set project information name + desc
		p.setName(r.getString("name"));
		p.setDesc(r.getString("description"));
		
		stmt.close();
		
		conn.close();
		
		//return the project
		return p;
	}
	
	/** GET FULL PROJECT USING PROJECT_ID **/
	public Project getProjectID(Integer project_id) throws SQLException{
		//open connection
		Connection conn = ds.getConnection();
		
		//new project declaration
		Project p = new Project();
		
		//query format: select * from projects where name = ?;
		String query = "SELECT * FROM projects WHERE project_id = '" + project_id + "';";
		
		//prepare statement with the string query
		PreparedStatement stmt = conn.prepareStatement(query);
		
		//result then execute query
		ResultSet r = stmt.executeQuery();
		
		//getresults
		if (!r.next()) {
			return null;
		}
		
		//set project information name + desc
		p.setName(r.getString("name"));
		p.setDesc(r.getString("description"));
		
		stmt.close();
		
		conn.close();
		
		//return the project
		return p;
	}
	
	
	
	/**  ADD PROJECTS 
	 * @throws SQLException **/
	public Project addProject(String name, String desc) throws Exception{
		//open connection
		Connection conn = ds.getConnection();
		
		//declare new project to add to database
		Project p = new Project();
		
		//set name and desc
		p.setName(name);;
		p.setDesc(desc);
		
		//example
		//String insert = "INSERT INTO courses(name, course_num) VALUES(?, ?)";
		
		//query format: insert ignore into projects(description, name) 
		String insert = "INSERT INTO projects(name, description) values(?,?)";
		
		//prepare statment
		PreparedStatement stmt = conn.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS);
		
		//set stmnt parameters
		stmt.setString(1, p.getName());
		stmt.setString(2, p.getDesc());
		
		int affectedRows = stmt.executeUpdate();

		if (affectedRows == 0) {
            throw new SQLException("Creating project failed, no rows affected.");
        }
		
		ResultSet generatedKeys = stmt.getGeneratedKeys();
	       if (generatedKeys.next()) {
	       	p.setProjectId(generatedKeys.getInt(1));
	       }
	       else {
	           throw new SQLException("Creating project failed, no ID obtained.");
	       }
	        
	       // Close the connection
	       stmt.close();
	       
	       //conn.close();
	   
	       return p;
	}
	
	
	/** UPDATE PROJECTS **/
	public void updateProjectDesc(Integer id, String desc) throws SQLException{
		//open connection
		Connection conn = ds.getConnection();
		
		//query format
		String query = "UPDATE projects SET description=? where project_id=?";
		
		//prepared statement
		PreparedStatement stmt = conn.prepareStatement(query);
		
		//set the values for 1,2 desc and name
		stmt.setString(1, desc);
		stmt.setInt(2, id);
		
		//need to udpate
		stmt.executeUpdate();
		
		//close
		stmt.close();
		
		//close
		//conn.close();
	}
	
	
	/** ADD MEETING FOR A PROJECT **/
	public void addMeeting(Integer project_id, String meeting_name, int year) throws SQLException{
		//open connection
		Connection conn = ds.getConnection();
			
		//create new meeting
		Meeting m = new Meeting();
		
		//set values
		m.setName(meeting_name);
		m.setYear(year);
		
		//query format
		String insert = "INSERT INTO meetings(project_id, name, year) values(?,?,?)";
		
		//prepare statement
		PreparedStatement stmt = conn.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS);
		
		//the project name should be with this meeting ot be associate
		stmt.setInt(1, project_id);
		stmt.setString(2, meeting_name);
		stmt.setInt(3, year);
		//stmt.setString(4,project_name);
		
		int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating course failed, no rows affected.");
        }
        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
        	m.setMeetingId(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Creating meeting failed, no ID obtained.");
        }
		
		stmt.close();
		
		//conn.close();
	}
	
	//get meetings for specific project id
	public ArrayList<Meeting> getMeetingsforProjectID(Integer project_id) throws SQLException{
		//open connection
		Connection conn = ds.getConnection();
		
		//declare arraylistof meetings
		ArrayList<Meeting> m = new ArrayList<Meeting>();
		
		//query
		String query = "SELECT * FROM meetings WHERE project_id=?";
		
		//ps
		PreparedStatement stmt = conn.prepareStatement(query);
	
		stmt.setInt(1, project_id);
		
		//execute
		ResultSet r = stmt.executeQuery();
		
		//iterate thru the result set, and add all meetings to the array list we are retunring
		while(r.next()){
			Meeting m2 = new Meeting();
			
			//set name o fmeeting
			m2.setName(r.getString("name"));
			
			//set year
			m2.setYear(r.getInt("year"));;
			
			//add meetings to array list
			m.add(m2);
		}

		//return arraylist
		return m;
	}

	
	
	
	/** DELETE PROJECT 
	 * @throws SQLException **/
	//requires a project name
	public void deleteProj(Integer project_id) throws SQLException{
		//open connection
		Connection conn = ds.getConnection();
		
		//query format: delete from projects where name = ?;
		String query = "DELETE FROM projects WHERE name=?";
		
		//prepare statement with the string query
		PreparedStatement stmt = conn.prepareStatement(query);
		
		//set the ? to the project name
		stmt.setInt(1, project_id);
		
		//run it
		stmt.executeUpdate();
		
		stmt.close();
		
		//close connection
		//conn.close();
	}
		
	
	/** EXAMPLE CODE **/
	
	public Course addCourse(Course c) throws Exception {
		Connection conn = ds.getConnection();
		
		String insert = "INSERT INTO courses(name, course_num) VALUES(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(insert,
                Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, c.getName());
		stmt.setString(2, c.getCourseNum());
		
		//take the query that is now populated with real values. the insert 
		//statement has become a valid insert statement. 
		//executeUpdate does the actual insert into the database table
		int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating course failed, no rows affected.");
        }
        
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
        	c.setCourseId(generatedKeys.getInt(1));
        }
        else {
            throw new SQLException("Creating course failed, no ID obtained.");
        }
        
        // Close the connection
        conn.close();
        
		return c;
	}

	public Course getCourse(int courseId) throws Exception {
		String query = "select * from courses where course_id=" + courseId;
		Connection conn = ds.getConnection();
		PreparedStatement s = conn.prepareStatement(query);
		ResultSet r = s.executeQuery();
		
		if (!r.next()) {
			return null;
		}
		
		Course c = new Course();
		c.setCourseNum(r.getString("course_num"));
		c.setName(r.getString("name"));
		c.setCourseId(r.getInt("course_id"));
		return c;
	}

}
