
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
		return true;
	}

</script>
</head>

<body>
		<%@include file="/WEB-INF/jsp/common/header.jsp"%>
		
		<div class="main mt10">
		&nbsp;&nbsp;&nbsp;&nbsp;密码修改成功，请重新登录。<a href="login.do">登录</a>
		</div>
		<div class="footer mt10"> <span class="left_span"> <span class="right_span"><span >版权所有：赛尔网络</span></span> </span> </div>
</body>
</html>