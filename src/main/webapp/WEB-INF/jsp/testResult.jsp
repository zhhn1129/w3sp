<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="cwui" prefix="cwui" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link href="styles/base.css" type="text/css" rel="stylesheet" />
		<link href="styles/main.css" type="text/css" rel="stylesheet" />
		<title>按分公司统计报表</title>
		<link href="styles/style.css" type="text/css" rel="stylesheet" />	
		<style>
		.fieldTh{ 
			white-space:nowrap;
		} 
		</style>	
	</head>	
		
	<body>
	<%@include file="/WEB-INF/jsp/common/header.jsp"%>
	<div class="notice">
    		<div class="mess">
				<a href="nodeResult.do">按学校统计报表</a>&nbsp;&nbsp; 
				<a href="axisResult.do">漏洞追踪统计报表</a>&nbsp;&nbsp;  
				<a href="webLeakList.do">返回</a>
			</div>
	</div>
		
	<div class="main mt10" >
			<cwui:table name="${infoList}"  requestURI="" sort="list"
					class="list-table"  allStatRow="true" >				
				<cwui:column property="corpSubName" sortable="true" title="分公司"  allStat="true" allStatVal="合计" width="100px"/>
				<cwui:column property="statusRpr" sortable="true" title="已修复" allStat="true" allStatVal="${infoSum[0][0]}" style="background:#6bc30d"/>
				<cwui:column property="statusCls" sortable="true" title="已关闭" allStat="true" allStatVal="${infoSum[0][1]}" style="background:#00ced1"/>
				<cwui:column property="statusLnk" sortable="true" title="连不上" allStat="true" allStatVal="${infoSum[0][2]}" style="background:#ff6000"/>
				<cwui:column property="statusErr" sortable="true" title="未处理" allStat="true" allStatVal="${infoSum[0][3]}" style="background:#FF0000"/>
				<cwui:column property="statusSum" sortable="true" title="合计" allStat="true" allStatVal="${infoSum[0][4]}" />
				<cwui:column property="statusPro" sortable="false" title="未处理漏洞占比" allStat="true" allStatVal="${statusProSum}" style="background:#ffff00"/>
			</cwui:table>
		</div>
		<%@include file="/WEB-INF/jsp/common/footer.jsp"%>	
	</body>
	
</html>							