package com.cernet.postXML;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class server extends HttpServlet {
	String tableName = "W3SP_WEBLEAK_INTERFACE";
    String seqName = "W3SP_WEBLEAK_INTERFACE_SEQ";
    String appId = "";
	String millis = "";
	String signatureClient = "";
	String xml = "";
	
    SignatureUtil signatureUtil = new SignatureUtil();  
    Connection conn = DBUtils.insertData();
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String returnS = returnClient("100","成功提交");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("请求方式：" + request.getMethod());
		System.out.println("请求的协议种类：" + request.getProtocol());
		System.out.println("请求资源路径：" + request.getRequestURI());
		System.out.println("请求的路径信息：" + request.getRequestURL());
		System.out.println("请求的servlet路径：" + request.getServletPath());
		System.out.println("您的IP是：" + request.getRemoteAddr());
		System.out.println("您的真实IP是：" + getIpAddr(request));
		
		PrintWriter out = response.getWriter();
		
		//获取request长度
		int totalbytes = request.getContentLength(); 
		System.out.println("request长度为：" + totalbytes);
		if(totalbytes>10500000){
			returnS = returnClient("101","XML数据大小超过10M");
			out.print(returnS);
			return;
		}else if(totalbytes==-1){
			returnS = returnClient("102","签名无效");
			out.print(returnS);
			return;
		}
		
		appId = request.getParameter("appId");
		millis = request.getParameter("ts");
		signatureClient = request.getParameter("sign");
		System.out.println("appId:"+appId);
		System.out.println("ts:"+millis);
		System.out.println("sign:"+signatureClient);
		
		//校验
		if(valid(appId,millis,signatureClient).equals("noAPP_ID")){
			returnS = returnClient("103","AppId错误");
			out.print(returnS);
			return;
		}else if(valid(appId,millis,signatureClient).equals("false")){
			returnS = returnClient("102","签名无效");
			out.print(returnS);
			return;
		}else if(valid(appId,millis,signatureClient).equals("other false")){
			returnS = returnClient("102","签名无效");
			out.print(returnS);
			return;
		}
		if(!checkIP(request,appId)){
			returnS = returnClient("104","IP地址无效");
			out.print(returnS);
			return;
		}
		
		String insertSql = "INSERT INTO " + tableName + " (id,appId," + 
	    "url,openly,unit_name,leak_name,leak_type,leak_rank,event_time," +
	    "prod_name,soft_name,leak_detail,os_name,check_method,solution,note) " +
	    "VALUES (" + seqName + ".NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		//XML拿取
		byte[] dataOrigin = new byte[totalbytes]; //建立xml长度的数组
		DataInputStream in = new DataInputStream(request.getInputStream());
//		in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中
		in.read(dataOrigin);
		in.close(); // 关闭数据流
		String xml = new String(dataOrigin); // 从字节数组中得到表示实体的字符串
		
		int turns = 0;
		
		ArrayList<String> arrayList;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			conn.setAutoCommit(false);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			Reader rr=new StringReader(xml); 
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(rr));
			NodeList nl = doc.getElementsByTagName("leak");
			String[]field = {"url","openly","unit_name","leak_name","leak_type","leak_rank",
					"event_time","prod_name","soft_name","leak_detail","os_name","check_method",
					"solution","note"};
			pstmt = conn.prepareStatement(insertSql);
			for (int i = 0; i < nl.getLength(); i++) {
				arrayList = new ArrayList<String>();
				for(int k=0;k<field.length;k++){
					try{
						arrayList.add(doc.getElementsByTagName(field[k]).item(i).getFirstChild().getNodeValue());
					}catch(NullPointerException e){
						arrayList.add("");
					}
				}
				
				
//				pstmt.setString(1, seqName+".NEXTVAL");
				pstmt.setString(1, appId);
				pstmt.setString(2, arrayList.get(0)); //url
				pstmt.setString(3, arrayList.get(1)); 
				pstmt.setString(4, arrayList.get(2)); //unit_name
				pstmt.setString(5, arrayList.get(3));
				pstmt.setString(6, arrayList.get(4)); //leak_type
				pstmt.setString(7, arrayList.get(5));
				pstmt.setDate(8, new Date(sdf.parse(arrayList.get(6)).getTime())); //event_time
				pstmt.setString(9, arrayList.get(7));
				pstmt.setString(10, arrayList.get(8));
				pstmt.setString(11, arrayList.get(9));
				pstmt.setString(12, arrayList.get(10));
				pstmt.setString(13, arrayList.get(11));
				pstmt.setString(14, arrayList.get(12));
				pstmt.setString(15, arrayList.get(13));
//				for(int n=0;n<13;n++){
//					System.out.println(arrayList.get(n));
//				}
				
				pstmt.addBatch();
//				System.out.println("存入记录ID为:"+arrayList.get(0));
			}
		} catch (SAXException e) {
			e.printStackTrace();
			turns = 1;
			returnS = returnClient("105","XML格式错误");
			try {
				pstmt.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			out.print(returnS);
			out.close();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			turns = 1;
			returnS = returnClient("200","未知错误");
			try {
				pstmt.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			out.print(returnS);
			out.close();
			return;
		}
			
		try {
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
				System.out.println("执行了回滚");
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			returnS = returnClient("106","必填项有空值");
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
		String sqlSecret = "select APP_SECRET from W3SP_WEBLEAK_WHITE where APP_ID='" + appID + "'";
		try {
			rs = conn.createStatement().executeQuery(sqlSecret);
			if(rs.next()){
				appSecret = rs.getString("APP_SECRET");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return "other false";
		}
		if(appSecret.equals("")){
			result = "noAPP_ID";
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
	
	//校验IP地址
	public boolean checkIP(HttpServletRequest request, String appID){
		String sqlCheckIP = "select IP from W3SP_WEBLEAK_WHITE where APP_ID='" + appID + "'";
		String IP = "";
		try {
			rs = conn.createStatement().executeQuery(sqlCheckIP);
			while(rs.next()){
				IP = rs.getString("IP");
				System.out.println("sql的IP："+IP);
			}
			if(IP==null||IP.equals("")){
				return true;
			}else{
				if(StringUtils.equals(getIpAddr(request),IP)){
					return true;
				}else{
					return false;
				}
			}
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
	 * 返回客户端代码
	 */
	public String returnClient(String code, String desc){
		String st = "<result><code>" + code + "</code><desc>" + desc + "</desc></result>";
		return st;
	}
}
