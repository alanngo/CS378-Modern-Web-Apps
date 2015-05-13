package assign.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import assign.domain.Meetings;

public interface EavesdropServ {
	
	//single function
	public Map<String, Integer> getAllMeetingCounts() throws SQLException;
}
