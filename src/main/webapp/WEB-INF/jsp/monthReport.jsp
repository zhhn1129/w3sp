<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<link href="styles/layout.css" type="text/css" rel="stylesheet" />
<link href="styles/main.css" type="text/css" rel="stylesheet" />

<title></title>

<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script>
<script src="scripts/echarts.js"></script>
</head>
<body>
<nav class="newsTitle">
漏洞月报
</nav>
<article class="topNews">
<form action="./monthReport.do">
<cwui:table name="plist" id="monthReport" requestURI="" sort="list" class="main-table">
	<cwui:column property="leakType" sortable="true" title="漏洞类型" />
	<cwui:column property="totalNum" sortable="true" title="总漏洞数" />
	<cwui:column property="notFinished" sortable="true" title="未处理漏洞数" />
	<cwui:column property="percent" sortable="true" title="未处理占比" />
	<cwui:column property="lastTime" sortable="true" title="最近一次更新时间">
	<%-- 
	<fmt:formatDate value="${monthReport.lastTime}"	pattern="yyyy-MM-dd" />
	--%>
	</cwui:column>
	
</cwui:table>
<%--
	<cwui:column property="runStatus" sortable="true" title="运行状态" />
	<cwui:column sortable="true" title="连续运行时间"> ${LeakStruts2.runHours}H   </cwui:column>
	<cwui:column property="statusPro" sortable="true" title="未处理漏洞占比" />-->
	<cwui:column property="totalNum" sortable="true" title="数量" />
	<!--<cwui:column property="totalNum" sortable="true" title="运行状态" />
--%>
</form>
<br></br>
<br></br>
<br></br>
</body>
</html>