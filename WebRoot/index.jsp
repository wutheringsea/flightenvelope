<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body>
  <br/>
    This is the test FusionCharts Program.it will generate the Flight-Envlope diagram.
   	</br>
   	<form action="flightenvelopeServlet" method="post" enctype="multipart/form-data">
		Select the SpeedLine file : <input name="SpeedFile" type="file"/></br></br>
		Select the LimitedArea file : <input name="AreaFile" type="file"/></br></br>
		Select the SamplePoint file : <input name="PointFile" type="file"/></br></br>
		<input type="submit" value="Submit to try!" >		   	
   	</form>
  </body>
</html>
