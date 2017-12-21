		<%@page pageEncoding="utf-8"%>
		<script>
		$(document).ready(function(){
			$("#menu a").each(function(){
				if(location.href.indexOf($(this).attr("href")) > -1){
					$(this).parent().addClass("current");
				}
			});
		});
		</script>
		<div class="header">
			 <span class="logo">
			 	<img src="images/logo.jpg" /></span><span class="user-mess">欢迎您 ${contact}&nbsp;&nbsp; <a href="loginOut.do">退出</a><br/>
			 	<a href="changePWD.do">修改密码</a>
			 </span>
			 
  			<div class="nav" id="menu">
  			
  				 <h1><a href="webshellList.do">WEB后门</a></h1>
			     <h1><a href="botnetList.do">僵尸网络</a></h1>
			     <h1><a href="webLeakList.do">WEB漏洞</a></h1>
			   
  			</div>
		</div>
