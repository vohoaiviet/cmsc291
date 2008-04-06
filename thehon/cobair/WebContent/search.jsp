<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.jachsoft.cbir.ImageSearchEngine"%>
<%@page import="com.jachsoft.cbir.FileImageDatabase"%>
<%@page import="com.jachsoft.imagelib.RGBImage"%>
<%@page import="com.jachsoft.cbir.RGBColorContentDescriptor"%>
<%@page import="com.jachsoft.cbir.OutlineDescriptor"%>
<%@page import="java.net.URL"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.net.MalformedURLException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.jachsoft.cbir.SearchResult"%>
<%@page import="com.jachsoft.cbir.ImageDatabaseEntry"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Leaf Image Retrieval System</title>
</head>
<body>
<h2>Leaf Image Retrieval System</h2>
<form method="post" action="search.jsp">
Enter image URL: <input type="text" name="url" size="20">
<input type="submit" name="op" value="Search">
<input type="submit" name="op" value="Add">
</form>

<% 
	
	ImageSearchEngine engine = (ImageSearchEngine)session.getAttribute("engine");
	FileImageDatabase db = (FileImageDatabase)session.getAttribute("db");
	RGBImage input= null;
	OutlineDescriptor inputDescriptor;
	String inputURL = request.getParameter("url");
	String action = request.getParameter("op");

	if (inputURL.length() == 0){
		inputURL = "http://localhost/leaf/banaba1779";
	}

%>

<p>
<h4>Input Image: </h4>
<img src="<% out.print(inputURL); %>"></img>

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

	inputDescriptor = new OutlineDescriptor(input);

	if (action.equals("Search")){
		List results = engine.search(inputDescriptor);
		Iterator ite = results.iterator();
		
		double percentSimilar = 1.0;//0.20;
		
		int total = results.size();
		int relevantCount = (int)(percentSimilar*total);
		
		int j = 0;
		out.println("<h4>Search Results: " + relevantCount + " of " + total + "</h4>");
		out.println();
		
		while(ite.hasNext() && (j != relevantCount)){
			SearchResult result = (SearchResult)ite.next();
			j++;
			out.println("<p>"+result.getDistanceFromInput()+"</p>");
%>
		<img src="<% out.println(result.getUrl()); %>" width="200" height="200"></img>		
<%	
		}
	}else if (action.equals("Add")){
		ImageDatabaseEntry entry = db.createEntry(inputURL);
		db.add(entry);
		db.save();
		//entry.getDescriptor().normalize();
		out.println("Image succesfully added.");
	}

%>
</p>
</body>
</html>