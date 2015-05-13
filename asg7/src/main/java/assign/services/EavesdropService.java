package assign.services;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.parser.Tag;
import java.util.TreeMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

public class EavesdropService implements EavesdropServ{

	static EavesdropService singleton;
	
	DataSource ds;
	
	public EavesdropService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//name of database
		ds = setupDataSource();
	}
	
	//singleton method for shared service
	public static EavesdropService shared(){
		if(singleton == null){
			singleton = new EavesdropService();
		}
		
		return singleton;
	}

	//set up data source to connect to mine
	public DataSource setupDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("davidko");
        ds.setPassword("");
        ds.setUrl("jdbc:mysql://localhost:3306/counts");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        return ds;
    }
	
	//return document with jsoup connection method
	public Document getDoc (String URL) throws IOException{
		//returns a document object that makes a connection to the URl passed through
		return Jsoup.connect (URL).get();
	}
	
	/** assignment 7 methods 
	 * @throws SQLException **/
	public Map<String, Integer> getAllMeetingCounts() throws SQLException{
		Connection conn = ds.getConnection();

		
		//this list is what we turn, its a list of meetings		
		Map<String, Integer> newmap = new TreeMap<String, Integer>();
		
		//query
		String query = "SELECT * FROM counts";
				
		PreparedStatement stmt = conn.prepareStatement(query);
		
		ResultSet r = stmt.executeQuery();

		//iterate thruresult set
		
		//throw if not work
		while(r.next()){
			String team = r.getString("team_meeting_name");
			int count = r.getInt("count");
			
			newmap.put(team, count);
		}
		
		return newmap;
	}
	
	
	//get counts returns a list
	
	
	

	/** old assignment methods **/
	
	//use Jsoup to extract links, then convert to xml
	public Elements projNames (Document doc){
		Elements ele = new Elements();

		String add = "";		
		String additive = "";

		//checking if add is a string or nul
		if(add == ""){
			additive = add;
		}

		
		//use for a href
		Elements titles = doc.getElementsByTag("a");
		
		//iterate thru the elements and filter out the headers
		for(Element titleElem : titles){
			String htmlTitle = titleElem.html();
		
			if (htmlTitle.equals("Name") || htmlTitle.equals("Last modified") || htmlTitle.equals("Size") ||
					htmlTitle.equals("Description") || htmlTitle.equals("Parent Directory")){
					continue;
			}
		
			//add the tag value of project in the html to the ele list
			Element elem = new Element(Tag.valueOf("Project"), "");
			elem.html(titleElem.html().replace("/",""));
			ele.add(elem);
		}
		//return the total ele struc
		return ele;
	}
	
	
	public Elements getLinks(Document doc, String add){
		Elements ele = new Elements();

		String additive = "";

		//checking if add is a string or nul
		if(add == ""){
			additive = add;
		}


		//use for a href
		Elements titles = doc.getElementsByTag("a");
		
		//iterate thru the elements and filter out the uneeded headers
		for(Element titleElem : titles){
			String htmlTitle = titleElem.html();
				
			if (htmlTitle.equals("Name") || htmlTitle.equals("Last modified") || htmlTitle.equals("Size") ||
					htmlTitle.equals("Description") || htmlTitle.equals("Parent Directory")){
					

				//create dummy

			}
		
			//get the links into new element struct
			Element elem = new Element(Tag.valueOf("link"), "");
			//elem.html(titleElem.html().replace("/",""));
						
			//check if the add passed thru is a or null
			String total = "";
			if(add == null){
				add = "";
			}else{
				add = add;
			}
			
			//make a href
			total = add + titleElem.attr("href");
			ele.add(elem);
		}
		return ele;
	}
	
	//converting to XML  - usign code from github and some from the Jsoup documentation
	public String doXML (String rootElem, Map<String, String> attri, Elements elem) {
		Document docu = Jsoup.parse ("");
		docu.html ("");
		int nullcheck1 = 0;
		int nullcheck2 = 0;
		
		Element root = docu.appendElement(rootElem);
		
		if(attri == null){
			nullcheck1 = 1;
		}
		else if (attri != null) {
			for (String key : attri.keySet ()){
				String value = attri.get (key);
				root.attr (key, value);
			}
		}
		if (elem == null){
			nullcheck2 = 1;
		}else if(elem != null) {
			for (Element element : elem) {
				root.appendChild(element);
			}
		}
		return docu.html ();
	}
	public String getXML (String projName, Elements elem) {
		
		Map<String, String> attri = new TreeMap<String, String> ();
		
		attri.put ("name", projName);
		
		if(attri == null){
			elem = null;
		}
		
		return doXML ("project", attri, elem);
	}
	
	//get data method, pass thru the ele and then make the xml from the elements
	public String getData(Elements elem){
		
		//call the make proejct xml
		//and the make xml and return the string
		
		return doXML ("projects", null, elem);
	}
	
	//use for interim testing
	public String testData(){
		
		return "Dummy testing. Again. 2";
	}
	
}
