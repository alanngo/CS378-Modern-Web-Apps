package assign.services;

import java.sql.SQLException;
import java.util.ArrayList;

import assign.domain.Course;
import assign.domain.Meeting;
import assign.domain.Project;

public interface CourseStudentService {

	public Course addCourse(Course c) throws Exception;
	
	public Course getCourse(int courseId) throws Exception;
	
	public void deleteProj(Integer id) throws SQLException;
	
	public void addMeeting(Integer project_id, String meeting_name, int year) throws SQLException;
	
	public void updateProjectDesc(Integer id, String desc) throws SQLException;

	public Project addProject(String name, String desc) throws Exception;

	public Project getProject(String project_name) throws SQLException;
	
	public Project getProjectID(Integer project_id) throws SQLException;

	public ArrayList<Meeting> getMeetingsforProjectID(Integer project_id) throws SQLException;
	
}
