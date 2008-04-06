<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.jachsoft.cbir.ImageSearchEngine"%>
<%@page import="com.jachsoft.cbir.FileImageDatabase"%>
<%@page import="com.jachsoft.cbir.ImageDatabase"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Leaf Image Retrieval System</title>
</head>
<body>
<%

	ImageSearchEngine engine = (ImageSearchEngine)session.getAttribute("engine");
	FileImageDatabase db = (FileImageDatabase)session.getAttribute("db");
	
	if (engine == null || db == null){
		engine = new ImageSearchEngine();
		db = new FileImageDatabase(this.getServletContext().getRealPath("dahon.dat"));	
		db.initialize();				
		engine.setImageDatabase(db);	
		session.setAttribute("engine",engine);
		session.setAttribute("db",db);
	}
%>


<h2>Leaf Image Retrieval System</h2>
<form method="post" action="search.jsp">
Enter image URL: <input type="text" name="url" size="20">
<input type="submit" name="op" value="Search">
<input type="submit" name="op" value="Add">
</form>
</body>
</html>