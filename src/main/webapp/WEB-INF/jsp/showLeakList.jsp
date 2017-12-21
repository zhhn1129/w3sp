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
	
	 <script type="text/javascript">
	 window.onload = function(){
		var myimg,oldwidth;
		var maxwidth = $(window).width()-18; //缩放系数
		 
		 $("div.leakContent table").each( function(){
			myimg = this;
			$(myimg).css("table-layout","fixed")
			if(myimg.width > maxwidth){
				oldwidth = $(myimg).css("width");
				$(myimg).css("width",maxwidth + "px")
				.css("height",$(myimg).css("height") * (maxwidth/oldwidth) + "px");
			}
		}); 
	};
</script>
 
</head>	
		
	<body>
	<nav class="newsTitle">
${leaks[0].nameC}，漏洞信息：
	</nav>
	<article class="topNews">
	<ul class="newsList" id="addMore">
		<c:forEach items="${leaks}" var="art">
		<div id="leakContent" class="leakContent" style="overflow:auto;">
		<table style="word-break:break-all; word-wrap:break-word;">
		<tr><td>漏洞URL：${art.url}</td></tr>
		<tr><td>重要程度：${art.score}</td></tr><tr><td>漏洞类型：${art.leakType}</td></tr>
		<tr><td>发生时间：<fmt:formatDate value="${art.eventDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td></tr>
		<tr><td>检测方法：${art.checkMethod}</td></tr>
		<tr><td>所属单位：${art.nameC}</td></tr>
		</table></div>
		<li style="border-bottom: 1px solid #eee;padding-top: 10px;"></li>
		</c:forEach>
	</ul>
	</article>

		
	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>