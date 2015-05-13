package assign.domain;

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
@Table(name = "counts")
public class Meetings{

	private Long id;
	private String tmn;
	private Integer c;
	
	/** constructor **/
	public Meetings(){
		//this form used by hibernate
	}

	public Meetings(String first, Integer second){
		//for application use to create new events
		this.tmn = first;	//sets team meeting name
		this.c = second;
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

	/** COUNT **/
	@Column(name = "count")
	public Integer getCount(){
		return c;
	}

	public void setCount(Integer tmp){
		this.c = tmp;
	}

}