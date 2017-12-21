package com.cernet.postXML;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtils {
	public static Connection insertData(){
		Properties p=new Properties();
		InputStream in=DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			p.load(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String driver = p.getProperty("jdbc.driverClassName");
//		System.out.println(driver);
		String username = p.getProperty("jdbc.username");
//		String url = "jdbc:oracle:thin:@115.25.87.179:1521:oracle10g"; //测试环境
//		String password = "w3sp";    //测试密码
		String url = p.getProperty("jdbc.url");//正式url
		String password = p.getProperty("jdbc.password");//正式密码
		
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(url);
		ds.setDriverClassName(driver);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(10);
		ds.setMaxIdle(20);
		ds.setMinIdle(5);
		ds.setMaxActive(50);
		ds.setMaxWait(1000);
		ds.setTestOnReturn(true);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("select * from W3SP_WEBLEAK_WHITE");
		Connection conn = null;
		try{
			Class.forName(driver);
			conn = ds.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		return conn;
	}
public static void main(String[] args) {
	insertData();
}
}
