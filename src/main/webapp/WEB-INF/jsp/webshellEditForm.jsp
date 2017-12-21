<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站系统安全服务平台</title>
<link href="styles/base.css" type="text/css" rel="stylesheet" />
<link href="styles/main.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script><!--选择日期-->

</head>

<body>
		<%@include file="/WEB-INF/jsp/common/header.jsp"%>
		<div class="main mt10">
		<form id="webshellForm" name="webshellForm" action="saveWebshell.do?url=${webshell.url}" method="post" >
		  <table class="main-table">
		    <tr>
		      <td class="tr fb">事件日期:</td>
		      <td>
		      	<fmt:formatDate value="${webshell.eventDate}"
											pattern="yyyy-MM-dd" />
		      </td>
		    </tr>
		    
		    <tr>
		      <td class="tr fb">被控链接:</td>
		      <td>${webshell.url}</td>
		    </tr>
		    <tr>
		      <td class="tr fb">被控地址:</td>
		      <td>${webshell.ctrledIp}</td>
		    </tr>
		    <tr>
		      <td class="tr fb">被控单位名称:</td>
		      <td><input type="text" id="nameC" name="nameC" value="${webshell.nameC }" /></td>
		    </tr>
		    <tr>
		      <td class="tr fb">
		        	 所属分公司：
		      </td>
		      <td>
		        <select name="subCorpList" list="subCorpList">
		          
		           <option value="">未知</option>
		          <c:forEach items="${subCorpList }" var="demo">
		      
		            <option value="${demo.lov}"  <c:if test="${demo.lov == webshell.corpSubName}">selected</c:if>  >
		              ${demo.lov}
		            </option>
		          </c:forEach>
		        </select>
		      </td>
		    </tr>
		     <tr>
		    <td></td>
		     <td >
		     	<input type="submit" value="保存" class="btn" />
		     	
		     	<input type="button" onclick="location.href='webshellList.do'" value="取消" class="btn" />
		     </td>
		    </tr>
		  </table>
		  <form>
		</div>
		<div class="footer mt10"> <span class="left_span"> <span class="right_span"><span >版权所有：赛尔网络</span></span> </span> </div>
</body>
</html>