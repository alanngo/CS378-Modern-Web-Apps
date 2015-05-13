package assign.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import assign.domain.Project;
import assign.domain.Projects;
import assign.services.EavesdropService;

@Path("/myeavesdrop")
public class UTCoursesResource {
	
	final String URL = "http://localhost:8080/assignment4/myeavesdrop/projects/";
	final String meetings = "meetings";
	final String irc = "irclogs";
	
	//URLS need ot make connection with
	final String meetingsurl = "http://eavesdrop.openstack.org/meetings";
	final String ircurl = "http://eavesdrop.openstack.org/irclogs";
	
	ArrayList<String> totalOutput = new ArrayList<String>();
	
	private Projects projects = new Projects();
	private Project project = new Project();
	
	EavesdropService service = null;
	
	//eavesdropservice functions
	//check if the service is in use, null or not
	EavesdropService startService(){
		if(service == null){
			service = EavesdropService.shared();
		}
		
		return service;
	}
		
	//set services
	void setEavesdropService(EavesdropService services){
		this.service = services;
	}
	
	//functions for projects
	public void setProjects(Projects projects){
		this.projects = projects;
	}
		
	public UTCoursesResource() {
		this.service = new EavesdropService();
	}
	
	//using this as dummy tester for functionality
	@GET
	@Path("/helloeavesdrop")
	@Produces("text/html")
	public String helloEavesdrop() {
		return this.service.testData();		
	}	
	
	// OUTPUTS THE UNION OF MEETINGS AND IRCLOGS
	@GET
	@Path("/projects")
	@Produces("application/xml")
	public String outputUnion() throws JAXBException {
		/*
		This should return a union of all the projects listed on:
 		http://eavesdrop.openstack.org/meetings
 		http://eavesdrop.openstack.org/irclogs
		*/
		
		//am i using projects arraylist for irclogs?
		
		//output
		String output = "";
		
		//create an arraylist of projects
		ArrayList<String> projlist = new ArrayList<String>();
		
		//set the projects lists rraylist to projects // initialize
		projects.setProjects(projlist);
		
		//check if service is null and then get it initialized
		EavesdropService service = startService();
		
		//parse and use functions form eavsedropservices
		try {
			//connect to meetings urls and set the elements
			org.jsoup.nodes.Document doc = service.getDoc (meetingsurl);
			Elements elements = service.projNames (doc);
			
			//connect to irclogs url and set the elements 
			doc = service.getDoc (ircurl);
			
			elements.addAll (service.projNames (doc));
			output = service.getData (elements);
		} catch (IOException e) {	
			output = null;
		}
		
		if(project == null){
			projects.setProjects(projlist);
		}
		
		
		/*
	    return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	            outputCourses(outputStream, projects);
	         }
	      };
	      */
		return output;
	}	

	//for irclogs specific
	//gets Unknown @PathParam: projectparam for path: /myeavesdrop/projects/#heat/irclogs
	@GET
	@Path("/projects/{projects}/irclogs")
	public String getIrclogs(@PathParam("projectparam") String projectname) {
		
		Project irclogsproj = new Project();
		String totalURL = "";
		String output = "";
		
		ArrayList<String> projList = new ArrayList<String>();

		
		//start service
		EavesdropService service1 = startService();
		
		//create the URL
		//we have ircurl already globally
		String slash = "/";
		String projparam = projectname;
		
		//types in %23, but outputs at #23
		//projectname = projectname.replace ("%23", "#");
		
		//total url = http://eavesdrop.openstack.org/irclogs + / + projname + /
		totalURL = ircurl + slash + projectname + slash; 
		
		//undo the string replace we did?
		projectname = projectname.replace ("%23", "#");

		
		//have totlaurl here
		//get Unknown @PathParam: projectparam for path: /myeavesdrop/projects/#heat/irclogs error
		
		//debug to see if url is right
		System.out.println(totalURL);
		
		//totalURL = totalURL.replace("%23", "#");
		
		try {
			org.jsoup.nodes.Document doc = service1.getDoc (totalURL);
			Elements elements = service1.getLinks (doc, totalURL);
			output = service1.getXML (totalURL, elements);
		} catch (IOException e) {	
			output = null;
		}
		
		
		//parsed andoutput the irclogs
		return output;
	}
	
	
	@GET
	@Path("/project")
	@Produces("application/xml")
	public StreamingOutput getProject() throws Exception {
		final Project heat = new Project();
		heat.setName("%23heat");
		heat.setLink(new ArrayList<String>());
		heat.getLink().add("l1");
		heat.getLink().add("l2");		
			    
	    return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {
	            outputCourses(outputStream, heat);
	         }
	      };	    
	}		
	
	protected void outputCourses(OutputStream os, Projects projects) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Projects.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(projects, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}	
	
	protected void outputCourses(OutputStream os, Project project) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(Project.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(project, os);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}	
	}
	
	
}
