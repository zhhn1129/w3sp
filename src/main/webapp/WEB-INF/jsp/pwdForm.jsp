
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
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.1.min.js"></script>
<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script><!--选择日期-->

<script type="text/javascript">
	function checkForm(){
		var oldPWD = $("#oldPWD").val();
		var newPWD = $("#newPWD").val();
		var renewPWD = $("#renewPWD").val();
		
		
		if(oldPWD == null || oldPWD == "" || newPWD == null || newPWD == "" || 
			renewPWD == null || renewPWD == ""){
			alert("密码不能为空。");
			return false;
		}
		if(renewPWD != newPWD){
			alert("两次输入新密码不一致。");
			return false;
		}
		if(!checkOldPwd()){
			return false;
		}
		document.getElementById("pwdForm").submit();
		
	}
	
	function checkOldPwd(){
		var res = "";
		var oldPWD = $("#oldPWD").val();
		$.ajax({
			  type: "post",
			  async:false,
			  url: "checkOldPWD.do",
			  dataType: 'text',
			  data: {'oldPWD':oldPWD},
			success: function(backData){
				res = backData;
			  }
		});
		if("wrong" == res){
			$("#oldPWD_notice").html("旧密码不正确");
			return false;
		}else{
			$("#oldPWD_notice").html("");
			return true;
		}
	}

</script>
</head>

<body>
		<%@include file="/WEB-INF/jsp/common/header.jsp"%>
		
		<div class="main mt10">
		<form id="pwdForm" name="pwdForm" action="savePWD.do?userId=${userId}" method="post" >
		  <table class="main-table">
		   
		    <tr>
		      <td class="tr fb">旧密码:<span class="warning">*</span></td>
		      <td>
		      	<input onblur="checkOldPwd()" type="password" id="oldPWD" name="oldPWD" />
		      	<span id="oldPWD_notice" style="color:red"></span>
		       </td>
		    </tr>
		    
		    <tr>
		      <td class="tr fb">新密码:<span class="warning">*</span></td>
		      <td><input type="password" id="newPWD" name="newPWD" /> </td>
		    </tr>
		    
		    <tr>
		      <td class="tr fb">确认新密码:<span class="warning">*</span></td>
		      <td><input type="password" id="renewPWD" name="renewPWD" /></td>
		    </tr>
		    <tr>
		    	<td></td>
		    	<td>
		    		<input onclick="checkForm()" type="button" value="确定" class="btn"/> &nbsp;&nbsp;&nbsp;
		    		<input type="button" value="取消" class="btn" onclick="history.back()"/>
		    	</td>
		    </tr>
		  </table>
		</form>
		</div>
		<div class="footer mt10"> <span class="left_span"> <span class="right_span"><span >版权所有：赛尔网络</span></span> </span> </div>
</body>
</html>