package com.cernet.util;
/**
 * 系统常量
 * @author maqb
 *
 */
public  class SysConstant {
	
	/**
	 * DNS统计分析-->域名解析Top100排名
	 * 网内/网外IP解析比例 表格颜色
	 * inOutColorStatus=0 若网内<网外
	 * inOutColorStatus=1 若网内=网外
	 * inOutColorStatus=2 若网内>网外
	 */
	public enum inOutColorStatus{
		  inItOut("1"),//inOutColorStatus=0 若网内<网外
		  inEqOut("2"),//inOutColorStatus=1 若网内=网外
		  inGtOut("3");//inOutColorStatus=2 若网内>网外
		  private final String value;
		  private inOutColorStatus(String value) {
		   this.value = value;
		  }
		  public String toString() {
		   return this.value;
		  }
		 };

	
	public enum transferObjField{
		icp("icp"),//icp
		interconnected("interconnected"),//interconnected 互联口即HttpSum
		dnsAcc("dnsAcc");//dnsAcc dns加速配置
		private final String value;
		private transferObjField(String value) {
		this.value = value;
		}
		  public String toString() {
		   return this.value;
		  }
	};
	
	/**
	 * 对应FlowDirectRate.java 中的业务字段record的处理使用
	 * @author maqb
	 *
	 */
	public enum recordFlag{
		no("0"),//没有记录
		one("1"),//一条记录
		more("more");//多条记录
		private final String value;
		private recordFlag(String value) {
		this.value = value;
		}
		  public String toString() {
		   return this.value;
		  }
	};
}
