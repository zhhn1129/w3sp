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
				var newTr = "<tr style=\"display:none;\" id=\"tr_"+id+"\"><td colspan=\"11\"><table class=\"second-table\"  style=\"display:''\" id=\"one_"+id+"\" operated=\"no\"></table></td></tr>";
				$("#parentoneId" +id).after(newTr);
				var detailTable = document.getElementById("one_" +id);
				 
				$(field).attr("src","images/minus.jpg");//将加号换成减号,显示详细信息
				var operated = $(detailTable).attr("operated");
				
				if(operated=="no"){
					
					var tableTitle="<tr role=\"head\" ><th ></th><th sort=\"true\" >序号</th><th  sort=\"true\">学校名称</th><th  sort=\"true\">类型</th><th sort=\"true\" class='tr' >被感染IP数</th><th sort=\"true\" class=\"tr\" >被攻击次数</th><th sort=\"true\" class=\"tr\" >最近一次攻击时间</th><th sort=\"true\" class=\"tr\" >未处理</th></tr>";
					//ajax得到详细数据
					var tableContext=invokeajax($(field).attr("queryCondition"),id);
					detailTable.style.display="";
					$(detailTable).empty();
					
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
				  url: "botnetList2.do",
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
							result+="<td class='tr'>"+backData[i].countIp+"</td>";
							result+="<td class='tr'>"+backData[i].sumCount+"</td>";
							result+="<td class='tr'>"+backData[i].lastTime+"</td>";
							result+="<td class='tr'>"+backData[i].statusERR+"</td>";
							result+="</tr>";
						}
						var department = '${currentUser.department}';
						if("技术开发部" != department){
							result+="<tr><td>合计</td><td colspan='3'></td><td class='tr'>" + '${sumIp}' + "</td>"+
							"<td class='tr'>" + '${sum}' +"</td>" + 
							"<td></td>" + 
							"<td class='tr'>" + '${sumErrWB}' +"</td>" + 
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
				var newTr = "<tr style=\"display:none;\" id=\"twotr_"+id+"\"><td colspan=\"9\"><table class=\"third-table\"  style=\"display:''\" id=\"three_"+id+"\" operated=\"no\"></table></td></tr>";
				$("#parenttwoId" +id).after(newTr);
				var threedetailTable = document.getElementById("three_" +id);
				
				$(field).attr("src","images/minus.jpg");//将加号换成减号,显示详细信息
				var operated = $(threedetailTable).attr("operated");
				if(operated=="no"){
					//var tableTitle="<tr role=\"head\"><th sort=\"true\" width='5%'>序号</th><th sort=\"true\" >被控地址</th><th sort=\"true\" class='tr' >控制地址</th><th sort=\"true\" class='tr' >控制单位</th><th sort=\"true\" class='tr' >开始时间</th><th class='tr' sort=\"true\" >结束时间</th><th class='tr' sort=\"true\" >木马类型</th><th class='tr' sort=\"true\" >控制端口</th><th class='tr' sort=\"true\" >攻击次数</th><th class='tr' sort=\"true\">操作</th></tr>";
					var tableTitle="<tr role=\"head\"><th sort=\"true\" width='5%'>序号</th><th sort=\"true\" >被控地址</th><th sort=\"true\" class='tr' >控制地址</th><th sort=\"true\" class='tr' >控制单位</th><th sort=\"true\" class='tr' >开始时间</th><th class='tr' sort=\"true\" >结束时间</th><th class='tr' sort=\"true\" >木马类型</th><th class='tr' sort=\"true\" >控制端口</th><th class='tr' sort=\"true\" >攻击次数</th></tr>"; //去掉操作
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
				  url: "botnetList3.do",
				  dataType: 'json',
				  data: {'nameC':nameC,'corpSubName':corpSubName},
				success: function(backData){
					//alert(backData.length);
					for(var i=0;i<backData.length;i++){
						result+="<tr>";
						result+="<td>"+backData[i].id+"</td>";

						var nameC2 = backData[i].ctrledIp;
						//var namereg = /undefined/;
						//if(namereg.test(nameC2)){
						//	nameC2='';
						//}
						result+="<td>"+nameC2+"</td>";
						result+="<td class='tr'>"+backData[i].ctrlIp+"</td>";
						result+="<td class='tr'>"+backData[i].ctrlNameC+"</td>";
						result+="<td class='tr'>"+backData[i].startTime+"</td>";
						result+="<td class='tr'>"+backData[i].endTime+"</td>";
						result+="<td class='tr'>"+backData[i].event+"</td>";
						result+="<td class='tr'>"+backData[i].ctrlPort+"</td>";
						result+="<td class='tr'>"+backData[i].sumCount+"</td>";
						
					//	var statusVal = backData[i].status;
					//	var status;
						    
					//	if("未处理" ==  statusVal){
					//		status = "<span style=\"color:" + "#FF0000" + "\">" + backData[i].status + "</span>";
					//	}else if("已修复" ==  statusVal){
					//		status = "<span style=\"color:" + "#339933" + "\">" + backData[i].status + "</span>";
					//	}else if("网络不通" ==  statusVal){
					//		status = "<span style=\"color:" + "#ff9900" + "\">" + backData[i].status + "</span>";
					//	}else{
					//		status = "<span style=\"color:" + "#0066cc" + "\">" + backData[i].status + "</span>";
					//	}
					//	var department = '${currentUser.department}';
					//	if("技术开发部" == department){
					//		result+="<td class='tr'>" + status + " &nbsp;<a target='_blank' href='editWebshellHandle.do?id="+ backData[i].id +"'>处理</a>&nbsp;<a target='_blank' href='editWebshell.do?url="+backData[i].url +"'>编辑</a></td>";
					//	}else{
					//		result+="<td class='tr'>" + status + " &nbsp;<a target='_blank' href='editWebshellHandle.do?id="+ backData[i].id +"'>处理</a></td>";
					//	}
						
						
						//result+="<td class='tr'>" + status + " &nbsp;<a target='_blank' href='editWebshellHandle.do?id="+ backData[i].id +"'>处理</a>&nbsp;<a target='_blank' href='editWebshell.do?url="+backData[i].url +"'>编辑</a></td>";
						result+="</tr>";
					}
					
				  }
			});//end $.ajax  
			return result
		}
	
		</script>
	</head>
	<body>
		<%@include file="/WEB-INF/jsp/common/header.jsp"%>
		
		<span class="warning">${msg }</span>
		<!--  
		<div class="notice">
		
		</div>
		-->
		</div>
	
		<div class="main mt10">
			<table  class="main-table" id="myTable">
			
      		
      			<tr role="head">
      			   <th></th> 
				    <th sort="true" >序号</th>    
				    <th sort="true" >名称</th>    
					<th sort="true" class="tr" >被感染IP数</th> 
					<th sort="true" class="tr" >被攻击次数</th> 
					<th sort="true" class="tr" >最后一次攻击时间</th>
					<th sort="true" class="tr" >未处理</th> 
					<th sort="true" class="tr" >已修复</th> 
					<th sort="true" class="tr" >网络不通</th> 
					<th sort="true" class="tr" >链接打不开</th> 
				
				</tr>
			    <c:forEach var="model" varStatus="cur" items="${requestScope.botnetList}" >
			    	
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
			    			${model.countIp }
			    		
			    		<td class="tr">
			    			${model.sumCount}
			    		</td>
						<td class="tr">
							<fmt:formatDate value="${model.lastTime }"
											pattern="yyyy-MM-dd" />
							
						</td>
						<td class="tr">${model.statusERR }</td>
						<td class="tr">${model.statusRPR }</td>
						<td class="tr">${model.statusLNK }</td>
						<td class="tr">${model.statusCLS }</td>
							    		
			    	</tr> 
			     
			    </c:forEach>
      		</table>		
		</div>
		<%@include file="/WEB-INF/jsp/common/footer.jsp"%>
	</body>
	
</html>							