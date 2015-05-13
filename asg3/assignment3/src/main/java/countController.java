import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpHeaders;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



@Controller
public class countController {

		private countService firstcountService;
		
		//homepage
		@ResponseBody
		@RequestMapping(value = "/")
		public String defaultmessage()
		{
			return "Please go to /meetings to make a query.";
		}
		
		//plain meetings
		@ResponseBody
		@RequestMapping(value = "/meetings")
		public String meetingconnect(){
			
			return "Please enter parameters to make a query.";
		}
		
		@ResponseBody
		@RequestMapping(value = "/meetings")
		public String meetingconnection(@RequestParam("project") String project) {

			//use retval to return values
			//im guessing as well as theoutput to csreen
			
			
			//make URl connection, project is the given project param passed
			firstcountService.getResponseFromEavesDrop(project);
			
			
			
			
			//output section
			//firstcountService.outputTitle();
			
			return project;
		}


		//dependency injection
		public void setCountEditorService(countService editorService) {
			this.firstcountService = editorService;
		}
}
