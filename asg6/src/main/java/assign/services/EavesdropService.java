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

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class EavesdropService {

	//make singleton instance
	static EavesdropService singleton;
	
	//singleton method for shared service
	public static EavesdropService shared(){
		if(singleton == null){
			singleton = new EavesdropService();
		}
		
		return singleton;
	}
	
	//return document with jsoup connection method
	public Document getDoc (String URL) throws IOException{
		//returns a document object that makes a connection to the URl passed through
		
		return Jsoup.connect (URL).get();
	}


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
