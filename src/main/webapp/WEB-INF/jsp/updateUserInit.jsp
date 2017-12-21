<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no">

		<link href="styles/layout.css" type="text/css" rel="stylesheet" />
	<title>更新微信用户信息</title>
		
		<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
	
	
<script language="javascript" type="text/javascript">

function updateUser(){
	var msg = "";
	
	$.ajax({
		type : "post",
		async : false,
		url : "updateWeChatUser.do",
		dataType : 'text',
		data : {},
		success : function(backData) {
			msg = backData;
			$("#msgs").html("<a class='pageBtn'>"+msg+"</a>");
			$("#updateUser").css("display","none");
		}
	});
} 
 </script>
 
</head>	
		
	<body>
	<nav class="newsTitle">更新微信用户</nav>
	<article class="topNews">
	<div style="padding-top: 50px;"></div>
	<span id="msgs"></span>
 	<a class="pageBtn" href="javascript:void(0);" id="updateUser" onclick="updateUser();">点击更新用户</a>
	<div style="padding-bottom: 100px;"></div>
	</article>

		
	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>