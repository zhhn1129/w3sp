<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html >
<head>
	<meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="generator" content="AppFuse 2.0" />
	<title>网站系统安全服务平台</title>
	<link href="styles/base.css" type="text/css" rel="stylesheet" />
	<link href="styles/main.css" type="text/css" rel="stylesheet" />
	<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script><!--选择日期-->
	
	<script type="text/javascript">

		function checkForm(){
			  
			var handleDate = document.getElementById("handleDate").value;
			if(handleDate == "" || handleDate == null ){
				alert("请选择处理时间！");
				return false;
			}
			document.getElementById("memo").value = document.getElementById("remark").value;
		    return true; 
		}
		
		function search(){
			var url = document.getElementById("searchUrl").value;
			if(url == "" || url == null ){
				alert("请输入URL信息！");
				return ;
			}
			
			location.href="search.do?url="+url;
		}
	</script>
</head>

<body>
<%@include file="/WEB-INF/jsp/common/header.jsp"%>
<div class="notice">
  <div class="mess">请输入要查找的URL：
		
			 	<input type="text" id="searchUrl" name="searchUrl">
    		 	<input type="button" class="btn" onclick="search()" value="查找" />
    		    <input type="hidden" name="msg" value="${msg }">
  </div>
</div>
<div class="main mt10">
		<form onsubmit="return checkForm();" id="wlhForm" name="wlhForm" action="saveWLH.do?wlhId=${wlhId}" method="post" >
		<table class="main-table">
			<tr>
				<th>处理时间</th>
				<th>状态</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
			<tr>
				<td><input name="handleDate" id="handleDate" type="text" onclick="WdatePicker()" class="Wdate"></td>
				<td>
					<select name="handleStatus">
						<c:forEach items="${handleStatusList }" var="demo">
				            <option value="${demo.lov}">
				              ${demo.lov}
		        	    	</option>
		          		</c:forEach>
					</select>
				</td>
				<td>
					<input type="hidden" id="memo" name="memo"/>
					<textarea id="remark" cols="50" rows="2"></textarea>
				</td>
				<td><input type="submit" class="btn"  value="提交" /> &nbsp; <input
					type="button" onclick="location.href='webLeakList.do'" class="btn" value="取消" /></td>
			</tr>
			<tr>
				<td colspan="4">
					
					<table class="second-table">
						<tr>
							<th width="15%">处理日期</th>
							<th width="10%">处理人</th>
							<th width="15%">状态</th>
							<th width="60%">备注</th>
						</tr>
						<c:if test="${wlhListSize eq 0}">
							<tr><td colspan="4">没有数据。</td></tr>
						</c:if>					
						<c:if test="${wlhListSize != 0}">		
							<c:forEach var="wld" varStatus="cur" items="${wlhList}">
								<tr>
									<td><fmt:formatDate value="${wld.handleDate}"
											pattern="yyyy-MM-dd" /></td>
									<td>${wld.addopr}</td>
									<td>${wld.status}</td>
									<td>${wld.memo}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					
				</td>
			</tr>
		</table>
		</form>
	</div>
<div class="footer mt10"> <span class="left_span"> <span class="right_span"><span >版权所有：赛尔网络</span></span> </span> </div>
</body>
</html>






















