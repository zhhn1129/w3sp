// JavaScript Document
$(document).ready(function(e) {
    $('.main_table tr:even').addClass('even');
	$('.main_table tr:odd').addClass('odd');
	
	  //menu select
	$("h1[class='current']").removeAttr("class");
	$("li[class='current']").removeAttr("class");
	$("#" + mainMenu).addClass("current");
	$("#" + subMenu).addClass("current");
});
 
//将数字的整数部份三位三位的用“，”分开
function formatNumber(number){ 
	//如果不是数字,返回原形
	if(isNaN(number)) 
		return number;
	number = number + '';
	
	var intPart = number + '';
	var decimalPart;
	if (number.indexOf(".") > 0){
		number = (number/1).toFixed(2) + '';
		intPart = number.split(".")[0];
		decimalPart = number.split(".")[1];
	}
	var r = /(\d)(?=(\d\d\d)+(?!\d))/g; 
	intPart = intPart.replace(r,"$1,");
	return intPart + ((number.indexOf(".") > 0)?("." + decimalPart):"");
}

//函数说明：合并指定表格行的空数据项的相邻单元格
//参数说明：_tr 为需要进行合并单元格的表格行jquery对象
function _tr_colspan(_tr){
	var newTr = _tr.clone().empty();
	_tr.find("td").each(
		function(){
			if($(this).text() != ''){
				newTr.append($(this).clone());
			}else{
				if(newTr.find("td").length != 0){
					var lastTd = newTr.find("td").eq(newTr.find("td").length - 1);
					if(lastTd.text() == ''){
						lastTd.attr("colspan",lastTd.attr("colspan")/1 + 1);
					}else{
						newTr.append($(this).clone().attr("colspan",1));
					}
				}else{
					newTr.append($(this).clone().attr("colspan",1));
				}
			}
		}
	);
	_tr.after(newTr);
	_tr.remove();
}