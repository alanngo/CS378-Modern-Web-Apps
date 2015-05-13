David Ko
dpk326
Assignment 4

PLEASE READ - notes on this assignment:

I understand this assignment in and out, i just couldn't get the actual program to work 100% with functionality. I do make a connection in my services to get the html, find the link and then convert to XML. But, if you look in my EavesdropResources.java, where i do my xml parsing and output, something is wrong with the way i attempted it, even though it is similiar to what i was using on previous assignments. it output the union of the projects, but it doesnt do it in XML format even though thats how I had wrote it. Not sure what part I am doing wrong, i looked at alot of documentation on jsoup, junit and jaxb output/parsing to try and get it right but it just wont, so it outputs it all just one by one without <project> </project> tags. In my EavesdropService, i create the document variable to use, then use the link passed through to create the Document class object on that link. Then i create elements object with botht he project names and the links. I do the links so i can access them and get their xml. At the bottom i ahve 2 methods to create the output, one is getData from the orginal, and the other is getxml.

It also took a while for me to even be able to get the project form github to work, for some reason in my Eclipse IDE, if you do a maven update and load your dependencies, it actually clears all your deployments. I was in office hours with Edward when we were trying to figure out why this was happening, and there was another student htere as well with the same problem. I followed the steps in the github instructions, and after if you do a clean and build OR a maven update, it clears your deployments. Just a slight complication with Eclipse that for some reason not all students had a problem with.

Another problem is that when I do the GET ..../{project}/irclogs, the {project} is saved to my variable projectparam, and when i was doing simple testing and running, the link it puts changes the %23 to a #, so i did a string replace, but for some reason it just would not do it, and no matter whihc way i created the string link, it always gave back ..../projects/#heat/irclogs and keeps blocking that GET method form working correctly. I dont know if i was thinking about it wrong or just implementing it in a too simplistic way. 



