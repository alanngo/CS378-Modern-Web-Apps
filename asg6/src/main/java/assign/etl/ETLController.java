package assign.etl;

import java.util.List;
import java.util.Map;

public class ETLController {
	
	EavesdropReader reader;
	EavesdropReader reader2;
	Transformer transformer;
	//Transformer transformer2;
	DBLoader loader;
	//DBLoader loader2;
	
	public ETLController() {
		transformer = new Transformer();	//construct new transformer
		loader = new DBLoader();			//new loader
	}
	
	public static void main(String[] args) {
		ETLController etlController = new ETLController();
		etlController.performETLActions();
	}
	
	private void performETLActions() {		
		try {

			//LINK SOURCES
			String source = "http://eavesdrop.openstack.org/meetings/solum/";
			String source2 = "http://eavesdrop.openstack.org/meetings/solum_team_meeting/";
			
			/** LOAD SOURCE 1 **/
						
			reader = new EavesdropReader(source);
			
			System.out.println("new reader made of source");

			// Read data
			Map<String, String> data = reader.readData();
			
			System.out.println("reading data.");

			
			// Transform data
			Map<String, String> transformedData = transformer.transform(data);
			
			System.out.println("data being transformed.");
			
			// Load data
			loader.loadData(transformedData);
			
			System.out.println("attempting to load...");
			
			
			/** LOAD SOURCE 2 **/
			
			reader = new EavesdropReader(source2);
			
			System.out.println("new reader made of source");
			
			//Read data
			Map<String, String> data2 = reader.readData();
			
			System.out.println("reading data.");

			//Transform data
			Map<String, String> transformedData2 = transformer.transform(data2);
			
			System.out.println("data being transformed.");
			
			//Load Data
			loader.loadData(transformedData2);
			
			System.out.println("attempting to load...");
			
			/** FINISHED LOADING BOTH LINKS **/
			

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
