<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<html>
	<head>
		<title>网站系统安全服务平台</title>
        <meta http-equiv="Cache-Control" content="no-store"/>
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="generator" content="AppFuse 2.0" />
        <link href="styles/base.css" type="text/css" rel="stylesheet" />
		<link href="styles/main.css" type="text/css" rel="stylesheet" />
		<link href="styles/Site.css" type="text/css" rel="stylesheet" />
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/DatePicker/WdatePicker.js"></script><!--选择日期-->
        <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.4.1.min.js"></script>
		<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/scripts/TableSort.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				var department = '${currentUser.department}';
				if("技术开发部" != department){
					$("#myTable").find("tr[id^='parentoneId']").find("td:first").find("img").each(function(){
						$(this).click();
					});
					
				}
			});
		</script>
		<script type="text/javascript">
	        $(function () {
	           
	            
	            $("#myTable th").click(function(){
	            	$("#myTable tr table").parents("tr").remove();
	            	
	            	$("#myTable img[operated='no']").each(function(){
		            	$(this).attr("src", "images/add.jpg");
		            });
	            });
	            
	            $("#myTable").sorttable({
	                ascImgUrl: "images/bullet_arrow_up.png",
	                descImgUrl: "images/bullet_arrow_down.png",
	                ascImgSize: "8px",
	                descImgSize: "8px",
	                onSorted: function (cell) {
	                    
	                }
	            });
	            
	            
	            
	        });
    	</script>       
		
		<script type="text/javascript">
		
		(function($){
	        // add a new method to JQuery

	        $.fn.equalHeight = function() {
	           // find the tallest height in the collection
	           // that was passed in (.column)
	            tallest = 0;
	            this.each(function(){
					$(this).removeAttr('style');
	                thisHeight = $(this).height();
	                if( thisHeight >tallest)
	                    tallest = thisHeight;
						$(this).height(tallest);
	                if( thisHeight < tallest)
	                    thisHeight = tallest;
	            });

	            // set each items height to use the tallest value found
	            this.each(function(){
	                $(this).height(tallest);
	            });
	        }
	    })(jQuery);
		
		
		//缩放二级表格实现
		function hostSumDetail(field){
			var id = $(field).attr("entityId");
			$("#tr_" +id).toggle();
			
			//图片加减切换
			var imageSrc = $(field).attr("src");
			
			if("images/minus.jpg"==imageSrc){
				$(field).attr("src","images/add.jpg");//将减号换成加号
				$("#tr_" +id).remove();
				//detailTable.style.display="none";
				$('.column').equalHeight();
				//secondTb
			}else{
				var newTr = "<tr style=\"display:none;\" id=\"tr_"+id+"\"><td colspan=\"10\"><table class=\"second-table\"  style=\"display:''\" id=\"one_"+id+"\" operated=\"no\"></table></td></tr>";
				$("#parentoneId" +id).after(newTr);
				var detailTable = document.getElementById("one_" +id);
				 
				$(field).attr("src","images/minus.jpg");//将加号换成减号,显示详细信息
				var operated = $(detailTable).attr("operated");
				
				if(operated=="no"){
					//去除 已关闭 已修复 连不上三列
					//var tableTitle="<tr role=\"head\" ><th ></th><th sort=\"true\" >序号</th><th  sort=\"true\">学校名称</th><th sort=\"true\" class='tr' >主机数</th><th class='tr' sort=\"true\" >漏洞数</th><th sort=\"true\" class=\"tr\" >未处理</th><th sort=\"true\" class=\"tr\" >已修复</th><th sort=\"true\" class=\"tr\" >连不上</th><th sort=\"true\" class=\"tr\" >已关闭</th></tr>";
					var tableTitle="<tr role=\"head\" ><th ></th><th sort=\"true\" >序号</th><th  sort=\"true\">学校名称</th><th  sort=\"true\">类型</th><th sort=\"true\" class='tr' >主机数</th><th class='tr' sort=\"true\" >漏洞数</th><th sort=\"true\" class=\"tr\" >未处理</th></tr>";
					//ajax得到详细数据
					var tableContext=invokeajax($(field).attr("queryCondition"),id);
					detailTable.style.display="";
					$(detailTable).empty();
					
					//$(detailTable).append(tableTitle);
					//$(detailTable).append(tableContext);
					
					$("#one_" +id).html(tableTitle + tableContext);
			
					$("#one_" + id + " th").click(function(){				
						$("#one_" +id).find("tr[id^='twotr']").remove();
		            	$("#one_" + id + " img[operated='no']").each(function(){
			            	$(this).attr("src", "images/add.jpg");
			            });
		            });
					
					
					$("#one_" +id).sorttable({
		                ascImgUrl: "images/bullet_arrow_up.png",
		                descImgUrl: "images/bullet_arrow_down.png",
		                ascImgSize: "8px",
		                descImgSize: "8px",
		                onSorted: function (cell) {
		                    
		                }
		            });
			       
					
					$("#one_" +id).parent().parent()[0].style.display="";
					$("#one_" +id)[0].style.display="";
				
					$(detailTable).attr("operated","no");
					
				}else{
					detailTable.style.display="";
					$('.column').equalHeight();
				}
			 
			}
		}
		
		
		//请求并组织缩放一级表格第一张表的数据
		function invokeajax(queryCondition,id){
			
			var temp = queryCondition.split(";");
			var id =id;
			var corpSubName = temp[0];
			//var dateType = temp[1];
			//var sumType = temp[2];
			//var serverCate = temp[3];
			var result = "";
			$.ajax({
				  type: "post",
				  async:false,
				  url: "webLeakSubList.do",
				  dataType: 'json',
				  data: {'corpSubName':corpSubName},
				success: function(backData){
					
					//alert(backData);
					if(backData.length == 0){
						result+="<tr>";
						result+="<td>暂无数据</td>";<%//排名%>
						
						result+="</tr>";
					}else{
						for(var i=0;i<backData.length;i++){
							result+="<tr id='parenttwoId"+ backData[i].id +"'>";
							result+="<td><img src='images/add.jpg' onclick='hostSumThreeDetail(this);' queryCondition='"+backData[i].corpSubName+";"+backData[i].nameC+"'  operated='no'  entityId='"+backData[i].id+"'/> </td>";
							
							//result+="<td >"+backData[i].id+"</td>";
							result+="<td >"+(i+1)+"</td>";
							
							result+="<td>"+backData[i].nameC+"</td>";
							result+="<td>"+backData[i].nodeType+"</td>";
							result+="<td class='tr'>"+backData[i].countHost+"</td>";
							result+="<td class='tr'>"+backData[i].countURL+"</td>";
							result+="<td class='tr'>"+backData[i].statusERR+"</td>";
							//result+="<td class='tr'>"+backData[i].statusRPR+"</td>";
							//result+="<td class='tr'>"+backData[i].statusLNK+"</td>";
							//result+="<td class='tr'>"+backData[i].statusCLS+"</td>";
							//result+="<td class='tr'>"+backData[i].pathNum+"</td>";
							
							result+="</tr>";
							
				//			result+="<tr style='display:none;' id='twotr_"+id+""+backData[i].id+"'><td colspan='7'><table class='third-table'  style=\"display: '';\" id='three_"+backData[i].id+"' operated='no'></table></td></tr>";
							
						}
						var department = '${currentUser.department}';
						if("技术开发部" != department){
							result+="<tr><td>合计</td><td colspan='3'></td><td class='tr'>" + '${sumHost}' + "</td>"+
							"<td class='tr'>" + '${sumUrl}' +"</td>" + 
							"<td class='tr'>" + '${sumErrWL}' + "</td>" +
							"</tr>";
						}
						
					}
				  }
			});//end $.ajax  
			
			return result
		} 
	
		//缩放二级表格第一张表下面的三级表格实现
		function hostSumThreeDetail(field){
			var id = $(field).attr("entityId");
			$("#twotr_" +id).toggle();
			var imageSrc = $(field).attr("src");
			
		//	var threedetailTable = document.getElementById("three_" +id);
			
			if("images/minus.jpg"==imageSrc){
				$(field).attr("src","images/add.jpg");//将减号换成加号
				$("#twotr_" +id).remove();
				//threedetailTable.style.display="none";
				$('.column').equalHeight();
				
			}else{
				var newTr = "<tr style=\"display:none;\" id=\"twotr_"+id+"\"><td colspan=\"7\"><table class=\"third-table\"  style=\"display:''\" id=\"three_"+id+"\" operated=\"no\"></table></td></tr>";
				$("#parenttwoId" +id).after(newTr);
				var threedetailTable = document.getElementById("three_" +id);
				
				$(field).attr("src","images/minus.jpg");//将加号换成减号,显示详细信息
				var operated = $(threedetailTable).attr("operated");
				if(operated=="no"){
					var tableTitle="<tr role=\"head\"><th sort=\"true\" width='10%'>序号</th><th sort=\"true\" width='10%'>二级单位</th><th sort=\"true\" class='tr' width='10%'>漏洞标识</th><th sort=\"true\" class='tr' width='25%'>漏洞链接</th><th sort=\"true\" class='tr' width='10%'>漏洞类型</th><th class='tr' sort=\"true\" width='25%'>所在路径</th><th class='tr' width='10%'>当前状态</th></tr>";
					//ajax得到详细数据
					var tableContext=threeinvokeajax($(field).attr("queryCondition"));
					
					threedetailTable.style.display="";
					$(threedetailTable).empty();
				//	$(threedetailTable).append(tableTitle);
				//	$(threedetailTable).append(tableContext);
					
					$("#three_" +id).html(tableTitle + tableContext);
					
					$("#three_" +id).sorttable({
		                ascImgUrl: "images/bullet_arrow_up.png",
		                descImgUrl: "images/bullet_arrow_down.png",
		                ascImgSize: "8px",
		                descImgSize: "8px",
		                onSorted: function (cell) {
		                    
		                }
		            });
					
					$("#three_" +id).parent().parent()[0].style.display="";
					$("#three_" +id)[0].style.display="";
					
					$(threedetailTable).attr("operated","no");
					$('.column').equalHeight();
				} else {
					threedetailTable.style.display="";
					$('.column').equalHeight();
				}
				
				$('.column').equalHeight();
			}
		}
		
		//请求并组织二级表格第一张表下面的三级缩放表格的数据
		function threeinvokeajax(queryCondition){
			var temp = queryCondition.split(";");
			var nameC = temp[1];
		    var corpSubName=temp[0];
			var result = "";
			$.ajax({
				  type: "post",
				  async:false,
				  url: "webLeakSubList2.do",
				  dataType: 'json',
				  data: {'nameC':nameC,'corpSubName':corpSubName},
				success: function(backData){
					//alert(backData.length);
					for(var i=0;i<backData.length;i++){
						result+="<tr>";
						result+="<td>"+backData[i].id+"</td>";

						var nameC2 = backData[i].nameC2;
						var namereg = /undefined/;
						if(namereg.test(nameC2)){
							nameC2='';
						}
						result+="<td>"+nameC2+"</td>";
						
						
						result+="<td class='tr'>"+backData[i].ctrledTag+"</td>";
						result+="<td class='tr'><a target=\"_blank\" href=\""+backData[i].url +"\">" + backData[i].url + "</a></td>";
						result+="<td class='tr'>"+ backData[i].leakType +"</td>";
						var path = backData[i].path;
						var reg = /undefined/;
						if(reg.test(path)){
							path='';
						}
						result+="<td class='tr'>"+path+"</td>";
						var statusVal = backData[i].status;
						var status;
						    
						if("未处理" ==  statusVal){
							status = "<span style=\"color:" + "#FF0000" + "\">" + backData[i].status + "</span>";
						}else if("已修复" ==  statusVal){
							status = "<span style=\"color:" + "#339933" + "\">" + backData[i].status + "</span>";
						}else if("网络不通" ==  statusVal){
							status = "<span style=\"color:" + "#ff9900" + "\">" + backData[i].status + "</span>";
						}else{
							status = "<span style=\"color:" + "#0066cc" + "\">" + backData[i].status + "</span>";
						}
						var department = '${currentUser.department}';
						if("技术开发部" == department){
							result+="<td class='tr'> " + status + " &nbsp;<a target='_blank' href='editWebLeakHandle.do?id="+ backData[i].id +"'>处理</a>&nbsp;<a target='_blank' href='editWebLeak.do?url="+backData[i].url +"'>编辑</a></td>";
						}else{
							result+="<td class='tr'> " + status + " &nbsp;<a target='_blank' href='editWebLeakHandle.do?id="+ backData[i].id +"'>处理</a>&nbsp;</td>";
						}
						
						result+="</tr>";
					}
					
				  }
			});//end $.ajax  
			return result
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
		
		<span class="warning">${msg }</span>
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
		</div>
	
		<div class="main mt10">
			<table  class="main-table" id="myTable">
			
      		
      			<tr role="head">
      			   <th></th> 
				    <th sort="true" >序号</th>    
				    <th sort="true" >名称</th>    
				    <th sort="true" class="tr" >主机数</th>      
					<th sort="true" class="tr" >漏洞数</th> 
					<th sort="true" class="tr" >未处理</th> 
					<th sort="true" class="tr" >已修复</th> 
					<th sort="true" class="tr" >网络不通</th> 
					<th sort="true" class="tr" >链接打不开</th>
					<th sort="true" class="tr" >未处理占比</th> 
					<!--  
					<th sort="true" class="tr" width="10%">被攻击次数</th> 
					<th sort="true" width="30%">最近一次发布时间</th>
					-->
				</tr>
			    <c:forEach var="model" varStatus="cur" items="${requestScope.webLeakCVList}" >
			    	
			    	<tr id="parentoneId${model.id}">
			    		<td>
			    			<img src="images/add.jpg" onclick="hostSumDetail(this);" queryCondition="${model.corpSubName};${model.id}" operated="no"  entityId="${model.id}"/>
			    		</td>
			    		<td>
			    		    <a href="#" target="_blank">
								${cur.index + 1}
							</a>
			    		</td>
			    		<td >
			    			<c:if test="${model.statusERR eq 0}">
			    				<font color="#339933">
			    					${model.corpSubName }
			    				</font>
			    			</c:if>
			    			<c:if test="${model.statusERR gt 0}">
			    				${model.corpSubName }
			    			</c:if>
			    			
			    		</td>
			    		<td class="tr">
			    			${model.countHost }
			    		</td>
			    		<td class="tr">
			    			${model.countURL }
			    		</td>
						<td class="tr">${model.statusERR }</td>
						<td class="tr">${model.statusRPR }</td>
						<td class="tr">${model.statusLNK }</td>
						<td class="tr">${model.statusCLS }</td>
						<td class="tr">${model.rate }</td>			    		
			    	</tr> 
			     
			    </c:forEach>
      		</table>		
		</div>
		<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
	</body>
	
</html>							