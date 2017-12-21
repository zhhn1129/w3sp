<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no">
		<link href="styles/layout.css" type="text/css" rel="stylesheet" />
		<title>${article.articleTitle}</title>
		
		<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
	<script type="text/javascript">
	 window.onload = function(){
		var myimg,oldwidth;
		var maxwidth = $(window).width()-18; //缩放系数
		 $("div.articleContent img").each( function(){
			myimg = this;
			if(myimg.width > maxwidth){
				oldwidth = $(myimg).css("width");
				$(myimg).css("width",maxwidth + "px")
				.css("height",$(myimg).css("height") * (maxwidth/oldwidth) + "px");
			}
		}); 
		 $("div.articleContent table").each( function(){
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
	<c:if test="${article.colId eq '3010'}">安全公告</c:if>
	<c:if test="${article.colId eq '3020'}">漏洞信息</c:if>
	<c:if test="${article.colId eq '3030'}">补丁信息</c:if>
	<c:if test="${article.colId eq '3040'}">研究报告</c:if>
	</nav>

	<article class="articleList" style="display: block;" id="articleList">
    <div class="head clearfix">
        <h3 class="title">${article.articleTitle}</h3>
        <h2>来源：${article.srcName}</h2>
    </div>
    <div class="text clearfix">
        <div class="articleContent" id="articleContent">
        	<div class="TRS_Editor" id="TRS_Editor" style="overflow:auto;">${article.articleContent}</div>        
        </div>
    </div>
	</article>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
	</body>
	
</html>							