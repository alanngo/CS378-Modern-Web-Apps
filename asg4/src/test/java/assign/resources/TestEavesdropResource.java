package assign.resources;

import java.io.IOException;
import assign.services.EavesdropServices;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.jsoup.select.Elements;
import org.junit.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.nodes.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//@Path("/projects");
public class TestEavesdropResource {
	
	//use mock objects
	EavesdropSerices mockservice = mock(EavesdropService.class);
	UTCoursesResource mockresource = new UTCoursesResource();
	
	final String home = "http://localhost:8080/assignment4/myeavesdrop/projects/";
	final String test1 = "http://localhost:8080/assignment4/myeavesdrop/projects/%23openstack-api/irclogs";
	final String test2 = "http://localhost:8080/assignment4/myeavesdrop/projects/non-existent-project/irclogs";
	
	
	//test for get union of meetings and irclogs
	//test home
	@Test
	public void testprojects () throws Exception {
		final String link = home;
		
		//use mockservice to get a mock document, reurn true
		when (mockservice.getDoc (link)).thenReturn (null);
		
		//use mock serice to get a mck map of links
		when (mockservice.getLinks (null, link)).thenReturn (null);
		
		//use mock serice to get a mock XML document
		when (mockservice.getXML ("projects", null)).thenReturn (null);
		
		String output = mockresource.getOutputUnion();
		
		//assert null, stirng shouldbe nul 
		assertNull (output);
	}
	
	//test1
	@Test
	public void testopenstackapi() throws Exception{
		
		final String link1 = test1;
		
		//use mockservice to get a mock document, reurn true
		when (mockservice.getDoc (link1)).thenReturn (null);
		
		//use mock serice to get a mck map of links
		when (mockservice.getLinks (null, link1)).thenReturn (null);
		
		//use mock serice to get a mock XML document
		when (mockservice.getXML ("%23openstack-api", null)).thenReturn (null);
		
		String output = mockresource.getIrclogs(link1);
		
		//assert null, stirng shouldbe nul 
		assertNull (output);
	}
	
	
	//test2
	@Test
	public void testexistent() throws Exception{	
		
		final String link2 = test2;
		
		//use mockservice to get a mock document, reurn true
		when (mockservice.getDoc (link2)).thenReturn (null);
		
		//use mock serice to get a mck map of links
		when (mockservice.getLinks (null, link2)).thenReturn (null);
		
		//use mock serice to get a mock XML document
		when (mockservice.getXML ("non-existent-project", null)).thenReturn (null);
		
		String output = mockresource.getIrclogs(link2);
		
		//assert null, stirng shouldbe nul 
		assertNull (output);
	}	
	
}
