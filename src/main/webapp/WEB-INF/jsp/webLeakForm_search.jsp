<%@ page pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站系统安全服务平台</title>
<link href="styles/base.css" type="text/css" rel="stylesheet" />
<link href="styles/main.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript" src="/scripts/DatePicker/WdatePicker.js"></script><!--选择日期-->


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
  <tr><th colspan="2">  <span class="ml10 warning fb">${searchMsg }</span><!--<span class="ml10 safe fb">成功查找到信息，如下。</span>--></th></tr>
    <tr>
      <td class="tr fb">URL:</td>
      <td>
      <c:if test="${searchFlag eq '1'}">
         <input id="url" name="url" type="text" value="${searchUrl }" />&nbsp;<span class="warning">*</span>
      </c:if>
      <c:if test="${searchFlag ne '1'}">
        <input id="url" name="url" type="text" value="${webLeak.url }" />&nbsp;<span class="warning">*</span>
      </c:if>
      </td>
    </tr>
    <tr>
      <td class="tr fb">单位名称:</td>
      <td><input id="nameC" name="nameC" type="text" value="${webLeak.nameC }" /></td>
    </tr>
    <tr>
      <td class="tr fb">所属分公司:</td>
       <td>
         <select name="subCorpList" list="subCorpList">
         <option value="">未知</option>
		          <c:forEach items="${subCorpList }" var="demo">
		            <option value="${demo.lov}" <c:if test="${demo.lov == webLeak.corpSubName}">selected</c:if>>
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
   			<td></td>
		    <td>
		    	<c:if test="${currentUser.department eq '技术开发部'}">
		     		<input type="button" value="保存" class="btn" onclick="return checkForm();"/>
		     	</c:if>
		    <input type="button" value="取消" onclick="location.href='webLeakList.do'" class="btn" /></td>
	</tr>
  </table>
  </s:form>
</div>
<div class="footer mt10"> <span class="left_span"> <span class="right_span"><span >版权所有：赛尔网络</span></span> </span> </div>
</body>
</html>