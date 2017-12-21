package com.cernet.postXML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

	// 表示服务器端的url
//	private static String PATH = "http://219.224.97.72:8091/postXML/login";
	 private static String PATH = "http://219.224.97.72:8080/w3sp/login";
	private static URL url;

	public HttpUtils() {
	    // TODO Auto-generated constructor stub
	  }

	static {
		try {
			url = new URL(PATH);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * params 填写的URL的参数 encode 字节编码
	 */
	public static String sendPostMessage(String params,
			String encode) {

//		StringBuffer stringBuffer = new StringBuffer();
//
//		if (params != null && !params.isEmpty()) {
//			for (Map.Entry<String, String> entry : params.entrySet()) {
//				try {
//					stringBuffer.append(entry.getKey()).append("=").append(
//							URLEncoder.encode(entry.getValue(), encode))
//							.append("&");
//
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			// 删掉最后一个 & 字符
//			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
//			System.out.println("-->>" + stringBuffer.toString());
//		}

		try {
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setConnectTimeout(3000);
			httpURLConnection.setDoInput(true);// 从服务器获取数据
			httpURLConnection.setDoOutput(true);// 向服务器写入数据
//			httpURLConnection.setUseCaches(false);

			// 获得上传信息的字节大小及长度
			byte[] mydata = params.getBytes();
			// 设置请求体的类型
			httpURLConnection.setRequestProperty("Content-type", "text/xml");
			httpURLConnection.setRequestProperty("Content-Lenth", String
					.valueOf(mydata.length));
			httpURLConnection.setRequestMethod("POST");

			// 获得输出流，向服务器输出数据
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(mydata);
			outputStream.flush();
			outputStream.close();

			// 获得服务器响应的结果和状态码
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == 200) {

				// 获得输入流，从服务器端获得数据
				InputStream inputStream = (InputStream) httpURLConnection
						.getInputStream();
				return (changeInputStream(inputStream, encode));

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/*
	 * // 把从输入流InputStream按指定编码格式encode变成字符串String
	 */
	public static String changeInputStream(InputStream inputStream,
			String encode) {

		// ByteArrayOutputStream 一般叫做内存流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {

			try {
				while ((len = inputStream.read(data)) != -1) {
					byteArrayOutputStream.write(data, 0, len);

				}
				result = new String(byteArrayOutputStream.toByteArray(), encode);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return result;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		SignatureUtil generator  = new SignatureUtil();
		String appSecret = "cernet001";
		String appID = "003100";
		long millis = System.currentTimeMillis();  
	    String xml = "<RESULT>" +
	    		"<leak>" +
	    		"<ID>1000</ID>" +
	    		"<EVENT_DATE>2018-01-02</EVENT_DATE>" +
	    		"<NODE_ID>K483948</NODE_ID>" +
	    		"<NAME_C>清华大学</NAME_C>" +
	    		"<CORP_SUB_NAME>北京分公司</CORP_SUB_NAME>" +
	    		"<CTRLED_TAG></CTRLED_TAG>"+
	    		"<IP></IP>"+
	    		"<URL>http://www.baidu.com</URL>"+
	    		"<PATH></PATH>"+
	    		"<MEMO></MEMO>"+
	    		"<LEAK_TYPE>STRUTS2漏洞</LEAK_TYPE>"+
	    		"<NAME_C2></NAME_C2>"+
	    		"<STATUS>1</STATUS>"+
	    		"<NODE_TYPE>集团业务</NODE_TYPE>"+
	    		"<SCORE>10</SCORE>"+
	    		"<PROD_NAME>1</PROD_NAME>"+
	    		"<SOFT_NAME>广州大学质量工程管理系统</SOFT_NAME>"+
	    		"<REFER_FILE>广州大学质量工程管理系统命令执行漏洞</REFER_FILE>"+
	    		"<CHECK_METHOD>检测结果：dir:D:-apache-tomcat-6.0.20-webapps-proapply-</CHECK_METHOD>"+
	    		"</leak>" +
	    		"<leak>"+
	    		"<ID>1001</ID>"+
	    		"<EVENT_DATE>2018-02-02</EVENT_DATE>"+
	    		"<NODE_ID>F794612</NODE_ID>"+
	    		"<NAME_C>北京大学</NAME_C>"+
	    		"<CORP_SUB_NAME>上海分公司</CORP_SUB_NAME>" +
	    		"<CTRLED_TAG></CTRLED_TAG>"+
	    		"<IP></IP>"+
	    		"<URL>http://tsinghua.com</URL>"+
	    		"<PATH></PATH>"+
	    		"<MEMO>1</MEMO>"+
	    		"<LEAK_TYPE>sql漏洞</LEAK_TYPE>"+
	    		"<NAME_C2></NAME_C2>"+
	    		"<STATUS>2</STATUS>"+
	    		"<NODE_TYPE>集团业务</NODE_TYPE>"+
	    		"<SCORE>9</SCORE>"+
	    		"<PROD_NAME>2</PROD_NAME>"+
	    		"<SOFT_NAME>管理系统</SOFT_NAME>"+
	    		"<REFER_FILE>工程管理系统命令执行漏洞.docx</REFER_FILE>"+
	    		"<CHECK_METHOD>检测结果：dir:proapply-</CHECK_METHOD>"+
	    		"</leak>" +
	    		"</RESULT>";
//	    String token = generator.findTokenById(appSecret);  
	    String signature = generator.digest(appID + String.valueOf(millis) + appSecret, "MD5");
//      String signature = generator.generateSignature(token, digest , appID);  
//	    Map<String, String> params = new HashMap<String, String>();
//	    params.put("appID", appID);
//	    params.put("millis", String.valueOf(millis));
//	    params.put("signature", signature);
//	    params.put("xml", xml);
	    
	    String result = sendPostMessage(appID+"Scharacter"+String.valueOf(millis)+"Scharacter"+signature+"Scharacter"+xml, "utf-8");
	    System.out.println("-result->>" + result);

	  }
	
}
