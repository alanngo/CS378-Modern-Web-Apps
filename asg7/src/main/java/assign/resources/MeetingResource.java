package assign.resources;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map.Entry;
import java.util.TreeMap;




import assign.services.EavesdropService;

@Path("/myeavesdrop")
public class MeetingResource{

	private EavesdropService dbs = new EavesdropService();
	
	
	@GET
	@Path("/helloworld")
	@Produces("text/html")
	public String helloWorld() {
		System.out.println("doing stuff nigga");
		
		return "Hello world UPDATERRR LALAAL"; 		
	}
	
	@GET
	@Path("/counts")
	@Produces("application/xml")
	public StreamingOutput outputCount() throws JAXBException, SQLException{
		/** ERROR CHECKING **/
		final Map<String, Integer> tmp = new TreeMap<String, Integer>(dbs.getAllMeetingCounts());
		
		return new StreamingOutput() {
	         public void write(OutputStream outputStream) throws IOException, WebApplicationException {  
	        		 outputCounts(outputStream, tmp);	         }
	      };
	}

	/** OUTPUT COUNTS **/
	protected void outputCounts(OutputStream os, Map<String, Integer> tmp) {
	      PrintStream writer = new PrintStream(os);   
	      
	      writer.println("<meetings>");
	      for(Entry<String, Integer> entry: tmp.entrySet()){
	    	  String name = entry.getKey();
	    	  int value = entry.getValue();
		      writer.println("<meeting>");
	    	  writer.println("<name>" + name + "</name>");
	    	  writer.println("     <count>" + value + "</count>");
		      writer.println("</meeting>");
	      }
	      writer.println("</meetings>");
	}
}
