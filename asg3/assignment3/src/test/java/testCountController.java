import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class testCountController {
	
	countController countController = new countController();
	countService countEditor = null;
	
	@Before
	public void setUp1() {		
		countEditor = mock(countService.class);		
		countController.setCountEditorService(countEditor);
	}
	
	@Test
	public void thisAlwaysPasses() {
    }
	
	@Test
	public void testdefaultMessage() {
		String reply = countController.defaultmessage();
		assertEquals("Please go to /meetings to make a query.", reply);
	}
	
	@Test
	public void testMettingMessage() {
		String reply = countController.meetingconnect();
		assertEquals("Please enter parameters to make a query.", reply);
	}
	
		
}