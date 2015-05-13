import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class testCountServiceImpl {

	countServiceImpl countEditor = null;
	
	@Before
	public void setUp() {
		countEditor = new countServiceImpl();
	}
	
	@Test
	public void testReadDataFromEavesdrop() {
		
		try {
			String exampleString = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
			exampleString += "<html>";
			exampleString += "</html>";
			
			URLConnection connection = mock(URLConnection.class); // Create mock dependency: mock()
			
			InputStream i = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));		
			
			when(connection.getInputStream()).thenReturn(i); // Setting up the expectations
			
			String result = countEditor.readDataFromEavesdrop(connection);
						
			assertEquals(exampleString, result);
						
			verify(connection).getInputStream();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}