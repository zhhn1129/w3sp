/**
 * 
 */
package com.cernet.postJson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author zhaohn
 *
 */
public class DBUtil {
	public static final String DRIVER = "oracle.jdbc.OracleDriver";
	public static final String USERNAME = "W3SP";
	public static final String URL = "jdbc:oracle:thin:@202.205.11.54:1521:ora10g";//正式url
	public static final String PASSWORD = "w3spadmin";
	
	public Connection conn=null;
	public Connection getConn(){
		try {
			Class.forName(DRIVER);
			conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			String appSecret = "";
			String time="";
			ResultSet rs=new DBUtil().getConn().createStatement().executeQuery("select APP_SECRET,time from W3SP_WEBLEAK_WHITE where APP_ID='003106'");
			if(rs.next()){
				appSecret = rs.getString("APP_SECRET");
				time=rs.getString(2);
				System.out.println(">>>>>>>>>>>>>>>>>>"+time);
				System.out.println("server:>>>>>>>>"+appSecret);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
