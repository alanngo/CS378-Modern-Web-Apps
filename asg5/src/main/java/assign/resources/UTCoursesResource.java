package assign.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import java.net.URI;

import assign.domain.Course;
import assign.domain.Meeting;
import assign.domain.Project;
import assign.services.CourseStudentService;
import assign.services.CourseStudentServiceImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.xml.internal.ws.util.Pool.Marshaller;


@Path("/myeavesdrop")
public class UTCoursesResource {
	
	CourseStudentService db;
	String password;
	String username;
	String dburl;
		
	
	public UTCoursesResource(@Context ServletContext servletContext) {		
		dburl = servletContext.getInitParameter("DBURL");
		username = servletContext.getInitParameter("DBUSERNAME");
		password = servletContext.getInitParameter("DBPASSWORD");
		this.db = new CourseStudentServiceImpl(dburl, username, password);
	}

	
	
	//this is just to confirm that the values are correct
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		System.out.println("Inside helloworld");
		System.out.println("DB creds are:");
		System.out.println("DBURL:" + dburl);
		System.out.println("DBUsername:" + username);
		System.out.println("DBPassword:" + password);		
		return "Hello world UPDATERRR LALAAL"; 		
	}
	
	//do POST of projects - to create the project - /projects/
	@POST
	@Path("/projects")
	@Consumes("application/xml")
	public Response createProject(InputStream input) throws Exception{
		
		//how to create response code?
		//imoprt the response io
		
		//create new project
		Project p = new Project();
		
		p = parseProject(input);
		
		//now we have the project parsed
		db.addProject(p.getName(), p.getDesc());
		
		//create response
	    return Response.created(URI.create("/myeavesdrop/projects" + p.getName())).build();
	}
	
	
	//do POST of meetings - create meeting FOR a project - /projects/<projectId>/meetings
	@POST
	@Path("projects/{project_id}/meetings")
	@Consumes("application/xml")
	public Response createMeeting(@PathParam("project_id") Integer project_id, String name, InputStream input) throws SQLException{
		
		//create new meeting object
		Meeting m = parseMeeting(input);
		
		//add meeting to a project
		db.addMeeting(project_id, m.getName(), m.getYear());

		//reponse
		return Response.created(URI.create("/myeavesdrop/projects/" + project_id)).build();
	}
	
	//GET PROJECTS
	@GET
	@Path("/projects/{project_id}")
	@Produces("application/xml")
	public Project outputProject(@PathParam("project_id") Integer project_id) throws JAXBException, SQLException{
		//arraylist
		ArrayList<Meeting> m = new ArrayList<Meeting>();
		Meeting newMeeting = new Meeting();
		
		//delcare new project
		Project p = db.getProjectID(project_id);
		
		//not a project to get
		if(p == null){
			
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		//make a list 
		m = db.getMeetingsforProjectID(project_id);
		
		//totalmeetings.setMeetingArray(m);
		
		//if not empty set meetings
		if(m.size() != 0){
			p.setMeetingList(m);
		}
		
		//return the porject to output
		return p;
	}
	
	
	
	//do DELETE of project/<project_id> - delete project
	/** DELETE PROJECT 
	 * @throws SQLException **/
	@DELETE
	@Path("/projects/{project_id}")
	public void deleteProj(@PathParam("project_id") Integer id) throws SQLException{
		db.deleteProj(id);
	}
	
	
	//UPDATE DO PUT
	@PUT
	@Path("projects/{project_id}")
	public void updateProj(@PathParam("project_id") Integer project_id, InputStream is) throws SQLException{
		//eclare new project object
		Project p = parseProject(is);
		
		//set to the project we want
		p.setProjectId(project_id);
		
		//grab old proj
		Project temp = db.getProjectID(p.getProjectId());
		
		//check if roject exist
		//need torhrow status response if not found
		if(temp == null){
			//project no tin database
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		//updateit
		db.updateProjectDesc(p.getProjectId(), p.getDesc());
	}

	
	/** DOM PARSER METHOD FORM EX 3 O RILEY **/
	public Project parseProject(InputStream is){
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Project p = new Project();
			if(root.getAttribute("project_id") != null && !root.getAttribute("project_id").trim().equals("")){
				p.setProjectId(Integer.valueOf(root.getAttribute("project_id")));;
			}
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("name")) {
					p.setName(element.getTextContent());
				} else if (element.getTagName().equals("description")) {
					p.setDesc(element.getTextContent());
				}/*
				 else if (element.getTagName().equals("street")) {
		               cust.setStreet(element.getTextContent());
		            }
		            else if (element.getTagName().equals("city")) {
		               cust.setCity(element.getTextContent());
		            }
		            else if (element.getTagName().equals("state")) {
		               cust.setState(element.getTextContent());
		            }
		            else if (element.getTagName().equals("zip")) {
		               cust.setZip(element.getTextContent());
		            }
		            else if (element.getTagName().equals("country")) {
		               cust.setCountry(element.getTextContent());
		            } 
		            */
			}
			return p;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
	
	/** DOM PARSER FOR MEETINGS  FROM EX 3 O RILYES **/
	public Meeting parseMeeting(InputStream is){
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			Meeting m = new Meeting();
			if(root.getAttribute("project_id") != null && !root.getAttribute("project_id").trim().equals("")){
				m.setMeetingId(Integer.valueOf(root.getAttribute("project_id")));;
			}
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("name")) {
					m.setName(element.getTextContent());
				} else if (element.getTagName().equals("year")) {
					int year = Integer.parseInt(element.getTextContent());
					m.setYear(year);
				}/*
				 else if (element.getTagName().equals("street")) {
		               cust.setStreet(element.getTextContent());
		            }
		            else if (element.getTagName().equals("city")) {
		               cust.setCity(element.getTextContent());
		            }
		            else if (element.getTagName().equals("state")) {
		               cust.setState(element.getTextContent());
		            }
		            else if (element.getTagName().equals("zip")) {
		               cust.setZip(element.getTextContent());
		            }
		            else if (element.getTagName().equals("country")) {
		               cust.setCountry(element.getTextContent());
		            } 
		            */
			}
			return m;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
	

	
}
