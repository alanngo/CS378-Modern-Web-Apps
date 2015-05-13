package assign.domain;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

//need xml element to retrieve?

/** ALL THE MEETING OBJECT METHODS TO RETRIEVE **/


@XmlRootElement(name="meeting")
public class Meeting {
	
	
	private String name;
	private int year;
	private int meeting_id;
	//private ArrayList<Meeting> meetingList;
	//private String project_name;

	
	//constructor
	public Meeting(){
	
	}
	
	@XmlElement
	public String getName(){
		
		return name;
	}
	
	public void setName(String n){
	
		this.name = n;
	}
	
	public void setYear(int y){
		
		this.year = y;
	}
	
	@XmlElement
	public int getYear(){
		
		return year;
	}

	@XmlElement
	public int getMeetingId(int id){
		return meeting_id;
	}
	
	public void setMeetingId(int id) {
		// TODO Auto-generated method stub
		this.meeting_id = id;
	}

}