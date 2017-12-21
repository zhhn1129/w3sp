<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,user-scalable=no">
<link href="styles/layout.css" type="text/css" rel="stylesheet" />
<link href="styles/main.css" type="text/css" rel="stylesheet" />

<title></title>

<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script>
 <script src="scripts/echarts.js"></script>

</head>

<body>
<!--<button id="btn">123</button>-->
<nav class="newsTitle">
采集监控列表
</nav>
<article class="topNews">
<form action="./showLeakColMonitorInfo.do">
<cwui:table name="plist" id="LeakColMonitorInfo" requestURI="" sort="list" class="main-table">
	<cwui:column property="proName" sortable="true" title="监控程序" />
	<cwui:column property="totalNum" sortable="true" title="采集数据总数" />
	<cwui:column sortable="true" title="最近采集时间">
	<fmt:formatDate value="${LeakColMonitorInfo.lastTime}"	pattern="yyyy-MM-dd" />
	</cwui:column>
	<cwui:column property="lastTotalCount" sortable="true" title="最近采集总数" />
	<cwui:column property="status" sortable="true" title="运行状态" />
	<cwui:column property="runHours" sortable="true" title="连续运行时间"> ${LeakColMonitorInfo.runHours}H   </cwui:column>
</cwui:table>
</form>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
</article>
<div id="main" style="width:auto;height:400px;"></div>

<script type="text/javascript">

var CNVD=1;
var SH=1;
$(function(){
	//$("#btn").click(function() {
		//alert(1);
	var name="CNVD采集程序";
	
	$.ajax({
		type: "POST",
        url: "getCNVD.do",
        async:false,
        data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data);
            //alert(data.totalNum);
          CNVD=data.totalNum;
           // alert("CNVD="+CNVD);
            a();
        }, error: function(error) {
       alert("尚未发布任何信息！");
        }
	//});
	});
	$.ajax({
		type: "POST",
        url: "getSH.do",
        async:false,
        data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data.totalNum);
           SH=data.totalNum;
           // alert("SH="+SH);
            a();
        }, error: function(error) {
       alert("尚未发布任何信息！");
        }
	});
	function a(){
	var myChart = echarts.init(document.getElementById('main'));
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : '采集监控列表'
		},
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
		}
		},
		legend : {
			data : [ '采集数据总数', '最近采集数据总数' ]
		},
		xAxis : [ {
			//data: ["CNVD采集程序","上海交大","绿盟返回信息程序","反共黑客联盟采集程序","struts2监测"],
			data : [ "CNVD采集程序", "上海交大", "绿盟返回信息程序", "反共黑客联盟采集程序", "struts2监测" ],
			axisTick : {
				alignWithLabel : true
			}
		} ],
		yAxis : {},
		series : [ {
			name : '采集数据总数',
			type : 'bar',
			data : [ CNVD, SH, 1, 1, 99 ]
		}, {
			name : '最近采集数据总数',
			type : 'bar',
			data : [ 1, 162, 1, 1, 99 ]
		} ]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	}
});

</script>
</body>
<footer>
网站系统安全服务平台 © 2016 W3SP
</footer>
</html>
