package assign.etl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

		
		try{
			//create a new connection and get() fetches and parses a HTml file
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

			//get the links
			Elements links = doc.select("tr td a");
			for(org.jsoup.nodes.Element singlelink : links){


				//get value this sectino get the /meetings/solum/year link
				String addlink = singlelink.attr("abs:href");
				list.add(addlink);
			}

			//now add to map the whole list
			data.put(mapkey, list);
			
			/** ACCESSING WHAT IS IN THE LIST **/
			
			//first element will always be /meeting so start at i = 1
			for(int i = 1; i < list.size(); i++){
				
				String link = list.get(i);
				
				//debug
				//System.out.println(link);
				
				
				ArrayList<String> templistforparse = new ArrayList<String>();
				
				
				//make connection
				org.jsoup.nodes.Document newdoc = Jsoup.connect(link).get();

				//get the values
				Elements newlinks = newdoc.select("tr td a");	//or should use ahref?
				
				//interate thru elements
				for(org.jsoup.nodes.Element newsinglelink : newlinks){

					//get value 
					String newaddlink = newsinglelink.attr("abs:href");
					String textonly = newsinglelink.text();
			
					//System.out.println(textonly);
					//System.out.println(newaddlink);
					
					pairs.put(textonly,newaddlink);
					
					//templistforparse.add(newaddlink);
					
					//now add to map
					data.put(link, templistforparse);
				}
				
			}
			
			/**
			for(Map.Entry<String,String> entry : pairs.entrySet()){
				String key = entry.getKey();
				String val = entry.getValue();
				System.out.println("Parsed Entry: " + key + " and the Abs Link is: "  + val);
			}
			**/
			
			
			//HERE NEED TO FIND THE .LOG.HTMLS ONLY
			
			
		} catch (IOException e){
				e.printStackTrace();
		}		
				
		return pairs;
	}
}
