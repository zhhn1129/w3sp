<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no">

		<link href="styles/layout.css" type="text/css" rel="stylesheet" />
	<title>
	</title>
		
		<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
		<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script>
	
	
<script language="javascript" type="text/javascript">


 </script>
 
</head>	
		
	<body>
	<nav class="newsTitle">
	消息推送
	</nav>
	<article class="topNews">
	<form action="./sendSingleMsg.do">
	<table class="gg_detail">
	<tr><td style="width: 10%;">微信备注：</td><td><input type="text" name="remarkKeyword" ></td></tr>
	<tr><td>学校名：</td><td><input type="text" name="nameC" ></td></tr>
	<tr><td>重要程度：</td><td><input type="text" name="score" ></td></tr>
	<tr><td>漏洞类型：</td><td><input type="text" name="leakType" ></td></tr>
	<tr><td>发生时间：</td><td><input type="text" name="leakTime" onclick="WdatePicker()" class="Wdate"></td></tr>
	<tr><td>漏洞URL：</td><td><input type="text" name="leakUrl" style="width: 80%;"></td></tr>
	<tr><td><input type="submit" value="发送"></td><td style="color: red">${msgs}</td></tr>
	</table>
	</form>
	</article>

		
	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>