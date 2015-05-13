package assign.etl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import assign.domain.Assignment;
import assign.domain.Meetings;
import assign.domain.UTCourse;

import java.util.logging.*;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;

public class DBLoader {
	private SessionFactory sessionFactory;
	
	Logger logger;


	@SuppressWarnings("deprecation")
	public DBLoader() {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        
        logger = Logger.getLogger("EavesdropReader");
	}
	
	public void loadData(Map<String, String> data) throws Exception {
		logger.info("Inside loadData.");
		String team_meeting_name = "";
		int year = 0;
		
		/** GET THE TEAM_MEETING_NAME <title> **/
		for(Map.Entry<String, String> entry : data.entrySet()){
			String key = entry.getKey();

			String value = entry.getValue();

			//find the key with title
			if(key.toLowerCase().contains("title")){
				team_meeting_name = value;
				//break;
				//System.out.println(team_meeting_name)
			}
		}

		for(Map.Entry<String, String> entry : data.entrySet()){
			String parsekey = entry.getKey();
			String absLink = entry.getValue();


			if(parsekey != "title"){
			//get the year of the whole entry	
			if(parsekey.toLowerCase().contains("2013")){
				year = 2013;
			} 
			else if(parsekey.toLowerCase().contains("2014")){
				year = 2014;
			}
			else if(parsekey.toLowerCase().contains("2015")){
				year = 2015;
			}

			//System.out.println(team_meeting_name);
			//System.out.println(year);
			//System.out.println(parsekey);
			//System.out.println(absLink);
			
			Long temp = addMeetings(team_meeting_name, year, parsekey, absLink);
			
			}
		}
		
		
	}
	
	public Long addMeetings(String teamname, Integer year, String parse, String abslink) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long meetingID = null;
		
		
		try {
		
			
			tx = session.beginTransaction();
			Meetings newMeeting = new Meetings(teamname, year, parse, abslink); 
			session.save(newMeeting);
		    meetingID = newMeeting.getId();
		    tx.commit();
		    
		    //System.out.println("ADDED THIS MEETING TO THE DATABASE PIECE OF SHIT.");
		
		
		
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();			
		}
		return meetingID;
	}
	
	public Long addAssignment(String title) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long assignmentId = null;
		try {
			tx = session.beginTransaction();
			Assignment newAssignment = new Assignment( title, new Date() ); 
			session.save(newAssignment);
		    assignmentId = newAssignment.getId();
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();			
		}
		return assignmentId;
	}
	
	public Long addAssignmentAndCourse(String title, String courseTitle) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long assignmentId = null;
		try {
			tx = session.beginTransaction();
			Assignment newAssignment = new Assignment( title, new Date() );
			UTCourse course = new UTCourse(courseTitle);
			newAssignment.setCourse(course);
			session.save(course);
			session.save(newAssignment);
		    assignmentId = newAssignment.getId();
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();			
		}
		return assignmentId;
	}
	
	public Long addAssignmentsToCourse(List<String> assignments, String courseTitle) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long courseId = null;
		try {
			tx = session.beginTransaction();
			UTCourse course = new UTCourse(courseTitle);
			session.save(course);
			courseId = course.getId();
			for(String a : assignments) {
				Assignment newAssignment = new Assignment( a, new Date() );
				newAssignment.setCourse(course);
				session.save(newAssignment);
			}
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();			
		}
		return courseId;
	}
	
	public List<Assignment> getAssignmentsForACourse(Long courseId) throws Exception {
		Session session = sessionFactory.openSession();		
		session.beginTransaction();
		String query = "from Assignment where course=" + courseId;		
		List<Assignment> assignments = session.createQuery(query).list();		
		return assignments;
	}
	
	public Assignment getAssignment(String title) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Assignment.class).
        		add(Restrictions.eq("title", title));
		
		List<Assignment> assignments = criteria.list();
		
		return assignments.get(0);		
	}
	
	public Assignment getAssignment(Long assignmentId) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Assignment.class).
        		add(Restrictions.eq("id", assignmentId));
		
		List<Assignment> assignments = criteria.list();
		
		return assignments.get(0);		
	}
}
