<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,user-scalable=no">
		<link href="styles/layout.css" type="text/css" rel="stylesheet" />
		<link href="styles/main.css" type="text/css" rel="stylesheet" />
		
	<title>
	</title>
		
		<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
		<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script>
 <script src="scripts/echarts.js"></script>
</head>	
		
	<body>
	<nav class="newsTitle">
	分发监控列表
	</nav>
	<article class="topNews">
	<form action="./showLeakPushMonitorInfo.do">
	<cwui:table name="plist" id="LeakPushMonitorInfo" requestURI="" sort="list"
					class="main-table" >
			    <cwui:column property="proName" sortable="true" title="分发程序" />				
				<cwui:column property="totalNum" sortable="true" title="分发总数" /> 
			    <cwui:column  sortable="true" title="最近分发时间" >
			     <fmt:formatDate value="${LeakPushMonitorInfo.lastTime}" pattern="yyyy-MM-dd"/>
			    </cwui:column>
			    <cwui:column property="lastTotalCount" sortable="true" title="最近分发总数" />
			    <cwui:column property="status" sortable="true" title="运行状态" />
			    <cwui:column  sortable="true" title="连续运行时间">
			    ${LeakPushMonitorInfo.runHours}H
			    </cwui:column>
				</cwui:table>
	</form>
	</article>
	
	
	<div id="main" style="width:auto;height:400px;"></div>

<script type="text/javascript">

var CNVD=1;
var SH=1;
$(function(){
	$.ajax({
		type: "POST",
        url: "getPushCNVD.do",
        async:false,
      //  data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data);
            //alert(data.totalNum);
          CNVD=data.totalNum;
            alert("CNVD="+CNVD);
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
        text: '分发监控列表',
        //subtext: '纯属虚构',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['CNVD采集程序','6个月以内高危漏洞推送','每周推送全部漏洞定时任务','特定漏洞类型手动推送','6个月以上高危漏洞推送','自定义消息推送']
    },
    series : [
        {
            name: '分发程序',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
{value:CNVD, name:'CNVD采集程序'},
{value:10, name:'6个月以内高危漏洞推送'},
{value:12, name:'每周推送全部漏洞定时任务'},
{value:2, name:'特定漏洞类型手动推送'},
{value:8, name:'6个月以上高危漏洞推送'},
{value:3,name:'自定义消息推送'}

            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
	myChart.setOption(option);
	}
});

</script>
	
	
	</body>
	<footer> 网站系统安全服务平台 © 2016 W3SP</footer>
</html>
