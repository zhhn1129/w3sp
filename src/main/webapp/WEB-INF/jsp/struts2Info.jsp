<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,user-scalable=no">
<link href="styles/layout.css" type="text/css" rel="stylesheet" />
<link href="styles/main.css" type="text/css" rel="stylesheet" />

<title></title>

<script language="javascript" type="text/javascript" src="scripts/jquery-1.4.1.min.js"></script>
<script language="javascript" type="text/javascript" src="scripts/DatePicker/WdatePicker.js"></script>
<script src="scripts/echarts.js"></script>
</head>

<body>
<nav class="newsTitle">
漏洞处置监控
</nav>
<article class="topNews">
<form action="./showLeakStruts2.do">
<cwui:table name="plist" id="LeakStruts2" requestURI="" sort="list" class="main-table">
	<cwui:column sortable="true" title="漏洞处置程序名称" >struts2漏洞自动化检测程序</cwui:column>
	<cwui:column sortable="true" title="漏洞类型">
	struts2漏洞
	</cwui:column>
	<cwui:column property="status" sortable="true" title="状态" />
	<cwui:column property="totalNum" sortable="true" title="数量" />
	<cwui:column sortable="true" title="最近一次更新时间">
	<fmt:formatDate value="${LeakStruts2.lastTime}"	pattern="yyyy-MM-dd" />
	</cwui:column>
	<cwui:column property="runStatus" sortable="true" title="运行状态" />
	<cwui:column sortable="true" title="连续运行时间"> ${LeakStruts2.runHours}H   </cwui:column>
</cwui:table>
<%--<!--
	<cwui:column property="statusPro" sortable="true" title="未处理漏洞占比" />-->
	<!--<cwui:column property="totalNum" sortable="true" title="运行状态" />
	
-->--%>
</form>
<br></br>
<br></br>
<br></br>
<div id="main" style="width:auto;height:400px;"></div>

<script type="text/javascript">

var info1=1;
var info0=1;var info2=1;var info3=1;
$(function(){
	$.ajax({
		type: "POST",
        url: "getStruts2Info1.do",
        async:false,
      //  data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data);
            //alert(data.totalNum);
          info1=data.totalNum;
           // alert("CNVD="+CNVD);
            a();
        }, error: function(error) {
       alert("尚未发布任何信息！");
        }
	});
	
	$.ajax({
		type: "POST",
        url: "getStruts2Info0.do",
        async:false,
      //  data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data);
            //alert(data.totalNum);
          info0=data.totalNum;
           // alert("CNVD="+CNVD);
            a();
        }, error: function(error) {
       alert("尚未发布任何信息！");
        }
	});
	$.ajax({
		type: "POST",
        url: "getStruts2Info2.do",
        async:false,
      //  data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data);
            //alert(data.totalNum);
          info2=data.totalNum;
           // alert("CNVD="+CNVD);
            a();
        }, error: function(error) {
       alert("尚未发布任何信息！");
        }
	});
	$.ajax({
		type: "POST",
        url: "getStruts2Info3.do",
        async:false,
      //  data:"proName="+123,
        contentType: "text/html;charset=utf-8",
        dataType: "json",
        success: function(data) {
            //alert(data);
            //alert(data.totalNum);
          info3=data.totalNum;
           // alert("CNVD="+CNVD);
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
        text: '漏洞处置监控',
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
        data: ['未处理','已修复','链接打不开','网络不通']
    },
    series : [
        {
            name: '分发程序',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
{value:info1, name:'未处理'},
{value:info0, name:'已修复'},
{value:info2, name:'链接打不开'},
{value:info3, name:'网络不通'},
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
</article>

</body>
<footer>
网站系统安全服务平台 © 2016 W3SP
</footer>
</html>