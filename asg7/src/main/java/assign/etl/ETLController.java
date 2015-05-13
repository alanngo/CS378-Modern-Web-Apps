package assign.etl;

import java.util.List;
import java.util.Map;

public class ETLController {
	
	EavesdropReader reader;
	Transformer transformer;
	DBLoader loader;
	
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
			String source = "http://eavesdrop.openstack.org/meetings";
						
			/** ASSIGNMENT 7 LOADING **/
			reader = new EavesdropReader(source);
			
			System.out.println("new reader made of source");
			
			//Read data
			Map<String, String> data = reader.readData();
			
			System.out.println("reading data.");

			//Transform data
			Map<String, Integer> transformedData = transformer.transform(data);
			
			System.out.println("data being transformed.");
			
			//Load Data
			loader.loadData(transformedData);


		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
