package assign.etl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

//old service from old assignment to use for parsing
import assign.services.EavesdropService;

import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.Marshaller;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class EavesdropReader {
	
	String url;
	Logger logger; 

	EavesdropService service = null;
	
	public EavesdropReader(String url) {
		this.url = url;
		
		logger = Logger.getLogger("EavesdropReader");
	}

	public static void main(String arg[]){
		EavesdropReader reader = new EavesdropReader(arg[0]);
		reader.readData();
	}
	
	void startService(){
		if(service == null){
			service = EavesdropService.shared();
		}
	}


	/*
	 * Return a map where the contents of map is a single entry:
	 * <this.url, List-of-parsed-entries>
	 */
	public Map<String, String> readData() {
		logger.info("Inside readData.");
		String mapkey = url;
		ArrayList<String> list = new ArrayList<String>();

		Map<String, List<String>> data = new HashMap<String, List<String>>();
		Map<String, String> pairs = new HashMap<String, String>();
		//Map<String, Integer> meetingCounts = new HashMap<String, Integer>();

		
		try{
			//create a new connection and get() fetches and parses a HTml file
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

			//get the links
			Elements links = doc.select("tr td a");


			for(org.jsoup.nodes.Element singlelink : links){

				//so here, it grabs newadlink which would be the ABS hypertext link to what its point to
				String meetinglink = singlelink.attr("abs:href");

				//textonly this grabs the text, whcih in this asg7 would be meeting name
				String teamname = singlelink.text();
				
				teamname = teamname.substring(0, teamname.length()-1);
				
				if(!teamname.contains("Parent")){
					pairs.put(teamname, meetinglink);
				}
			}
			
			/**
			for(Map.Entry<String,String> entry : pairs.entrySet()){
				String key = entry.getKey();
				String val = entry.getValue();
				System.out.println("Team name: " + key + " link to that meeting: "  + val);
			}
			**/
			
			
			//HERE NEED TO FIND THE .LOG.HTMLS ONLY
			
			
		} catch (IOException e){
				e.printStackTrace();
		}		
				
		//Map<String, String> newpairs = new TreeMap<String,String>(pairs);
		
		return pairs;
	}
}
