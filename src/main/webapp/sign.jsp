<%@page import="com.cernet.postXML.SignatureUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'sign.jsp' starting page</title>
    
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
    signature:<%
    SignatureUtil s=new SignatureUtil();
    String appId=request.getParameter("id");
    String time=request.getParameter("time");
    String name=request.getParameter("name");
    
    String sign=s.digest(appId+time+name, "MD5");
    out.print(sign);
     %>
     <br>
   id: <%=request.getParameter("id")%> <br>
	time: <%=request.getParameter("time").trim()%> <br>
	name: <%=request.getParameter("name").trim()%> <br>
	</body>
</html>
