
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.http.Cookie;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.io.FileOutputStream;

import javax.servlet.SessionTrackingMode;

import java.util.EnumSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;




public class MyEavesdropServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	Map<String, String> userData;
	Map<String, String> loggedInUsers;
	Map<String, String> visitedURLS;
	Map<String, String> urlData;

    @Override
		public void init(ServletConfig config) {
            userData = new HashMap<String, String>();
            loggedInUsers = new HashMap<String, String>();
            visitedURLS = new HashMap<String, String>();	//use cookie as key
        }
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
                    throws ServletException, IOException
    {
		String url = request.getRequestURL().toString();
		String username = request.getParameter("username"); 
        String session = request.getParameter("session");
        String type;
        String project;
        String year;
        //PrintWriter out = response.getWriter();
        String usernameholder = "";
        String totalURL;


        
	    //response.getWriter().println("the query parameters requested are: ");
        type = request.getParameter("type");
        project = request.getParameter("project");
        year = request.getParameter("year");
    
        //COOKIE PART SESSION MANAGEMENT
        Cookie cookies [] = request.getCookies();
        Cookie cookies1 [] = request.getCookies();
        boolean friend = false;
        
        if(username != null){
        	for(int i=0; cookies != null && i<cookies.length; i++) {
        		Cookie ck = cookies[i];
        		String cookieName = ck.getName();
                String cookieValue = ck.getValue();
                if ((cookieName != null && cookieName.equals("not-a-stranger-anymore")) 
                                && cookieValue != null && cookieValue.equals("friend")) {
                		cookieName = username;
                		cookieValue = "start";
                        friend = true;
                }
        	}
        }
        
        
        
        //check if already a session
        
        if(username != null){
        	for(int i=0; cookies1 != null && i<cookies1.length; i++) {
        		Cookie ck = cookies1[i];
        		String cookieName = ck.getName();
                String cookieValue = ck.getValue();
                if(cookieName.equals(username) && cookieValue.equals("start")) {
                		response.getWriter().println("There already exists a session with this username.");
                		//friend = true;
                }
        	}
        }
        
        
        //NEW USER with no input
        if (username == null && type == null) {
        	Cookie cookie = new Cookie("not-a-stranger-anymore","friend");
            cookie.setDomain("localhost");
            //cookie.setPath("/session-example" + request.getServletPath());
            cookie.setMaxAge(1000);
            response.addCookie(cookie);
            response.getWriter().println("Hello, stranger. Please enter username and session value to begin making queries.\n");
        }

        if(username != null && session != null){
        	
            for (String names : loggedInUsers.keySet()) {
            	//response.getWriter().println(names);
            	if(username == names){
            		response.getWriter().println("There already exists a person with this user name.");
            	}
            }      	
        }

  
        userData.put(username, session);			//keeps track of all user data
        loggedInUsers.put(username, session);		//keeps track of all usres already in
        //response.getWriter().println(loggedInUsers);
        
        //sesssion END, set the cookie value to END and then exit program
        if(session == "end"){
        	response.getWriter().println("Session end requested. Please exit.");
        	
        	for(int i=0; cookies != null && i<cookies.length; i++) {
        		Cookie ck = cookies[i];
        		String cookieName2 = ck.getName();
                String cookieValue2 = ck.getValue();
                
                if (cookieName2.equals(username)) {
                	cookieValue2 = "end";
                }
        	}
        }

        
        if(type != null){
        	String type2 = type;
        	String project2 = project;
        	String year2 = year;
        	
        	if(project2 == null){
        		project2 = "";
        	}
        	
        	if(year2 == null){
        		year2 = "";
        	}
        	
        	totalURL = "http://eavesdrop.openstack.org/" + type2 + "/" + project2 + "/" + year2 + "/";
        	//add url to the mapp
        	visitedURLS.put(username, totalURL);
        }
        
         
        /** outputing **/
        response.getWriter().println("Visited URls");

    		
        for (String key : visitedURLS.keySet()) {
        	
        	if(key == username){
        		//it gets here and shows that username is null cause the prerequested
        		response.getWriter().println(visitedURLS.get(key));
        	}
        }
        

        response.getWriter().println("\n");
        
        response.getWriter().println("URL Data");
        
        try {
        	String type1 = request.getParameter("type");
        	String project1 = request.getParameter("project");
        	String year1 = request.getParameter("year");
			String source = "http://eavesdrop.openstack.org/" + type1 + "/" + project1 + "/" + year1;			
		    //response.getWriter().println(source);
			
			Document doc = Jsoup.connect(source).get();
		    Elements links = doc.select("body a");
		    
		    java.util.ListIterator<Element> iter = links.listIterator();
		    while(iter.hasNext()) {
		    		Element e = (Element) iter.next();
		    		String s = e.html();
		    		s = s.replace("#", "%23");
		    		response.getWriter().println(source + s);
		    }	    
		} catch (Exception exp) {
			exp.printStackTrace();
		}	
    } 
}


