package assign.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class Projects {
	 
	String name;
	List<String> link = null;
    private List<String> project = null;
      
	
	@XmlElement(name = "project")
	private ArrayList<String> projects;
	  
    public ArrayList<String> getProjects() {
    	return projects;
    }
    
    public void setProjects(ArrayList<String> projects) {
    	this.projects = projects;
    }
    
    //add new project to arraylist of projects
    public void addProjects(String project){
    	if(!projects.contains(project)){
    		projects.add(project);
    	}
    }
    
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getLink() {
        return link;
    }
 
    public void setLink(List<String> link) {
        this.link = link;
    }
       
    
}
