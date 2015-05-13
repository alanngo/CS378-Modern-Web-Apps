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
import assign.domain.Meetings;
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

import assign.services.EavesdropService;
import assign.domain.Meetings;

@Path("/myeavesdrop")
public class MeetingResource{

	//CONSTRUCTOR??????
	//something about a session factory?\
	
	public MeetingResource(){
		//empty constructor?
	}
	
	
	//this is just to confirm that the values are correct
	@GET
	@Path("/helloworld")
	@Produces("application/xml")
	public String helloWorld() {
		System.out.println("doing stuff nigga");
		
		return "Hello world UPDATERRR LALAAL"; 		
	}
	
	//GET TO FIRST LINK
	
	
	//GET TO SECOND
	/** EXAMPLECODE **/


	/** implement GET for the count of meetings that happened for a particular meeting name in a particular year **/
	//@GET
	//@Path("/meetings/{team_meeting_name}/year/{year}/count")
	//@Produces("application/xml")
	//THIS ONE YOU CAN HARDCODE AND RETURN A SPECIFIC UNUMBER IF U WANT I GUESS?
	//THE FORMAT IS ON THE O RILEYS EX 3

	//what should this actually return?

	/** implement GET for all the meetings that happened in a particular year for a particular project **/
	@GET
	@Path("/meetings/{team_meeting_name}/year/{year}/")
	@Produces(application/xml)
	what should this actually return
	public Response outputMeetings(@PathParam("team_meeting_name") String team , @PathParam("year") Integer year) throws JAXBException, SQLException{

		//so this is the one to get all meetings for a specific team name and specific year 
		//would this return a set or a lsit? no idea how this works -> ask TA tmr
		
		//initalize the first meetingslist ot null, nothing in it
		//MeetingsList mlist = null;

		/** ERROR CHECKING **/
		if (team != "solum" || team != "solum_team_meeting"){

			//cant find these team meeting names, throw 404
			return Response.status(404).build();
		}

		
		//should i use a JAXB mashaller here? or should i use jsoup and parse out the data?
		//PROBLEM ->>> dont even know what object the data is returning in
		JAXBContext context = JAXBContext.newInstance(Project.class);
		Marshaller m = createMarshaller(context);

		new StreamingOutput() {
			public void write(OutputStream outputStream) {
				try {
					m.marshal(meeti, outputStream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		//in this case, meetings acts like a project...i guess?

		Response rs = Response.ok(meetings).build();
		return rs;
	}
	
	
	
	
}
