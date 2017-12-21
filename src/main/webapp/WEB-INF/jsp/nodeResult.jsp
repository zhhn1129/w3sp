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
		<title>按学校统计报表</title>
		<link href="styles/style.css" type="text/css" rel="stylesheet" /><style>
		.fieldTh{
			white-space:nowrap;
		} 
		</style>	
		
	</head>	
		
	<body>
	<%@include file="/WEB-INF/jsp/common/header.jsp"%>
	<div class="notice">
		<div class="mess">
    		 	&nbsp;&nbsp;
				<a href="nodeResult.do">按学校统计报表</a>&nbsp;&nbsp;
				<a href="axisResult.do">漏洞追踪统计报表</a>&nbsp;&nbsp;  
				<a href="webLeakList.do">返回</a>
		 </div>
	</div>
	<div class="main mt10" >
			<cwui:table id="nodeResult" name="${nodeList}" requestURI="" sort="list" 
					class="list-table" allStatRow="true" toExcel="all">
				<cwui:column style="word-break:break-all" property="nameC" sortable="true" title="学校" allStat="true" allStatVal="合计" width="200px"/>			
				<cwui:column property="corpSubName" style="word-break:break-all" sortable="true" title="分公司" />
				<cwui:column property="countHost" sortable="true" title="主机数"/>
				<cwui:column property="statusRPR" sortable="true" title="已修复" allStat="true" allStatVal="${infoSum[0][0]}" style="background:#339933" class="tr"/>
				<cwui:column property="statusCLS" sortable="true" title="链接打不开" allStat="true" allStatVal="${infoSum[0][1]}" style="background:#0066cc" class="tr"/>
				<cwui:column property="statusLNK" sortable="true" title="网络不通" allStat="true" allStatVal="${infoSum[0][2]}" style="background:#ff9900" class="tr"/>			
				<cwui:column property="statusERR" sortable="true" title="未处理" allStat="true" allStatVal="${infoSum[0][3]}" style="background:#cc0000" class="tr"/>
				<cwui:column property="statusSum" sortable="true" title="合计" allStat="true" allStatVal="${infoSum[0][4]}" class="tr"/>
				<cwui:column property="statusPro" sortable="false" title="未处理漏洞占比" allStat="true" allStatVal="${statusProSum}" style="background:#ff9900" class="tr"/>
				
			</cwui:table>
	</div>
		<%@include file="/WEB-INF/jsp/common/footer.jsp"%>		
	</body>
	
</html>							