package com.cernet.postJson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.cernet.postXML.DBUtils;
import com.cernet.postXML.SignatureUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ProvinceServer extends HttpServlet {
	//认证用户表名
	String tableName = "W3SP_WEBLEAK_INTERFACE";
    String seqName = "W3SP_WEBLEAK_INTERFACE_SEQ";
//  测试库
//  String tableData="W3SP_WEBLEAK_20160405";
//	正式库  
    String tableData="W3SP_WEBLEAK";
    String appId = "";
	String millis = "";
	String signatureClient = "";
	String xml = "";
	//分公司名
	String province="";
	
    SignatureUtil signatureUtil = new SignatureUtil();  
    Connection conn = DBUtils.insertData();
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		String returnS = returnClient("100","成功提交");
		String returnS ="";
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("请求方式:" + request.getMethod());
//		System.out.println("请求的协议种类：" + request.getProtocol());
//		System.out.println("请求资源路径:" + request.getRequestURI());
		System.out.println("请求的路径信息：" + request.getRequestURL());
//		System.out.println("请求的servlet路径:" + request.getServletPath());
//		System.out.println("您的IP是：" + request.getRemoteAddr());
		System.out.println("您的真实IP是：" + getIpAddr(request));
		
		PrintWriter out = response.getWriter();
		
		appId = request.getParameter("appId").trim();
		millis = request.getParameter("ts").trim();
		signatureClient = request.getParameter("sign").trim();
		province= request.getParameter("province");
		System.out.println("appId:"+appId);
		System.out.println("ts:"+millis);
		System.out.println("sign:"+signatureClient);
		System.out.println("province："+province);
		
		//校验
		
		CollegeServer c=new CollegeServer();
		c.checkIP(request, appId);
		String validResult=c.valid(appId,millis,signatureClient);
		if(validResult.equals("time error")){
			returnS = returnClient("105","请求时间无效");
			out.print(returnS);
			return;
		}else if(validResult.equals("noAPP_ID")){
				returnS = returnClient("103","AppId错误");
				out.print(returnS);
				return;
			}
		else if(validResult.equals("false")||validResult.equals("other false")){
			returnS = returnClient("102","签名无效");
			out.print(returnS);
			return;
			}
//							1		2			 3		4		5	 	6		7			8		 9			
		String find="select name_c,corp_sub_name,url,leak_type,score,status,create_time,check_method,id" +
			" from "+tableData+" where corp_sub_name = '"+province+"分公司' order by create_time desc";
		System.out.println(find);
		JSONObject webleak=new JSONObject();
		JSONArray webleaks=new JSONArray();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			pstmt=conn.prepareStatement(find);
			rs=pstmt.executeQuery();
			conn.commit();
			int i=0;
			while(rs.next()){
				/*i++;
				out.println(i);*/
				webleak.put("name_c", rs.getString(1));
				webleak.put("province", rs.getString(2));
				webleak.put("url", rs.getString(3));
				webleak.put("leak_type", rs.getString(4));
				webleak.put("score", rs.getString(5));
				//0:已修复；1:未处理;2： 链接打不开;3:网络不通
//				System.out.println(rs.getString(6));
				if(rs.getString(6).equals("0")){
					webleak.put("status", "已修复");
				}else if (rs.getString(6).equals("1")) {
					webleak.put("status", "未处理");
				}
				else if (rs.getString(6).equals("2")) {
					webleak.put("status", "链接打不开");
				}
				else if (rs.getString(6).equals("3")) {
					webleak.put("status", "网络不通");
				}
				webleak.put("event_date", rs.getDate(7).toString());
				if( rs.getString(8)!=null){
					webleak.put("check_method", rs.getString(8));
				}else {
					webleak.put("check_method", "null");
				}
				
				webleak.put("uid", rs.getString(9));
//				out.println(webleak+"<br/>");
				webleaks.add(webleak);
			}
			out.println(webleaks);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pstmt.close();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.print(returnS);
		
		out.close();
	}
	
	
	
	/*
	 * 获取真实ip
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}
	
	/*
	 * 返回客户端代�?
	 */
	public String returnClient(String code, String desc){
		String st = "{\"result\":" +
				"{\"code\":\"" + code + "\",\"desc\":\"" + desc + "\"}}";
		return st;
	}
}
