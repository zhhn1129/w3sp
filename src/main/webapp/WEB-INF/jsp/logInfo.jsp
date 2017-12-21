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
	<form action="./sendSingleMsg.do">
	<cwui:table name="plist" id="userInfo" requestURI="" sort="list"
					class="main-table" >
				<cwui:column property="username" sortable="true" title="用户" />	
				<cwui:column sortable="true" title="时间">
				<fmt:formatDate value="${userInfo.time}" pattern="yyyy-MM-dd hh:mm:ss"/>
				</cwui:column>				
				<cwui:column property="description" sortable="true" title="描述" />
				<cwui:column  sortable="true" title="动作类型" >
				<c:if test="${userInfo.action eq 'SendMsgTask' }"><span style="color:red">${userInfo.action}</span></c:if>
				<c:if test="${userInfo.action ne 'SendMsgTask' }">${userInfo.action}</c:if>
				</cwui:column>
				<cwui:column sortable="true" title="操作时间">
				<fmt:formatDate value="${userInfo.time}" pattern="yyyy-MM-dd"/>
				</cwui:column>
				<cwui:column property="userIp" sortable="true" title="用户操作IP地址" />
				<cwui:column property="location" sortable="true" title="访问地址" />
				<cwui:column  sortable="true" title="操作" >
				<a href="">增加</a>
				<c:if test="${userInfo.username ne '定时任务'}"><a href="">删除</a></c:if>
				<a href="">修改</a>
				</cwui:column>
			</cwui:table>
	</form>
	</article>

	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>
