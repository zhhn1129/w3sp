/**
 * 
 */
package com.cernet.spider.exporter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import com.cernet.spider.Article;
import com.cernet.spider.Spider;
import com.mysql.jdbc.StringUtils;

/**
 * Created by Eclipse.
 * 
 * @author Gump
 * @date 2013-11-1
 */

public class DataBaseExporter implements Exporter {
	private DataSource dataSource;
	private static Connection conn = null;
	
	
	@Override
	public void export(Article article) {
		if (StringUtils.isNullOrEmpty(article.getTitle())
				|| StringUtils.isNullOrEmpty(article.getContent())
				|| StringUtils.isNullOrEmpty(article.getFrom())) {// 文章不完整，去掉
			return;
		}
		
		PreparedStatement pStmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = dataSource.getConnection();
			
			//如何 同一个栏目下已经有 此文章 则不在存入  start 
			String sqlUnique = "select * from W3SP.ARTICLE where colid = " + article.getColId() + " and src_url = '" + article.getSrcURL()+"'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlUnique);
			while(rs.next()){
				return;
			}//如何 同一个栏目下已经有 此文章 则不在存入   end 
			
			String sql = "insert into ARTICLE(id,colid,title,content,src_url,SRC_NAME,"
					+ " creator, create_time, pub_time) "
					+ "values(W3SP_ARTICLE_SEQ.nextval,?,?,?,?,?,?,?,?)";

			pStmt = conn.prepareStatement(sql);
			
			//InputStream content = new ByteArrayInputStream(article.getContent().getBytes());

			pStmt.setInt(1, article.getColId());
			pStmt.setString(2, article.getTitle());
			pStmt.setString(3, article.getContent());
			pStmt.setString(4, article.getSrcURL());
			pStmt.setString(5, article.getFrom());
			pStmt.setString(6, "采集器");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date pickTime = sdf.parse(article.getPickTime());
			pStmt.setTimestamp(7, new Timestamp(pickTime.getTime()));
			

			if(!"".equals(article.getPubTime())&&(article.getPubTime())!=null){
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				Date pubDate = sdf2.parse(article.getPubTime());

				pStmt.setTimestamp(8, new Timestamp((pubDate.getTime())));
			}else{
				pStmt.setTimestamp(8, null);;
			}

			pStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {

			try {
				if(conn != null){
					conn.close();
				}
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(pStmt != null){
					pStmt.close();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private Article dealWithSnapshot(Article art) {
		String artContent = art.getContent();
		int length = artContent.length();
		String snapshot = "";
		if (length >= 1000) {
			snapshot = artContent.substring(0, 999);
		} else if (length >= 500) {
			snapshot = artContent.substring(0, 499);
		} else if (length >= 400) {
			snapshot = artContent.substring(0, 399);
		} else if (length >= 300) {
			snapshot = artContent.substring(0, 299);
		} else if (length >= 200) {
			snapshot = artContent.substring(0, 199);
		} else if (length >= 100) {
			snapshot = artContent.substring(0, 99);
		} else if (length >= 50) {
			snapshot = artContent.substring(0, 49);
		}
		snapshot = snapshot.replaceAll("<[^>]*>", "");
		if (snapshot.length() > 500) {
			snapshot = snapshot.substring(0, 499);
		}
		art.setSnapshot(snapshot);
		return art;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	//用于本地测试
	public static void main(String[] args) {
		Spider spider = new Spider();
		try {
			System.out.println("===========" + new Date()
					+ ":spider start ==============");
			spider.run();
			System.out.println("===========" + new Date()
					+ ":spider end ==============");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
