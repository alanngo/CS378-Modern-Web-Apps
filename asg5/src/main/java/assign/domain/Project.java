package assign.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

//need xml element to retrieve?

/** ALL THE PROJECT OBJECT METHODS TO RETRIEVE **/


@XmlRootElement(name="project")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
	
	private String name;
	private String description;	
	
	@XmlTransient
	private List<Meeting> meetingList;
	
	private int project_id;
	
	
	//constructor
	public Project(){
	
	}
	
	@XmlElement
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		this.name = n;
	}
	
	@XmlElement(name="description")
	public String getDesc(){
		
		return description;
	}

	public void setDesc(String d){
		
		this.description = d;
	}

	
	@XmlElement
	public int getProjectId(){
		return project_id;
	}
	
	public void setProjectId(int pid) {
		this.project_id = pid;
	}
	
	public List<Meeting> getMeetingList() {
		return meetingList;
	}
	
	public void setMeetingList(List<Meeting> m){
		this.meetingList = m;
	}
	
}

