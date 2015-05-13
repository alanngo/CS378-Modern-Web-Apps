package assign.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
	
	@XmlAttribute
	private String name;
		
	@XmlElement(name = "link")
	private ArrayList<String> links;

	//constructor
	public Project(){
		//nothing
	}	
	
	//devs
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getLink() {
        return links;
    }
 
    public void setLink(ArrayList<String> link) {
        this.links = link;
    }
}
