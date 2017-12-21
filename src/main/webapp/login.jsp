<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统登录</title>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.js"></script>
<link href="styles/login.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
	function login(){
		$("#error").empty();
		$("#username").val($.trim($("#username").val()));
		$("#passwd").val($.trim($("#passwd").val()));
		var userName =$("#username").val();
		var passWord = $("#passwd").val();
		var error = "";
		var flag = true;
		if(userName==""){
			$("#error").append("用户名不能为空  ");
			flag = false;
		}
		if(passWord==""){
			$("#error").append("密码不能为空");
			flag = false;
		}
		if(flag){//提交
			document.getElementById("loginForm").submit();
		}
		
		
		
	}
</script>
<style type="text/css">
* {
	padding:0;
	margin:0;
}
body {
	background:url(images/login_bg.jpg);
	font-size:14px;
	font-family:"微软雅黑";
	color:#05508b;
}
.login-box {
	background: url(images/login_box.jpg) no-repeat scroll 0 0 transparent;
	height: 290px;
	left: 50%;
	margin-left: -360px;
	margin-top: -260px;
	padding-left: 160px;
	padding-top: 230px;
	position: absolute;
	top: 50%;
	width: 560px;
}
.login-btn {
	background-image:url(images/login_btn.jpg);
	background-position:0 0;
	width:80px;
	height:32px;
	cursor:pointer;
	border:0;
}
.login-btn:hover{
	background-position:0 32px;
}
table {
	border-collapse:collapse;
	border-spacing:0;
}
td {
	padding:8px 5px;
}
input[type="text"], input[type="password"] {
	width:180px;
	height:22px;
	font-family:"微软雅黑";
	color:#333;
	border:1px solid #8facc3;
}
.warning{
	color:#F00;
	font-size:12px;
}
</style>
</head>
<body>

<div  class="login-box">
<form id="loginForm" action="login.do" method="post">
  <table>
    <tr>
      <td class="alignright">用户名：</td>
      <td>
        <input type="text" id="username" name="username" class="text"></td>
    </tr>
    <tr>
      <td class="alignright">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
      <td>
        <input type="password" class="text" id="passwd" name="passwd"></td>
    </tr>
    <tr>
      <td></td>
      <td class="red" id="error"><s:property value="message"/></td>
    </tr>
    <!--<tr>
      <td></td>
      <td><input type="checkbox">
        记住用户名密码</td>
    </tr>-->
    <tr>
      <td></td>
      <td><input type="button" class="button" value="确定" onclick="login();"></td>
    </tr>
  </table>
</form>
</div>

</body>
</html>
