<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.jachsoft.cbir.ImageSearchEngine"%>
<%@page import="com.jachsoft.cbir.FileImageDatabase"%>
<%@page import="com.jachsoft.imagelib.RGBImage"%>
<%@page import="com.jachsoft.cbir.RGBColorContentDescriptor"%>
<%@page import="java.net.URL"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.jachsoft.cbir.SearchResult"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>COntent-BAsed Image Retrieval</title>
</head>
<body>
<h2>COntent-BAsed Image Retrieval</h2>
<form method="post" action="search.jsp">
Enter image URL: <input type="text" name="url" size="20">
<input type="submit" name="op" value="Search">
<input type="submit" name="op" value="Add">
</form>
</body>

<% 
	
	ImageSearchEngine engine = (ImageSearchEngine)session.getAttribute("engine");
	FileImageDatabase db = (FileImageDatabase)session.getAttribute("db");
	RGBImage input= null;
	RGBColorContentDescriptor inputDescriptor;
	String inputURL = request.getParameter("url");
	String action = request.getParameter("op");

	if (inputURL.length() == 0){
		inputURL = "http://i229.photobucket.com/albums/ee69/diwata87/pout.jpg";
	}

%>

<p>
<h4>Input Image: </h4>
<img src="<% out.print(inputURL); %>"></img>
</p>
<hr/>
<p>


<%	
	
	try{			
		URL ul = new URL(inputURL);
    	input = new RGBImage(ImageIO.read(ul));		
	}catch(MalformedURLException e){
		out.println("Error in URL!");
	}catch(IOException ioe){
		out.println("Error reading image!");
	}

	inputDescriptor = new RGBColorContentDescriptor(input);

	if (action.equals("Search")){
		List results = engine.search(inputDescriptor);
		Iterator ite = results.iterator();
		
		double percentSimilar = 0.20;
		
		int total = results.size();
		int relevantCount = (int)(percentSimilar*total);
		
		int j = 0;
		out.println("<h4>Search Results: " + relevantCount + " of " + total + "</h4>");
		out.println();
		
		while(ite.hasNext() && (j != relevantCount)){
			SearchResult result = (SearchResult)ite.next();
			j++;
%>
		<img src="<% out.println(result.getUrl()); %>" width="200" height="200"></img>
<%	
		}
	}else if (action.equals("Add")){		
		db.add(inputURL);
		db.save();
		out.println("Image succesfully added.");
	}

%>
</p>
</body>
</html>