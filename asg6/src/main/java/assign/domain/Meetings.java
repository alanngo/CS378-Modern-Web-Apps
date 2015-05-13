package assign.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;	


import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "meetings")
public class Meetings{

	//use marshalling
	//wrapper
	
	/** the attributes:
	*	1. team_meeting_name
	*	2. year
	*	3. meeting_name ->> also declared as parsedentry in my program
	*	4. link
	**/

	/** attributes **/

	private Long id;
	private String tmn;
	private Integer y;
	private String mn;
	private String l;
	
	//list of meetings
	//should this be done in a seprate class

	/** constructor **/
	public Meetings(){
		//this form used by hibernate
	}

	public Meetings(String first, Integer second, String third, String fourth){
		//for application use to create new events
		this.tmn = first;	//sets team meeting name
		this.y = second;	//sets year
		this.mn = third;	//sets meeting_name
		this.l = fourth;	//sets link
	}

	/** MEETING_ID **/
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	@Column(name="meeting_id")
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	/** TEAM_MEETING_NAME **/
	@Column(name = "team_meeting_name", nullable = false, length = 1000)
	public String getTeamMeeting(){
		return tmn;
	}

	public void setTeamMeeting(String tmp){
		this.tmn = tmp;
	}

	/** YEAR **/
	@Column(name = "year")
	public Integer getYear(){
		return y;
	}

	public void setYear(Integer tmp){
		this.y = tmp;
	}

	/** MEETING_NAME **/
	@Column(name = "meeting_name")
	public String getMeetingName(){
		return mn;
	}

	public void setMeetingName(String tmp){
		this.mn = tmp;
	}

	/** LINK **/
	@Column(name = "link")
	public String getLink(){
		return l;
	}

	public void setLink(String tmp){
		this.l = tmp;
	}
	
	

}