package com.cernet.spider;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cernet.util.JoinUrl;

/**
 * @author Administrator
 * 
 */

public class Crawl {
	private final static Log log = LogFactory.getLog(Crawl.class);
	private static String testUrl = "http://www.cnvd.org.cn/webinfo/list?type=14";  
	private static String __jsluid = "";
	private static String __jsl_clearance = "";

	private static long currSequence;
	public static synchronized String getSequence(){
		currSequence++;
		String str=String.valueOf(currSequence);
		String seq="";
		for(int i=0;i<12-str.length();i++)
			seq+="0";
		return  seq+str;
		
	}
	
	static{
		try{
			  HashMap<String,Object> map = CNVDJSEngine.getCookieMap();
			  __jsluid = map.get("__jsluid").toString();
		      __jsl_clearance = map.get("__jsl_clearance").toString();
//		      __jsluid = "f1907e2157d7643152ff9cbdbadd42d7";
//		      __jsl_clearance = "1497235450.527|0|C0131NDBG%2FMjtMGoTmLsZPT32Wo%3D";
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static List<String[]> getUrls(String url, String html, String encoding) {

		try {
			List<String[]> list = new ArrayList<String[]>();
			if(html == null || html.equals("")) {			
				return null;
			}
			Parser parser = Parser.createParser(html, encoding);
			NodeList nodeList = parser
					.extractAllNodesThatMatch(new NodeFilter() {
						public boolean accept(Node node) {
							if (node instanceof LinkTag)
								return true;
							return false;
						}
					});

			Set <String> uniqueUrls = new HashSet <String> (); 
	 			
			for (int i = 0; i < nodeList.size(); i++) {
				LinkTag n = (LinkTag) nodeList.elementAt(i);
				 
				String urlTemp = n.extractLink();
	 			
				urlTemp = urlTemp.replaceAll("&amp;", "&");
				JoinUrl joinUrl = new JoinUrl();
				urlTemp = joinUrl.joinUrl(url, urlTemp);
				
				if(!uniqueUrls.contains(urlTemp)){//去重
					uniqueUrls.add(urlTemp);
					list.add(new String[]{urlTemp,n.getLinkText()});
				}
				
			}
			return list;
		} catch (Exception e) {
			log.debug("can not get the urls. " + url);
			return null;
		}

	}

	
	/**
	 * 鑾峰彇鎸囧畾缃戦〉浠ｇ爜涓殑閾炬帴鍦板潃
	 * 
	 * @param url
	 * @param html
	 * @param encoding
	 * @return
	 */
	public List<String[]> getUrlList(String url, String html, String encoding) {

		try {
			List<String[]> list = new ArrayList<String[]>();
			if(html == null || html.equals("")) {			
				return null;
			}
			Parser parser = Parser.createParser(html, encoding);
			NodeList nodeList = parser
					.extractAllNodesThatMatch(new NodeFilter() {
						public boolean accept(Node node) {
							if (node instanceof LinkTag)
								return true;
							return false;
						}
					});

			for (int i = 0; i < nodeList.size(); i++) {
				LinkTag n = (LinkTag) nodeList.elementAt(i);
				if (url == null) {
					url = "";
				}
				String urlTemp = url + n.extractLink();
				urlTemp = urlTemp.replaceAll("&amp;", "&");
				
				list.add(new String[]{urlTemp,n.getLinkText()});

			}
			return list;
		} catch (Exception e) {
			log.debug("鑾峰彇urlList澶辫触锛�" + e.toString());
			return null;
		}

	}


/**
 * 
 * @param urlStr
 * @param startStr
 * @param endStr
 * @param encoding
 * @return
 */
	public static String getHtml(String urlStr, String startStr, String endStr,
			String encoding) {

		String html = null;
		
		try {
			html = getAllHtml(urlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		startStr = startStr.replaceAll("\r|\n|\t", "");
		endStr = endStr.replaceAll("\r|\n|\t", "");
		html = html.replaceAll("\r|\n|\t", " ");
	 
		int start = html.indexOf(startStr);
		int end  = html.indexOf(endStr);
		if (start == -1  || end == -1) {
			log.debug("startStr or endStr not found.");	
			return null;
		}		
		 html = html.substring(start,  end+ endStr.length());
		 return html;

	}

 /**
  * 
  * @param urlStr
  * @return
  */
	/*public static byte[] getAllHtml(String urlStr) {
		
		
			URL url = null;
			try {
				url = new URL(urlStr);
			} catch (MalformedURLException e) {
				
				log.debug( "malformedURL: " + urlStr);
			}

			ByteArrayOutputStream out = null;
			try {
				
				URLConnection  uc = url.openConnection();
				uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				  
			      uc.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");  
			      uc.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");  
			      uc.setRequestProperty("Accept-Charset", "UTF-8");
			      uc.setRequestProperty("Connection", "keep-alive");  
			      uc.setRequestProperty("Cookie", "__jsluid=7b6d807188faaf70addf723ba1a915cc;__jsl_clearance=1480582418.916|0|tqm0BlVTyoXXennGesqu1g%2F41pk%3D");  
			      uc.setRequestProperty("Host", "www.cnvd.org.cn");  
			      uc.setRequestProperty("If-Modified-Since", "Mon, 29 Aug 2016 01:59:34 GMT");  
			      uc.setRequestProperty("If-None-Match", "W/\"1724-1472435974000\"");  
			      uc.setRequestProperty("Referer", urlStr);  
			      uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");  
				
				InputStream in = new BufferedInputStream(uc.getInputStream());
				out = new ByteArrayOutputStream(1024);
				byte[] temp = new byte[1024];
				int size = 0;
 				while ((size = in.read(temp)) != -1) {
					out.write(temp, 0, size);
				}
				in.close();
			} catch (IOException e) {
				log.debug("can not read the content from url: " + urlStr );
				
				return null;
			}
			return out.toByteArray();

	}*/
	
	/**
	  * 下载html文件内容
	  * @param urlStr
	  * @return
	  */
		public static String  getAllHtml(String urlStr) {
				String responseString = "";
			    CloseableHttpClient client = HttpClients.createDefault();  
				try {
				      HttpGet httpGet = new HttpGet(urlStr); 
				      //设置headers
				      httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
				      httpGet.setHeader("Accept-Encoding", "gzip, deflate");  
				      httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");  
				      httpGet.setHeader("Connection", "keep-alive");  
				      httpGet.setHeader("Host", "www.cnvd.org.cn");  
//				      httpGet.setHeader("If-Modified-Since", "Mon, 29 Aug 2016 01:59:34 GMT");  
//				      httpGet.setHeader("If-None-Match", "W/\"1724-1472435974000\"");  
				      httpGet.setHeader("Cookie",__jsluid+";"+__jsl_clearance);
				      httpGet.setHeader("Referer", testUrl);  
				      httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
				      
				      HttpResponse httpResponse = client.execute(httpGet);  
				      System.out.println(httpResponse.getStatusLine().getStatusCode()+"Crawl>>>getAllhtml>>>\t request line:" + httpGet.getRequestLine());  
				      HttpEntity entity = httpResponse.getEntity();  
				      if (httpResponse.getStatusLine().getStatusCode() == 200) {
				    	   // 判断响应实体是否为空  
						    if (entity != null) {  
						      responseString = EntityUtils.toString(entity);  
						    } 
				      }else if (httpResponse.getStatusLine().getStatusCode() == 521) {
						  HashMap<String,Object> map = CNVDJSEngine.getCookieMap();
						  __jsluid = map.get("__jsluid").toString();
					      __jsl_clearance = map.get("__jsl_clearance").toString();
					      getAllHtml(urlStr);
			          }
				} catch (Exception e) {
					log.debug("can not read the content from url: " + urlStr );
					
					return null;
				}
				return responseString;

		}
	
	public byte[] getAllHtml(String urlStr,String encode) {
		System.out.println("@@@@@@@@@@@@@@@getAllHtml encode:"+encode);
		URL url = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		ByteArrayOutputStream out = null;
		try {
			InputStream in = url.openStream();
			out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}
		String content = null;
		try {
			content = new String(out.toByteArray(),encode);
			System.out.println("@@@@@@@@@@@@@@@getAllHtml content:"+content);

		} catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
		NodeFilter imageFilter = new NodeClassFilter(ImageTag.class);
		Parser parser = Parser.createParser(content, encode);
		NodeList nodeList = null;
		try {
			nodeList = parser.extractAllNodesThatMatch(imageFilter);
		} catch (ParserException e) {

			e.printStackTrace();
		}
		URL pageUrl = null;
		try {
			pageUrl = new URL(urlStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < nodeList.size(); i++) {

			ImageTag imagenode = (ImageTag) nodeList.elementAt(i);
			String link = imagenode.getImageURL();
			String link2 = null;
			if (link.indexOf("://") == -1) {
				int port = pageUrl.getPort();
				if(port == -1) {
					port = 80;
				}
				if (link.charAt(0) == '/') {
					link2 = "http://" + pageUrl.getHost() + ":"
							+ port + link;
				} else {
					String file = pageUrl.getFile();
					if (file.indexOf('/') == -1) {
						link2 = "http://" + pageUrl.getHost() + ":"
								+ port + "/" + link;
					} else {
						String path = file.substring(0,
								file.lastIndexOf('/') + 1);
						link2 = "http://" + pageUrl.getHost() + ":"
								+ port + path + link;
					}
				}
			}
			if (link2 != null) {
				System.out.println(content.indexOf(link));
				content = content.replace(link, link2);
			}

		}
		
		return content.getBytes();

	}


	/**
	 * 鏍规嵁绠�鍗曞垎椤佃鍒欒幏鍙栧垪琛�
	 * 
	 * @param urlStr
	 *            涓嬩竴绾ф柊闂诲唴瀹圭殑url鍓嶇紑锛屽鏋滀笅涓�绾т负缁濆璺緞锛岃鍙傛暟涓�"";
	 * @param startStr
	 *            鎻愬彇鍒楄〃鍐呭鐨勫紑濮嬫爣蹇�
	 * @param endStr
	 *            鎻愬彇鍒楄〃鍐呭鐨勭粨鏉熸爣蹇�
	 * @param rule
	 *            鍒嗛〉瑙勫垯
	 * @return 鏂伴椈鍒楄〃鐨勯摼鎺�
	 */
	public List<String[]> getLinksByRule1(String urlStr, String beginStr,
			String endStr, String ruleStr, String encoding) {

		List<String[]> list = new ArrayList<String[]>();
		String temp = ruleStr.substring(ruleStr.indexOf("{"), ruleStr
				.indexOf("}") + 1);
		String begin = temp.substring(1, temp.indexOf(","));
		String end = temp.substring(temp.indexOf(",") + 1, temp.length() - 1);
		for (int i = Integer.parseInt(begin); i <= Integer.parseInt(end); i++) {
			String url = ruleStr.substring(0, ruleStr.indexOf("{")) + i;
			String html = getHtml(url, beginStr, endStr, encoding);
			list.addAll(this.getUrlList(urlStr, html, encoding));
		}
		return list;

	}
	

	/**
	 * 鏍规嵁澶嶆潅鍒嗛〉瑙勫垯鍒楄〃
	 * @param urlStr
	 * @param beginStr
	 * @param endStr
	 * @param ruleStr
	 * @param encoding
	 * @return
	 */
	public List<String[]> getLinksByRule2(String urlStr, String beginStr,
			String endStr, String ruleStr, String encoding) {

		List<String[]> list = new ArrayList<String[]>();

		String firstPage = ruleStr.substring(10, ruleStr.indexOf(","));
		String connectFlag = ruleStr.substring(
				ruleStr.indexOf("connectFlag=") + 12,
				ruleStr.indexOf("range") - 1);
		String temp = ruleStr.substring(ruleStr.indexOf("{"), ruleStr
				.indexOf("}") + 1);
		String begin = temp.substring(1, temp.indexOf(","));
		String end = temp.substring(temp.indexOf(",") + 1, temp.length() - 1);
		String suffix = ruleStr.substring(ruleStr.indexOf("suffix") + 7);

		list.addAll(getUrlList(urlStr, getHtml(firstPage + suffix, beginStr,
				endStr, encoding), encoding));

		for (int i = Integer.parseInt(begin); i <= Integer.parseInt(end); i++) {
			String url = firstPage + connectFlag + i + suffix;			
			String html = getHtml(url, beginStr, endStr, encoding);			
			list.addAll(this.getUrlList(urlStr, html, encoding));
		}
		return list;

	}

	/**
	 * 鏍规嵁鐢ㄦ埛鏃犺寰嬪垎椤佃鍒欒幏鍙栧垪琛�
	 * @param urlStr
	 * @param beginStr
	 * @param endStr
	 * @param ruleStr
	 * @param encoding
	 * @return
	 */
	public List<String[]> getLinksByRule3(String urlStr, String beginStr,
			String endStr, String ruleStr, String encoding) {

		List<String[]> list = new ArrayList<String[]>();

		String[] links = ruleStr.split(",");

		for (int i = 0; i < links.length; i++) {
			String url = links[i];
			String html = getHtml(url, beginStr, endStr, encoding);
			list.addAll(this.getUrlList(urlStr, html, encoding));
		}

		return list;

	}

}
