/**
 * 
 */

var xmlhttp;

function loadXMLDoc(url,cfunc)
{
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=cfunc;
xmlhttp.open("GET",url,true);
xmlhttp.send();
}



function myFunction()
{
loadXMLDoc("http://localhost:8080/assignment7/myeavesdrop/counts/",function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    //document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
    //alert(xmlhttp.responseXML);
    //alert(xmlhttp.responseText);
 
    xmlDoc = xmlhttp.responseXML;
    
    //titles
    document.write("<table><tr><th>Meeting</th><th>Count</th></tr>");
    
    var x=xmlDoc.getElementsByTagName("meeting");
    
    for (i=0;i<x.length;i++)
    { 
    document.write("<tr><td>");
    document.write(x[i].getElementsByTagName("name")[0].childNodes[0].nodeValue);
    document.write("</td><td>");
    document.write(x[i].getElementsByTagName("count")[0].childNodes[0].nodeValue);
    document.write("</td></tr>");
    }
 
    //end tag for table 
    document.write("</table>");

    
    }
  });

//xmlhttp.responseText;
//this is all xml ^^






}