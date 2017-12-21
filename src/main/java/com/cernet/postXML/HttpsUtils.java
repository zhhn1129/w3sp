package com.cernet.postXML;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import javax.net.ssl.*;

public class HttpsUtils {
	//private String url = "https://w3sp.cernet.com/w3sp/sendWebLeakInfo";

	//服务器密码：jskf49 name:root
	private String url = "https://115.25.87.150/w3sp/sendWebLeakInfo";

	//	private String url = "https://w3sp.cernet.com/w3sp/login";

	private myX509TrustManager xtm = new myX509TrustManager();

	private myHostnameVerifier hnv = new myHostnameVerifier();

	public HttpsUtils() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("TLS"); // SSL或TLS
			X509TrustManager[] xtmArray = new X509TrustManager[] { xtm };
			sslContext.init(null, xtmArray, new java.security.SecureRandom());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		if (sslContext != null) {
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
		}
		HttpsURLConnection.setDefaultHostnameVerifier(hnv);
	}

	public void run(String params, String appId, String ts,String sign) {
		HttpsURLConnection urlCon = null;
		try {
			// 获得上传信息的字节大小及长度
			byte[] mydata = params.getBytes();
			url = url + "?appId=" + appId + "&ts=" + ts + "&sign=" + sign;
			System.out.println("url:"+url);
			urlCon = (HttpsURLConnection) (new URL(url)).openConnection();
			urlCon.setDoOutput(true);
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-Length", String
					.valueOf(mydata.length));
			urlCon.setRequestProperty("Content-type", "text/xml");
//			urlCon.setUseCaches(false);
			urlCon.setDoInput(true);
			urlCon.getOutputStream().write(mydata);
			urlCon.getOutputStream().flush();
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlCon.getInputStream()));
			String line;
			System.out.print("服务端返回结果：");
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			// 增加自己的代码
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SignatureUtil generator  = new SignatureUtil();
		String appSecret = "liteng";
		String appId = "003101";
		String ts = "20160727182000";  
	    String xml = 
	    		"<webleak>" +
	    		    	
	    	"<leak>" + 
	    	"<url>http://192.168.1.1</url>" + 
	    	"<openly>否</openly>" + 
	    	"<unit_name>某某有限公司</unit_name>" + 
	    	"<leak_name>某某有限公司后台登录弱口令漏洞</leak_name>" + 
	    	"<leak_type>弱口令漏洞</leak_type>" + 
	    	"<leak_rank>低</leak_rank>" + 
	    	"<event_time>2016-09-08</event_time>" +
	    	"<prod_name>某某有限公司</prod_name>" + 
	    	"<soft_name>某某员工管理系统</soft_name>" + 
	    	"<leak_detail>用户名123密码456可登录</leak_detail>" +
	    	"<os_name>windows_98</os_name>" + 
	    	"<check_method>页面输入弱口令</check_method>" +
	    	"<solution>修改为较复杂的密码</solution>" + 
	    	"<note></note>" + 
	    	"</leak>" +

	    	"<leak>" + 
	    	"<url>http://192.168.1.2</url>" + 
	    	"<openly>是</openly>" + 
	    	"<unit_name>某某有限公司</unit_name>" + 
	    	"<leak_name>某某有限公司后SQL注入漏洞</leak_name>" + 
	    	"<leak_type>SQL注入漏洞</leak_type>" +
	    	"<leak_rank>高1</leak_rank>" + 
	    	"<event_time>2016-09-07</event_time>" +
	    	"<prod_name>某某有限公司</prod_name>" + 
	    	"<soft_name>某某工资查询系统</soft_name>" + 
	    	"<leak_detail>sql注入可实现万能密码登录</leak_detail>" +
	    	"<os_name>windows_98</os_name>" + 
	    	"<check_method>Sqlmap测试</check_method>" +
	    	"<solution>修改密码验证程序</solution>" + 
	    	"<note></note>" + 
	    	"</leak>" +

	    	"</webleak>";
	    String signature = generator.digest(appId + ts + appSecret, "MD5");
		HttpsUtils httpsTest = new HttpsUtils();
		httpsTest.run(xml, appId, ts, signature);
		
	}
}

/**
 * 重写三个方法
 * 
 * @author Administrator
 * 
 */
class myX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) {
//		System.out.println("cert: " + chain[0].toString() + ", authType: "
//				+ authType);
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}

/**
 * 重写一个方法
 * 
 * @author Administrator
 * 
 */
class myHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
//		System.out.println("Warning: URL Host: " + hostname + " vs. "
//				+ session.getPeerHost());
		return true;
	}
}
