package com.cernet.postJson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.cernet.postXML.DBUtils;
import com.cernet.postXML.SignatureUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CollegeServer extends HttpServlet {
	//认证用户表名
	String tableName = "W3SP_WEBLEAK_INTERFACE";
    String seqName = "W3SP_WEBLEAK_INTERFACE_SEQ";
//    测试库
//    String tableData="W3SP_WEBLEAK_20160405";
//  正式库  
    String tableData="W3SP_WEBLEAK";
    
    String appId = "";
	String millis = "";
	String signatureClient = "";
	String xml = "";
	String name_c="";
	
    SignatureUtil signatureUtil = new SignatureUtil();  
    Connection conn = DBUtils.insertData();
//    DBUtil du =new DBUtil();
    
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		String returnS = returnClient("100","成功提交");
		String returnS ="";
//		Connection conn=du.getConn();
		
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
		name_c= request.getParameter("name_c").trim();
//		name_c=URLDecoder.decode(request.getParameter("name_c"),"utf-8");
//		System.out.println("new name_c："+name_c);
		System.out.println("appId:"+appId);
		System.out.println("ts:"+millis);
		System.out.println("sign:"+signatureClient);
		System.out.println("name_c："+name_c);
		
		if(!checkIP(request,appId)){
			returnS = returnClient("104","IP地址无效");
			out.print(returnS);
			return;
		}
		//校验
		String validResult=valid(appId,millis,signatureClient);
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
		}/*else if(valid(appId,millis,signatureClient).equals("other false")){
			returnS = returnClient("102","签名无效");
			out.print(returnS);
			return;
		}*/
//							1		2			 3		4		5	 	6		7			8		 9		
		String find="select name_c,corp_sub_name,url,leak_type,score,status,create_time,check_method,id" +
			" from "+tableData+" where NAME_C = '"+name_c+"' order by create_time desc";
//		and create_time>TO_DATE('06/03/2015 00:00:00', 'MM/DD/YYYY HH24:MI:SS')
		
		System.out.println(find);
		JSONObject webleak=new JSONObject();
		JSONArray webleaks=new JSONArray();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			pstmt=conn.prepareStatement(find);
			rs=pstmt.executeQuery();
			conn.commit();
//			int i=0;
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
	
	//校验MD5
	public String valid(String appID, String millis, String signatureClient){
		String result = "";
		String appSecret = "";
		String signatureServer = "";
		long mill=Long.parseLong(millis);
		long time=System.currentTimeMillis();
		long maxtime=time+600000l;
		long mintime=time-600000l;
		
//		System.out.println("server:>>>>>>>>"+appID);
		String sqlSecret = "select APP_SECRET from W3SP_WEBLEAK_WHITE where APP_ID='" + appID + "'";
		try {
			rs = conn.createStatement().executeQuery(sqlSecret);
			if(rs.next()){
				appSecret = rs.getString("APP_SECRET");
//				System.out.println("server:>>>>>>>>"+appSecret);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			
			System.out.println("other false");
			return "other false";
		}
		if(appSecret.equals("")){
			result = "noAPP_ID";
		}else if(mill>maxtime||mill<mintime){
//			System.out.println("189:"+time);
			return "time error";
		}else{
			signatureServer = signatureUtil.digest(appID + millis + appSecret, "MD5");
			if(StringUtils.equals(signatureClient, signatureServer)){
				result = "true";
			}else{
				result = "false";
			}
		}
		
		return result;
	}
	public static void main(String[] args) {
		/*String sqlCheckIP = "select * from W3SP_WEBLEAK_WHITE ";
		ResultSet rs=null;
		Connection conn=DBUtils.insertData();
		try {
			rs = conn.createStatement().executeQuery(sqlCheckIP);
			while (rs.next()) {
				System.out.println(rs.getString("APP_SECRET"));
				System.out.println(rs.getString("TIME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
		System.out.println(sdf.format(new Date()));*/
		System.out.println(System.currentTimeMillis());
	}
	//校验IP地址
	public boolean checkIP(HttpServletRequest request, String appID){
		String sqlCheckIP = "select IP from W3SP_WEBLEAK_WHITE where APP_ID='" + appID + "'";
		String IPs[] =new String[10];
		String IP = "";
		try {
			rs = conn.createStatement().executeQuery(sqlCheckIP);
			while(rs.next()){
				IPs = rs.getString("IP").split(",");
//				System.out.println("sql的IP:"+IP);
			}
			if(IPs==null||IPs.equals("")){
				return false;
			}else{
				for (int i = 0; i < IPs.length;i++) {
					IP=IPs[i];
					if(StringUtils.equals(getIpAddr(request),IP)){
						return true;
					}
				}
			}
			return false;
		} catch (SQLException e2) {
			e2.printStackTrace();
			
			return false;
		} catch (NullPointerException e3) {
			e3.printStackTrace();
			return true;
		}
		
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
