<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no">

		<link href="styles/layout.css" type="text/css" rel="stylesheet" />
	<title>
	<c:if test="${colId eq '3010'}">安全公告</c:if>
	<c:if test="${colId eq '3020'}">漏洞信息</c:if>
	<c:if test="${colId eq '3030'}">补丁信息</c:if>
	<c:if test="${colId eq '3040'}">研究报告</c:if>
	</title>
		
		<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
	
	
<script language="javascript" type="text/javascript">

function appendMore(){
	var colId = $("#colId").html();
	var currentpage = parseInt($("#currentpage").html());
	var totalpage = $("#totalPage").html();
	$.ajax({ 
        url: 'ajaxGetAl.do?colId='+colId, 
        type: 'POST', 
        data: {currentpage: currentpage + 1}, 
        complete: function (xhr) {               
        if (200==xhr.status) {//成功返回
            currentpage++;
            $("#currentpage").html(currentpage);
            if (currentpage >= totalpage) $('#aMore').remove(); //加载所有页完毕去掉按钮
            var rText = eval(xhr.responseText);
            for(i=0;i<=rText.length;i++){
            	appendTxt = "<li class='newsHead'><a href='./getArticleById.do?articleId="+rText[i].articleId+"'>"+rText[i].articleTitle+"</a><span style='float:right;'>"+rText[i].articlePubTime+"</span></li>"
            	 $('#addMore').append(appendTxt);
                }
        	}
        	else alert('动态页出错，返回内容：'+xhr.responseText);
    	}
    });
} 
 </script>
 
</head>	
		
	<body>
	<nav class="newsTitle">
	<c:if test="${colId eq '3010'}">安全公告</c:if>
	<c:if test="${colId eq '3020'}">漏洞信息</c:if>
	<c:if test="${colId eq '3030'}">补丁信息</c:if>
	<c:if test="${colId eq '3040'}">研究报告</c:if>
	</nav>
	<article class="topNews">
	<ul class="newsList" id="addMore">
		<c:forEach items="${artls}" var="art">
		<li class="newsHead clearfix"><a id="articleId" href="./getArticleById.do?articleId=${art.articleId}">${art.articleTitle}</a>
		<span><fmt:formatDate value="${art.articlePubTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span></li>
		</c:forEach>
	</ul>
	
 <a class="pageBtn" href="javascript:void(0);" id="aMore" onclick="appendMore();">查看更多信息</a>
		<span id="colId" style="display: none;">${colId}</span>
		<span id="totalPage" style="display: none;">${totalPage}</span>
		<span id="currentpage" style="display: none;">${currentpage}</span>
	</article>

		
	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>