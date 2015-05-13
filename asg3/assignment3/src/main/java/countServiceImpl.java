import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class countServiceImpl implements countService{

	URL eavesdropURL = null;
	String name = "countService";
	String baseURL = "http://eavesdrop.openstack.org/";
	
	
	public countServiceImpl() {
		try {
			eavesdropURL = new URL(baseURL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	//need to define the countService functions
	public String getResponseFromEavesDrop(String projectName) {
		String retVal = "";		
		baseURL += "/meetings/" + projectName;		
		try {			
			eavesdropURL = new URL(baseURL);
			URLConnection connection = eavesdropURL.openConnection();
			String readData = readDataFromEavesdrop(connection);
			retVal = parseOutput(readData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public String outputTitle(){
		
		return "Year" + "        " + "count";
				
	}
	
	
	//copied form github

	protected String readDataFromEavesdrop(URLConnection connection) {
		String retVal = "";
		try {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			retVal += inputLine;
		}
		in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	protected String parseOutput(String inputString) {
		String retval = "";
		
		return retval;
	}

	public void thisIsVoidFunction() {
		// TODO Auto-generated method stub
	}

		
}
