import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.net.*;
import java.io.*;



public class CachingHTTPClient {
	


	/* main */
	public static void main(String []args) throws ParseException{


		/*** DECLARATIONS ***/
		int responseCode = 0;									//set to 0 at first, then reassign to what we get
		int statusCode = 0;										//set to 0 at first, then reassign to what we get
		boolean status = false; 								//false flag
		String expires = new String("Expires");		
		String maxage = new String("Cache-Control");			
		String ifmodifiedsince = new String("Last-Modified");	
		Date today = new Date();								//gives current date

		/*** DIRECTORY ***/
		File newDirectory = new File("/tmp/dpk326/assignment1");

		if( !newDirectory.exists()){

			/* does not exist make it */

			System.out.println("Creating the cache directory since it does not exist.");


			/* create */
			if(newDirectory.mkdirs()){
				System.out.println("Cache directory created.");
			} else{
				System.out.println("Cache directory creation failed.");
			}
		}
		

		/*** ERROR CHECKING ***/
		if (args.length < 1) {
			System.out.println("Usage:");
			System.out.println("java CachingHTTPClient <url>");
			System.exit(0);
		}

		URL url = null;
		
		try {
			url = new URL(args[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		/* assert for bad urls */
		assert url != null;
		
		String tempEn = new String(DatatypeConverter.parseBase64Binary(args[0]));
        String encoded = DatatypeConverter.printBase64Binary(tempEn.getBytes());

        int sizeofString = encoded.length();
        int findex = sizeofString-16;
        String subEncode = encoded.substring(findex,sizeofString);

		/*** GETTING URL RESPONSE ***/
		try{

			URLConnection connection = url.openConnection();
		
			/*** CREATE MAP OF HEADER AND VALUES ***/
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			Set<String> headerFieldsSet = headerFields.keySet();
			Iterator<String> headerFieldsItr = headerFieldsSet.iterator();

			while(headerFieldsItr.hasNext()){
				String headerFieldsKey = headerFieldsItr.next();
				List<String> headerFieldsValue = headerFields.get(headerFieldsKey);

				StringBuilder sb = new StringBuilder();
				for( String value: headerFieldsValue){
					sb.append(value);
					sb.append("");
				}
				//System.out.println(headerFieldsKey + "=" + sb.toString());
			}

			/*** NEED TO ADD BODY TO MAP ***/
			/*** CANT FIND A WAY TO SAVE THE BYTE BUFER INTO THE MAP AS A KEY VALUE WITH KEY BODY ***/
			/*** TRIED INPUT BYTE STREAM AND OUTPUT, NO AVAIL ***/

			/*** AT THIS POINT THE HEADERS AND BODY ARE SAVED IN THE MAP ***/

			//check the cache if there is the same folder of this url
			File check = new File("/tmp/dpk326/" + subEncode + ".ser");

			if(!check.exists()){
				
				FileOutputStream outputFile = new FileOutputStream("/tmp/dpk326/assignment1/" + subEncode + ".ser");

				ObjectOutputStream out = new ObjectOutputStream(outputFile);
				out.writeObject(headerFields);

				out.close();
				outputFile.close();

				System.out.println("Cache does not exist, so creating it...");
				System.out.println("Serialized data is saved in /tmp/dpk326/assignment1/" + subEncode + ".ser");
			}else{
				System.out.println("Cache exists for this URL.");
			}

			//if file exists, check thru the map for date headers
			if(check.exists()){

				//HEADER FIELDS IS CURRENT URL MAP
				//UNSERIALHEADER IS PREVIOUS CACHE MAP

				String expiresDate = "";
				String lastmodDate = "";
				String cachecontrolDate = "";
				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date todaysDate = new Date();

				//parse the date
				//Date parsed = format.parse(gotDate);


				/*** CACHE EXISTS, SO WE ARE GOING TO UNSERIALIZE
					AND GO THRU THE .SER AND COMPARE HEADERS ***/

				FileInputStream unserial = new FileInputStream("/tmp/dpk326/assignment1/" + subEncode + ".ser");
       		 	ObjectInputStream unserialdone = new ObjectInputStream(unserial);
       		 	Map<String, List<String>> unserialHeader  = (Map<String, List<String>>) unserialdone.readObject();
        		unserialdone.close();
        		unserial.close();
		
				//map is called unserialHeader

				/*** GET VALUES OF EXPIRES, MAX AGE, AND IF MODIFIED since ***/
				
				Set<String> checkKeys = unserialHeader.keySet();
				Iterator<String> checkItr = checkKeys.iterator();

				while(checkItr.hasNext()){
					//iterate thru and find the headers

					String tempKey = checkItr.next();

					//this string compare works
					if("Expires".equals(tempKey)){

						for(String s: unserialHeader.get(tempKey)){
							expiresDate += s;
							//Date parsed = format.parse(expiresDate);

							//parsed works here
						}
						Date parsed = format.parse(expiresDate);

						//now check expires parsed
						//todays date is before expires, so NOT expired, output from cache
						if(todaysDate.before(parsed)){
							System.out.println("***** Serving from the cache – start *****");

							FileInputStream unserialcache = new FileInputStream("/tmp/dpk326/assignment1/" + subEncode + ".ser");
      					 	ObjectInputStream unserialdonecache = new ObjectInputStream(unserialcache);
      					 	Map<String, List<String>> unserialHeadercache  = (Map<String, List<String>>) unserialdonecache.readObject();
     					   	unserialdonecache.close();
     					   	unserialcache.close();

        					Set<String> unserialKeyscache = unserialHeadercache.keySet();
        					Iterator<String> unserialItrcache = unserialKeyscache.iterator();

        					while(unserialItrcache.hasNext()){
        						String unserialnamecache = unserialItrcache.next();
        						List<String> unserialvaluecache = unserialHeadercache.get(unserialnamecache);
        						System.out.println(unserialnamecache + "=" + unserialvaluecache);
        					}
  
        					//output body from map here too

							System.out.println("***** Serving from the cache – end *****");
						}/* end date cmp */

						if("Last-Modified".equals(tempKey)){
							for(String s: unserialHeader.get(tempKey)){
								lastmodDate += s;
							}
							Date lastmodParse = format.parse(lastmodDate);
							System.out.println("last mod is: " + lastmodParse);

							if(lastmodParse.after(parsed)){
								//the last mod date is after expiration date
								//cache new data
								FileOutputStream recache = new FileOutputStream("/tmp/dpk326/assignment1/" + subEncode + ".ser");

								ObjectOutputStream outcache = new ObjectOutputStream(recache);
								outcache.writeObject(headerFields);

								outcache.close();
								recache.close();

								//recached new data, old overwrote

								System.out.println("***** Serving from the source – start *****");
								System.out.println("RESPONSE CODE: 200.");
								System.out.println("Caching new data and deleting/overwriting old.");


								Set<String> currentKeys = headerFields.keySet();
        						Iterator<String> currentItr = currentKeys.iterator();

       							while(currentItr.hasNext()){
        							String currentname = currentItr.next();
        							List<String> currentvalue = headerFields.get(currentname);
        							System.out.println(currentname + "=" + currentvalue);
       							}

       			 				//outputing the body
       							InputStream input = connection.getInputStream();
								byte[] buffer = new byte[4096];
								int n = - 1;

								while ( (n = input.read(buffer)) != -1){
								    if (n > 0){
			 	       					System.out.write(buffer, 0, n);
				   					 }
								}

								System.out.println("***** Serving from the source – end *****");
							}
							else if(lastmodParse.before(parsed)){
								//can be used
								//output
								System.out.println("***** Serving from the cache – start *****");
								System.out.println("RESPONSE CODE: 304.");
								System.out.println("Data in cache can be used to satisfy request.");

								FileInputStream unserialcache = new FileInputStream("/tmp/dpk326/assignment1/" + subEncode + ".ser");
      					 		ObjectInputStream unserialdonecache = new ObjectInputStream(unserialcache);
      					 		Map<String, List<String>> unserialHeadercache  = (Map<String, List<String>>) unserialdonecache.readObject();
     					   		unserialdonecache.close();
     					   		unserialcache.close();

        						Set<String> unserialKeyscache = unserialHeadercache.keySet();
        						Iterator<String> unserialItrcache = unserialKeyscache.iterator();

        						while(unserialItrcache.hasNext()){
        							String unserialnamecache = unserialItrcache.next();
        							List<String> unserialvaluecache = unserialHeadercache.get(unserialnamecache);
        							System.out.println(unserialnamecache + "=" + unserialvaluecache);
        						}
        					//output body from map here too

							System.out.println("***** Serving from the cache – end *****");
							}
						}
					}/* end if satement comparing expires to key */
				}/* end while loop*/
			}else{

				System.out.println("***** Serving from the source – start *****");

				//cache does not exist
				//output the website information here, it shuold already have a cache for the new url
				Set<String> currentKeys = headerFields.keySet();
        		Iterator<String> currentItr = currentKeys.iterator();

       			while(currentItr.hasNext()){
        			String currentname = currentItr.next();
        			List<String> currentvalue = headerFields.get(currentname);
        			System.out.println(currentname + "=" + currentvalue);
       			}

       			 //outputing the body
       			InputStream input = connection.getInputStream();
				byte[] buffer = new byte[4096];
				int n = - 1;

				while ( (n = input.read(buffer)) != -1)
				{
				    if (n > 0)
				    {
			 	       System.out.write(buffer, 0, n);
				    }
				}

				System.out.println("***** Serving from the source – end *****");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		/*** NOTES ***/

		//304 200
		//when u ping ther server again, if you get 200 -> overwrite the cache, 304 -> output same, no change
		//start time for max age is on request

	}
}
