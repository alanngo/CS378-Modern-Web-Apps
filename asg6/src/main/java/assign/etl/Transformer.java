package assign.etl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

public class Transformer {
	
	Logger logger;
	
	public Transformer() {
		logger = Logger.getLogger("Transformer");		
	}
	
	public Map<String, String> transform(Map<String, String> data) {
		// Read the data;
		// transform it as required;
		// return the transformed data;
		
		logger.info("Inside transform.");

		Map<String, List<String>> newData = new HashMap<String, List<String>>();
		Map<String,String> pairsofLogs = new HashMap<String, String>();

		String check = ".log.html";
		String parentcheck = "parent";

		
		//DEBUGGING ONLY
		/**
		for(Map.Entry<String,String> entry : data.entrySet()){
			String key = entry.getKey();
			String val = entry.getValue();
			System.out.println("Parsed Entry: " + key + " and the Abs Link is: "  + val);
		}
		**/
		
		//check for parent directory
		for(Map.Entry<String, String> entry : data.entrySet()){
			
			String title = entry.getKey();
			
			String link = entry.getValue();
			
			if(title.toLowerCase().contains(parentcheck.toLowerCase())){
				//checkifcontains
				if(link.toLowerCase().contains("team")){
					
					String first = "title";
					
					pairsofLogs.put(first, "solum_team_meeting");
				}
				else{
					String second = "title";
					
					pairsofLogs.put(second, "solum");
				}
			}
			
		}
		
		//find .log.html
		for(Map.Entry<String, String> entry : data.entrySet()){
			
			String key = entry.getKey();
			
			String val = entry.getValue();
			
			if(val.toLowerCase().contains(check.toLowerCase())){
				//System.out.println("CONTAINS: parsed: " + key + " and abs link: " + val);
				
				pairsofLogs.put(key,  val);
			}
			
			//here now i have pairsofLogs which contains keys and vals that contain the right .log.html
		}
		/**
		for(Map.Entry<String,String> entry : pairsofLogs.entrySet()){
			String key = entry.getKey();
			String val = entry.getValue();
			System.out.println("Parsed Entry: " + key + " and the Abs Link is: "  + val);
		}
		**/
		return pairsofLogs;
	}
}