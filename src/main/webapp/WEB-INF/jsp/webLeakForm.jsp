
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

<script type="text/javascript">

function checkForm(){
	  
	var url = document.getElementById("url").value;
	if(url == "" || url == null ){
		alert("请输入URL信息！");
		return ;
	}
	if(url.indexOf("http://")!=0){
		alert("请输入正确的URL信息！");
		return;
	}
	
	  document.getElementById("webLeakForm").submit();
	  
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
		  </div>
		</div>
		<div class="main mt10">
		<s:form id="webLeakForm" name="webLeakForm" action="save" method="post" validate="true">
		  <table class="main-table">
		    <tr>
		      <td class="tr fb">URL:</td>
		      <td><a href="#">${webLeak.url }</a>
		          <input type="hidden" id="url" name="url" value="${webLeak.url }"/> 
		      </td>
		    </tr>
		    
		    <tr>
		      <td class="tr fb">单位名称:</td>
		      <td><input type="text" id="nameC" name="nameC" value="${webLeak.nameC }" /> <span class="warning">*</span></td>
		    </tr>
		    <tr>
		      <td class="tr fb">二级单位:</td>
		      <td><input type="text" id="nameC2" name="nameC2" value="${webLeak.nameC2 }" /></td>
		    </tr>
		    
		    <tr>
		      <td class="tr fb">
		        	 所属分公司：
		      </td>
		      <td>
		        
		        
		        <select name="subCorpList" list="subCorpList">
		          
		           <option value="">未知</option>
		          <c:forEach items="${subCorpList }" var="demo">
		      
		            <option value="${demo.lov}"  <c:if test="${demo.lov == webLeak.corpSubName}">selected</c:if>  >
		              ${demo.lov}
		            </option>
		          </c:forEach>
		        </select>
		      </td>
		    </tr>
		    
		    
		    <tr>
		      <td class="tr fb">
		        	 漏洞种类：
		      </td>
		      <td>
		        
		        <select name="ldList" list="ldList">
		        <option value="">未知</option>
		          <c:forEach items="${ldList }" var="demo">
		            <option value="${demo.lov}" <c:if test="${demo.lov == webLeak.leakType}">selected</c:if>>
		              ${demo.lov}
		            </option>
		          </c:forEach>
		        </select>
		      </td>
		    </tr>
		    

		    <tr>
		      <td class="tr fb">所属路径:</td>
		      <td><input id="path" name="path" type="text" value="${webLeak.path }" /></td>
		    </tr>
		    <tr>
		      <td class="tr fb">事件发生时间:</td>
		      <td>
		      
		      <input id="eventDate" name="eventDate" type="text" onclick="WdatePicker()" class="Wdate" value="<fmt:formatDate value="${webLeak.eventDate }" pattern="yyyy-MM-dd" />" />
		      
		      
		      </td>
		    </tr>
		    <tr>
		    <td></td>
		     <td >
		     	<input type="button" value="保存" class="btn" onclick="return checkForm();"/>
		     	
		     	<input type="button" onclick="location.href='webLeakList.do'" value="取消" class="btn" />
		     </td>
		    </tr>
		  </table>
		  </s:form>
		</div>
		<div class="footer mt10"> <span class="left_span"> <span class="right_span"><span >版权所有：赛尔网络</span></span> </span> </div>
</body>
</html>