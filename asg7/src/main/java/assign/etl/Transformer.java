package assign.etl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.Marshaller;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class Transformer {
	
	Logger logger;
	
	public Transformer() {
		logger = Logger.getLogger("Transformer");		
	}
	
	public Map<String, Integer> transform(Map<String, String> data) throws IOException {
		logger.info("Inside transform.");

		Map<String, List<String>> newData = new HashMap<String, List<String>>();
		Map<String,String> pairsofLogs = new HashMap<String, String>();
		Map<String, Integer> meetingCount = new TreeMap<String, Integer>();

		String check = ".log.html";
		String parentcheck = "Parent";
			
		//iterate thru the map passed thru
		
		
		
		for(Map.Entry<String, String> entry: data.entrySet()){
			
			int totalcount = 0;

			String meetingname = entry.getKey();
			String meetinglink = entry.getValue();
			
			//make connection
			org.jsoup.nodes.Document newdoc = Jsoup.connect(meetinglink).get();
			
			ArrayList<String> templist = new ArrayList<String>();

			//get the values
			Elements newlinks = newdoc.select("tr td a");	//or should use ahref?
				
			//interate thru elements
			for(org.jsoup.nodes.Element newsinglelink : newlinks){
				
				String parse = newsinglelink.attr("abs:href");
				
				if(parse.length() > 40){
					templist.add(parse);
				}
			}
			
			
			//so here we have the meeting names and a list of meetings associated with it
			//System.out.println("Meeting name: " + meetingname + " and its list of links: " + templist);
			
			for(int i = 0; i < templist.size(); i++){
				//iterate thru map
				String l2 = templist.get(i);
				org.jsoup.nodes.Document d = Jsoup.connect(l2).get();
				
				Elements logs = d.select("tr td a");	//or should use ahref?

				//String totalhtml = d.text();
				ArrayList<String> logcheck = new ArrayList<String>();
				
				for(org.jsoup.nodes.Element loglink : logs){
					
					String parse2 = loglink.attr("abs:href");
					
					logcheck.add(parse2);
				}

				for(int j = 0; j < logcheck.size(); j++){
					
					String r = logcheck.get(j);
					
					if(r.toLowerCase().contains(check)){
						totalcount = totalcount + 1;
					}	
				}
				
			}
			meetingCount.put(meetingname, totalcount);
		}
		
		/**
		for(Entry<String, Integer> entry : meetingCount.entrySet()){
			String key = entry.getKey();
			Integer val = entry.getValue();
			System.out.println("MeetingName is: " + key + " and the number of meetings is: "  + val);
		}
		**/
	
		return meetingCount;
	}
}