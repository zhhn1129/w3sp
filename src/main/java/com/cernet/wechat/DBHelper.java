package com.cernet.wechat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.google.gson.Gson;

public class DBHelper {

	//gson工具 
	private static Gson gson = new Gson();
	
    //String jdbc = "jdbc:mysql://localhost:3306/test?";
    String driver = "oracle.jdbc.driver.OracleDriver";
//    String url = "jdbc:oracle:thin:@115.25.87.179:1521:oracle10g"; //测试环境
    String url = "jdbc:oracle:thin:@202.205.11.54:1521:ora10g";//正式url
    String username = "w3sp";
//    String password = "w3sp";    //测试密码
    String password = "w3spadmin";//正式密码
    Connection conn = null; // 数据库连接
    Statement stmt = null; // 数据库表达式
	

    /**
     * 关闭数据库连接
     * @param conn
     * @param stmt
     * @param rs
     */
    public void close(Connection conn,Statement stmt, ResultSet rs) {
    	try {
    		rs.close();
    		stmt.close();
    		conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
    
    /**
     * 将获取到的用户基本信息更新到数据表中
     * @param userInfoList
     */
    public boolean updateWXChatUserTable (String userInfoList) {
        // Convert JSON string back to Map.
        JSONObject jsonObject = JSONObject.fromObject(userInfoList);
        //提取userInfoList中的用户数组
        List<Map> userlist =  jsonObject.getJSONArray("user_info_list");
        //获取当前时间并转换类型
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		String nowDateTime = dateFormat.format(now); 
		
        try {
		    /*加载驱动*/
		    Class.forName(driver);
	        /*连接到数据库*/
            conn = DriverManager.getConnection(url, username, password);
            /*获取表达式*/
            stmt = (Statement) conn.createStatement();

            ResultSet rs = null; // 结果集
            //先更新所有用户的SUBSCRIBE字段为0
            String updateStr = "update WECHAT_USER set SUBSCRIBE='0'," + 
            		"UPDATE_TIME=to_date('"+nowDateTime+"','yyyy-MM-dd HH24:mi:ss')";
            ((java.sql.Statement) stmt).executeUpdate(updateStr);
            //再逐条插入用户数据
            for (Map<String, String> user : userlist) {
            	String openId = user.get("openid");
            	//判断库里是否有该用户
            	String select_sql = "select count(*) from WECHAT_USER where OPENID='"+openId+"'";
            	rs = ((java.sql.Statement) stmt).executeQuery(select_sql);
            	rs.next();
            	//System.out.println(rs.getString(1));
            	if (rs.getString(1).equals("0")) {
                    /*  插入数据	*/
                	String str_sql = "insert into WECHAT_USER"
                			+ " (ID,SUBSCRIBE,OPENID,NICKNAME,SEX,LANGUAGE,CITY,PROVINCE,"
                			+ "COUNTRY,HEADIMGURL,SUBSCRIBE_TIME,UNIONID,REMARK,GROUPID,CREATE_TIME,UPDATE_TIME)"
                			+ " values(WECHAT_USER_SEQ.nextval,%s,'%s','%s',%s,'%s',"
                			+ "'%s','%s','%s','%s','%s',%s,'%s',%s,to_date('"+nowDateTime+"','yyyy-MM-dd HH24:mi:ss'),'')";
                	String sql=String.format(str_sql,user.get("subscribe"),user.get("openid"),user.get("nickname").replace("'", "''")
                			,user.get("sex"),user.get("language"),user.get("city").replace("'", "''"),user.get("province")
                			,user.get("country"),user.get("headimgurl"),user.get("subscribe_time"),user.get("unionid")
                			,user.get("remark"),user.get("groupid"));
                	//System.out.println(sql);
	            	try {
	            		((java.sql.Statement) stmt).executeUpdate(sql);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else {
	                /*  更新数据	*/
	            	String str_sql = "update WECHAT_USER set " +
	            			"SUBSCRIBE=%s,NICKNAME='%s',SEX='%s',LANGUAGE='%s',CITY='%s',PROVINCE='%s'," +
	            			"COUNTRY='%s',HEADIMGURL='%s',SUBSCRIBE_TIME='%s',UNIONID='%s',REMARK='%s'," + 
	            			"GROUPID='%s',UPDATE_TIME=to_date('"+nowDateTime+"','yyyy-MM-dd HH24:mi:ss')" + 
	            			" where OPENID='"+openId+"'";
	            	String sql=String.format(str_sql,user.get("subscribe"),user.get("nickname").replace("'", "''")
	            			,user.get("sex"),user.get("language"),user.get("city").replace("'", "''"),user.get("province")
	            			,user.get("country"),user.get("headimgurl"),user.get("subscribe_time"),user.get("unionid")
	            			,user.get("remark"),user.get("groupid"));
	            	//System.out.println(sql);
	            	try {
	            		((java.sql.Statement) stmt).executeUpdate(sql);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
            }
            rs = ((java.sql.Statement) stmt).executeQuery("select * from WECHAT_USER");
            /* 查看里面的数据*/
            while (rs.next()) {
                System.out.println("[行号：" + rs.getRow() + "]\t"
                	      + rs.getString(1) + "\t" + rs.getString(2) + "\t"
                	      + rs.getString(3) + "\t" + rs.getString(4) + "\t"
                          + rs.getString(5) + "\t" + rs.getString(6) + "\t"
                          + rs.getString(7) + "\t" + rs.getString(8) + "\t"
                          + rs.getString(9) + "\t" + rs.getString(10) + "\t"
                          + rs.getString(11) + "\t" + rs.getString(12) + "\t"
                          + rs.getString(13) + "\t" + rs.getString(14) + "\t"
                          + rs.getString(15) + "\t" + rs.getString(16) + "\t"
                	      );
            }
            //关闭数据库连接
            close(conn,stmt,rs);
            return true;
		} catch (Exception e) {
			// TODO: handle exception
            e.printStackTrace();
            return false;
		}
	}

    /**
     * 获取和指定remark相似的用户openid（模糊查询）
     * @param remark
     * 		用户的备注信息
     * @return
     * 		符合查询结果的所有用的openid数组
     */
    public List<String> selectOpenidsFromWXUserTable(String remark) {
    	List<String> openids = new ArrayList();
        try {
		    /*加载驱动*/
		    Class.forName(driver);
	        /*连接到数据库*/
            conn = DriverManager.getConnection(url, username, password);
            /*获取表达式*/
            stmt = (Statement) conn.createStatement();

            ResultSet rs = null; // 结果集
            
            String sql = "select OPENID from WECHAT_USER where REMARK like '%"+remark+"%'";
            rs = ((java.sql.Statement) stmt).executeQuery(sql);
            /* 组合openids的数据*/
            while (rs.next()) {
            	openids.add(rs.getString(1));
            }
            //关闭数据库连接
            close(conn,stmt,rs);
		} catch (Exception e) {
			// TODO: handle exception
            e.printStackTrace();
		}
    	
		return openids;
	}
}
