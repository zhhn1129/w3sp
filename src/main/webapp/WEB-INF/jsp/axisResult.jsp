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
		<title>漏洞追踪统计报表</title>
		<link href="styles/style.css" type="text/css" rel="stylesheet" />
		<style>.fieldTh{
			white-space:nowrap;
		} </style>
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.1.min.js"></script>
		<script type="text/javascript">
 
		$(document).ready(function(){

		document.getElementById("axisCount").value="${axisCount}";
		document.getElementById("corpSubName").value="${corpSubName}";


	    $("td[class='td_axis']").each(function(){ 
	         		 if($(this).text()=="未处理"){
	       				$(this).attr("bgcolor","#cc0000");
		       		 }else if($(this).text()=="已修复"){
    						$(this).attr("bgcolor","#339933");
       					 }else if($(this).text()=="链接打不开"){
    						$(this).attr("bgcolor","#0066cc");
       					 }else if($(this).text()=="网络不通"){
    						$(this).attr("bgcolor","#ff9900");
       					 }	       		  
	     	   });          
		});
	
		function changeSelect(){
			location.href = encodeURI("axisResult.do?corpSubName=" + document.getElementById("corpSubName").value+"&axisCount="+ document.getElementById("axisCount").value);		
		}				
		</script>	
	</head>	
		
	<body>
	<%@include file="/WEB-INF/jsp/common/header.jsp"%>
			<div class="notice">
    		 	&nbsp;&nbsp;
				<a href="nodeResult.do">按学校统计报表</a>&nbsp;&nbsp;
				<a href="axisResult.do">漏洞追踪统计报表</a>&nbsp;&nbsp;  
				<a href="webLeakList.do">返回</a>
		  </div>
	
    	<div class="main">
    	<div class="mt5 mb5"> 

    <c:if test="${corpName!=null}">
     &nbsp;<span>当前分公司：${corpName}</span>
    <select name="corpSubName" id="corpSubName" style="display:none">
  		<option value="${corpName}">${corpName}</option>
  	</select>
    </c:if>
    <c:if test="${corpName==null}">
	<s:select cssStyle="" id="corpSubName" name="corpSubName" onchange="changeCorp()" cssClass="corpSubName"
		list="corpSubList" listKey="lov" listValue="lov" headerKey="" headerValue="--请选择分公司--" >
	</s:select>
	</c:if>
	 <select name="axisCount" id="axisCount" onchange=changeWeeks()>
  		<option value="0">请选择时间段</option>
  		<option value="1" >最近一周</option>
  		<option value="2" >最近二周</option>
  		<option value="3" >最近三周</option>
  		<option value="4" >最近四周</option>
  		<option value="5" >最近五周</option>
  		<option value="6" >最近六周</option>
  		<option value="7" >最近七周</option>
  		<option value="8" >最近八周</option>
 	 </select>&nbsp;
 	 <input type="button" value="提交" onclick="changeSelect()" class="btn">
 	 </div>
 	 
			<cwui:table name="axisList" requestURI="" sort="list" id="WebleakAxisView"
					class="list-table" toExcel="all">
			<c:if test="${axisCount>7}">
				<cwui:column property="status8" class="td_axis" sortable="true" title="${_8thFriday}" />
		 	</c:if>
		 	<c:if test="${axisCount>6}">
		 		<cwui:column property="status7" class="td_axis" sortable="true" title="${_7thFriday}" />
		 	</c:if>
			<c:if test="${axisCount>5}">
				<cwui:column property="status6" class="td_axis" sortable="true" title="${_6thFriday}" />
			</c:if>
			<c:if test="${axisCount>4}">
				<cwui:column property="status5" class="td_axis" sortable="true" title="${_5thFriday}" />
			</c:if>
			<c:if test="${axisCount>3}">	
				<cwui:column property="status4" class="td_axis" sortable="true" title="${_4thFriday}" />
			</c:if>
			<c:if test="${axisCount>2}">	
				<cwui:column property="status3" class="td_axis" sortable="true" title="${_3rdFriday}" />
				
			</c:if>
			<c:if test="${axisCount>1}">	
				<cwui:column property="status2" class="td_axis" sortable="true" title="${_2cdFriday}"/>
				
			</c:if>
			<c:if test="${axisCount>0}">	
				<cwui:column property="status1" class="td_axis"   sortable="true" title="${lastFriday}" />
				
			</c:if>
				
				<cwui:column property="nodeId" sortable="true" title="学校编号" />
				<cwui:column property="nameC" style="word-break:break-all" sortable="true" title="学校 " />				
				<cwui:column property="corpSubName"  sortable="true" title="分公司&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" />
				<cwui:column property="url" style="word-break:break-all" sortable="true" title="URL"/>
				<cwui:column property="leakType" style="word-break:break-all" sortable="true" title="漏洞类型"/>
				<cwui:column sortable="true" title="事件时间" width="70px">
					<fmt:formatDate value="${WebleakAxisView.eventDate}"
											pattern="yyyy-MM-dd" />
				</cwui:column>
				
			</cwui:table>
		</div>
			<%@include file="/WEB-INF/jsp/common/footer.jsp"%>			
	</body>
	
</html>							