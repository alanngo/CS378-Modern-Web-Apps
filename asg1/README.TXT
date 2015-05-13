CS378 Modern Web Apps
Assignment 1: Caching HTTP Client 
David Ko
UT EID: dpk326

Follow up questions:

1. How do you detect a resource is available in your cache?
	In my implementatoin, to detect a resource, first I 64 encode the URL and get the last 9 characters to make it unique, and append a string path, "/tmp/dpk326/" + encode + ".ser" 
	and check if that file exists, if it exists, then there was a previous cache.
	To check the cache, I unserialize the .ser file and interate thru the Map object that is in there. The map object is the way i store the headers and body data. The map's keys are the header titles, and the key values are the values of those headers. I iterate through them and find the 3 headers: last modified,cache-control, and expires. I then compare these cache values with the current Map of the current URL's header values and then proceed as necessary. If the data is expired, and cant be used, then i overwrite the .ser file with the same 64 encode into a .ser file, if it is good to use, we call the response codes and output from cache, otherwise output from source. If there is no cache, we save the data into a new unique .ser file, and then output from source. If there exists the .ser file with unexpired cache data, we output from cache.
2. How do you handle dynamic content?
	I use a Map object and save all the content into a map, and then the map is written to a .ser file and then i proceed to check headers and from there i output to the screen depending on what conditional is satisified.

References:
used Kulkarni's URl connection file, and used alot of .ser documentation to create the .ser files (since i had no experience with it). Also used references for SimpleDateFormat for date comparison on oracle's website using the Date API.

