<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no">
		<link href="styles/layout.css" type="text/css" rel="stylesheet" />
		<link href="styles/main.css" type="text/css" rel="stylesheet" />
		
	<title>
	</title>
		
		<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
		<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script>

</head>	
		
	<body>
	<nav class="newsTitle">
	日志列表
	</nav>
	<article class="topNews">
	<form action="./showLeakInfo.do">
	<cwui:table name="plist" id="leakInfo" requestURI="" sort="list"
					class="main-table" >
				<cwui:column property="leakType" sortable="true" title="漏洞类型" />				
				<cwui:column property="nameC" sortable="true" title="学校名称" />
				<cwui:column property="priority" sortable="true" title="漏洞级别" />
				<cwui:column sortable="true" title="日期" >
				<fmt:formatDate value="${leakInfo.createTime}" pattern="yyyy-MM-dd" />
				</cwui:column>
				<cwui:column property="leakTitle" sortable="true" title="漏洞标题" />
				<cwui:column property="url" sortable="true" title="漏洞下载地址" />
				<cwui:column property="leakSource" sortable="true" title="漏洞来源" />
				<cwui:column sortable="true" title="推送状态" >
				<c:if test="${leakInfo.push eq '0'}">未推送</c:if>
				<c:if test="${leakInfo.push eq '1'}">已推送</c:if>
				</cwui:column>
			</cwui:table>
	</form>
	</article>

	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>
