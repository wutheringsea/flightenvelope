<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String dataxml = (String)request.getAttribute("dataxml");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'flightenvelope.jsp' starting page</title>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="<%=basePath%>fusioncharts/FusionCharts.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css">
    	#circle { 
			width: 40px; 
			height: 40px; 
			background: red; 
			-moz-border-radius: 20px; 
			-webkit-border-radius: 20px; 
			border-radius: 20px; 
		}
    
    </style>	
   <script type="text/javascript">
    function myjs(){
    	alert("what the fuck");
    }
   </script>
  </head>
  
  <body>
  <div>
  	<div id="chartdiv" align="center" style="width: 810;height: 610;float: left"> 
        </div>
  	<script type="text/javascript">
      	 var charturl ="<%=basePath%>fusioncharts/Scatter.swf";
      	 var data = "<%=dataxml %>";
		 var chart = new FusionCharts(charturl,"ChartId","800","600");
		 chart.setDataXML(data);
		 chart.render("chartdiv");
		</script> 
    <!-- 
	<div id="legenddiv" style="float: left;width: 100;height: 100" >
		<div id="circle" style="float: left"></div>
		<span style="float: left;height: 40;position: relative">未编制</span>
	</div>
	 -->
	</div>	
  </body>
</html>
