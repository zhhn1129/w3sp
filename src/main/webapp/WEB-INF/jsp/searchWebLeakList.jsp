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
		<title></title>
		<link href="styles/style.css" type="text/css" rel="stylesheet" />
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.1.min.js"></script>
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/DatePicker/WdatePicker.js"></script>

		<style>
		.fieldTh{ 
			white-space:nowrap;
		} 
		</style>	
		
		<script>

		$(document).ready(function(){
			var leak_status = $("#leak_status").html();
			$('#webLeak_status option[value='+leak_status+']').attr('selected',true);
				
			});
		</script>
		
	</head>	
		
	<body>
	<%@include file="/WEB-INF/jsp/common/header.jsp"%>
	<div class="notice">
    		<div class="mess">
    		<form action="./searchList.do" method="post">
    		<table width="100%">
    		<tr><td>漏洞URL:</td>
    		<td><input type="text" id="searchUrl" name="webLeakRealTimeView.url" value="${webLeakRealTimeView.url}"></td>
    		<td>学校:</td>
    		<td><input type="text" id="nameC" name="webLeakRealTimeView.nameC" value="${webLeakRealTimeView.nameC}"></td>
    		<td>分公司:</td>
    		<td><input type="text" id="corpSubName" name="webLeakRealTimeView.corpSubName" value="${webLeakRealTimeView.corpSubName}"></td>
    		<td>漏洞类型:</td>
    		<td><input type="text" id="leakType" name="webLeakRealTimeView.leakType" value="${webLeakRealTimeView.leakType}"></td>
    		<td>漏洞状态:<span id="leak_status" style="display: none">${webLeakRealTimeView.status}</span></td>
    		<td><select id="webLeak_status" name="webLeakRealTimeView.status"><option value="">请选择</option>
			 	<option value="未处理">未处理</option><option value="已修复">已修复</option>
			 	<option value="网络不通">网络不通</option><option value="链接打不开">链接打不开</option>
			 	</select></td>
			<td>事件时间:</td>
    		<td><input type="text" id="eventDate" name="webLeakRealTimeView.eventDate" onclick="WdatePicker()" class="Wdate" value="${eventDate}"/></td>
    		</tr>
    		<tr>
    		<td>厂商名:</td>
    		<td><input type="text" id="prodName" name="webLeakRealTimeView.prodName" value="${webLeakRealTimeView.prodName}"></td>
    		<td>软件名:</td>
    		<td><input type="text" id="softName" name="webLeakRealTimeView.softName" value="${webLeakRealTimeView.softName}"></td>
    		<td>漏洞评分:</td>
    		<td><input type="text" id="score" name="webLeakRealTimeView.score" value="${webLeakRealTimeView.score}"></td>		
    		<td><input type="submit" class="btn" value="查找" />&nbsp;&nbsp;
    		<a href="webLeakList.do">返回</a></td>
    		<td><a href="nodeResult.do">按学校统计报表</a> &nbsp;&nbsp;<a href="axisResult.do">漏洞追踪统计报表</a></td>
			<td><input type="hidden" name="msg" value="${msg }">&nbsp;&nbsp;</td>
    		</tr>
    		</table>

    		 </form>
				
			</div>
	</div>
		
	<div class="main mt10" >
			<cwui:table name="plist" id="webLeakView" requestURI="" sort="list" class="main-table" >				
				<cwui:column property="id"  sortable="true" title="序号" />
				<cwui:column property="nameC"  sortable="true" title="学校" />
				<cwui:column property="corpSubName"  sortable="true" title="分公司" />
				<cwui:column property="url"  sortable="true" title="漏洞链接" />
				<cwui:column property="leakType"  sortable="true" title="漏洞类型" />
				<cwui:column sortProperty="eventDate" sortable="true" title="事件时间" >
					<fmt:formatDate value="${webLeakView.eventDate}" pattern="yyyy-MM-dd"/>
				</cwui:column>
				<cwui:column property="prodName"  sortable="true" title="厂商名" />
				<cwui:column property="softName"  sortable="true" title="软件名" />
				<cwui:column property="score"  sortable="true" title="漏洞评分" />
				<cwui:column operationCol="true" style="text-align:left;" sortable="true" title="漏洞状态">
				<c:if test="${webLeakView.status eq '未处理'}"><span style="color:#FF0000;">${webLeakView.status}</span></c:if>
				<c:if test="${webLeakView.status eq '已修复'}"><span style="color:#339933;">${webLeakView.status}</span></c:if>
				<c:if test="${webLeakView.status eq '网络不通'}"><span style="color:#ff9900;">${webLeakView.status}</span></c:if>
				<c:if test="${webLeakView.status eq '链接打不开'}"><span style="color:#0066cc;">${webLeakView.status}</span></c:if>
				<c:if test="${currentUser.department eq '技术开发部'}">&nbsp;
				<a target='_blank' href='editWebLeakHandle.do?id=${webLeakView.id}'>处理</a>&nbsp;
				<a target='_blank' href='editWebLeak.do?url=${webLeakView.url}'>编辑</a>
				</c:if>
				<c:if test="${currentUser.department ne '技术开发部'}">
				<a target='_blank' href='editWebLeakHandle.do?id=${webLeakView.id}'>处理</a>
				</c:if>

				</cwui:column>
			</cwui:table>
		</div>
		<%@include file="/WEB-INF/jsp/common/footer.jsp"%>	
	</body>
	
</html>							