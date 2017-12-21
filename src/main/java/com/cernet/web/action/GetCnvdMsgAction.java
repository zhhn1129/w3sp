package com.cernet.web.action;

import com.cernet.dao.WebLeakInfoDao;
import com.cernet.model.HighPush;
import com.cernet.model.LeakPushMonitor;
import com.cernet.model.WebLeakInfo;
import com.cernet.service.HighPushManager;
import com.cernet.service.LeakPushMonitorManager;
import com.cernet.service.WebLeakInfoManager;
import com.cernet.spider.CNVDJSEngine;
import com.cernet.wechat.WechatHelper;
import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetCnvdMsgAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HighPushManager highPushManager;
	private WebLeakInfoDao webLeakInfoDao;
	private WebLeakInfoManager webLeakInfoManager;
	private LeakPushMonitorManager leakPushMonitorManager;
	public String date = "2016-05-20";
	public String url = "";
	public String msg = "";
	public String title;
	public String downCode;
	public String finishDate;
	public String name;
	public String leakType;
	public JSONArray jsonArray;
	public boolean errorFlag;

	public LeakPushMonitorManager getLeakPushMonitorManager() {
		return leakPushMonitorManager;
	}

	public void setLeakPushMonitorManager(
			LeakPushMonitorManager leakPushMonitorManager) {
		this.leakPushMonitorManager = leakPushMonitorManager;
	}

	public HighPushManager getHighPushManager() {
		return highPushManager;
	}

	public void setHighPushManager(HighPushManager highPushManager) {
		this.highPushManager = highPushManager;
	}

	/*
	 * 读取cnvd.properties数据
	 */
	public Properties getCnvdProperties() {
		Properties prop = new Properties();// 读取属性文件cnvd.properties
		InputStream in = GetCnvdMsgAction.class.getClassLoader()
				.getResourceAsStream("cnvd.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
		}
		return prop;
	}

	/*
	 * 获取properties里key信息
	 */
	public String getCnvdByKey(String key) {
		String value = getCnvdProperties().getProperty(key);
		return value;
	}

	/*
	 * 通过CNVD接口获得JSON数据
	 */
	private JSONArray getJsonByCnvd() {
		GetCnvdMsgAction cnvdMsgAction = new GetCnvdMsgAction();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = dateFormat.format(new Date());
//		date ="2017-10-24";
		url = cnvdMsgAction.getCnvdByKey("uri") + "&date=" + date;
		System.out.println("日期显示:" + date+"\turl:"+url);
		msg = cnvdMsgAction.getMsgByHttpUrl(url);
		jsonArray = JSONArray.fromObject(msg);
		try {
			for (int i = 0; i < 5; i++) {
				errorFlag = jsonArray.getJSONObject(0).containsKey("error");
				if (!errorFlag) {
					break;
				} else {
					msg = cnvdMsgAction.getMsgByHttpUrl(url);
					jsonArray = JSONArray.fromObject(msg);
				}
			}
			System.out.println("jsonArray大小:" + jsonArray.size());
		} catch (IndexOutOfBoundsException exception) {
			System.out.println("CNVD数据为空,不存在errorkey,数组越界");
		}
		return jsonArray;
	}
	/*
	 * 通过CNVD获取截取漏洞存储到highPush对象并将对象存储到list中
	 */
	public List<HighPush> getLeakMsgByCnvd() {
		String[] schNames = getCnvdByKey("schName").split(",");
		// String[] leakTypes=getCnvdByKey("leakType").split(",");
		List<HighPush> list = new ArrayList<HighPush>();
		JSONArray jsonArray = getJsonByCnvd();
		String dateCreated="";
		for (int i = 0; i < jsonArray.size(); i++) {// 外层循环
			title = jsonArray.getJSONObject(i).getString("title");
			downCode = jsonArray.getJSONObject(i).getString("downCode");
			finishDate = jsonArray.getJSONObject(i).getString("finishDate");
			dateCreated=jsonArray.getJSONObject(i).getString("dateCreated");
			int index = title.indexOf("存在");
			for (int j = 0; j < schNames.length; j++) {
				int num = title.indexOf(schNames[j]);
				if (num == -1) {
					continue;
				} else {
					name = title.substring(0, num + schNames[j].length());
					System.out.println("单位名称:" + name);
					break;
				}
			}
			System.out.println("title:" + title);
			leakType = title.substring(index + 2);
			System.out.println("漏洞类型:" + leakType);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			date = dateFormat.format(new Date());
			System.out.println("时间:" + dateCreated);
			System.out.println("结束时间:" + finishDate);
			System.out.println("漏洞报告下载链接:" + downCode);
			// sendMsgToWechat(leakType,name,title,date,downCode);// 调用微信自动推送方法
			System.out.println("*****************************************************");
			HighPush highPush = new HighPush();
			try {
				Date creDate = dateFormat.parse(dateCreated);
				highPush.setCreateTime(creDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			highPush.setLeakTitle(title);
			highPush.setLeakType(leakType);
			highPush.setLeakSource("CNVD采集程序");
			highPush.setNameC(name);
			highPush.setUrl(downCode);
			highPush.setPriority("高");
			highPush.setPush(0l);
			list.add(highPush);
		}
		return list;
	}

	/**
	 * 调用微信模版推送消息
	 */
	public void sendMsgToWechat() throws IOException {
		// 发送微信消息
		DetachedCriteria dCriteria = DetachedCriteria.forClass(HighPush.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = dateFormat.format(new Date());
		try {
			Date creDate = dateFormat.parse(date);
			dCriteria.add(Restrictions.eq("createTime", creDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dCriteria.add(Restrictions.eq("push", 0l));
		List<HighPush> result = highPushManager.getListByDetachedCriteria(dCriteria);
		for (int i = 0; i < result.size(); i++) {
			HighPush highPush = result.get(i);
			// if
			// ("测试之大学".equals(highPush.getNameC())||"测试的大学".equals(highPush.getNameC())){
			// 调用微信推送接口
			highPush.setPush(1l);
			highPushManager.save(highPush);
			WechatHelper wechatHelper = new WechatHelper();
			String sendMsg = "";
			sendMsg = wechatHelper.sendTemplateMessage(highPush.getNameC(),
					highPush.getNameC() + " ，您好！", highPush.getPriority()
							+ "，请在24小时之内尽快处理该漏洞。", date, highPush.getLeakTitle(), "漏洞报告下载链接：" + highPush.getUrl(),
					highPush.getUrl());
			// 推送信息至学校对应的分公司
			String corpName = getCorpBySchName(highPush.getNameC());

			if (!"".equals(corpName)) {
				// 推送信息
			wechatHelper.sendTemplateMessage(corpName, highPush.getNameC()
			+ " ，您好！",highPush.getPriority() + "，请在24小时之内尽快处理该漏洞。", date,
			highPush.getLeakTitle(), "漏洞报告下载链接："
			+ highPush.getUrl(), highPush.getUrl());
			}
			// }
			// 将推送信息入库存WEBLEAK_PUSH_MONITOR表
			if ("SUC".equals(sendMsg)) {
				LeakPushMonitor obj = new LeakPushMonitor();
				obj.setCorpName(corpName);
				obj.setNameC(highPush.getNameC());
				try {
					obj.setLastTime(dateFormat.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				obj.setLeakDetail(highPush.getUrl());
				obj.setLeakType(highPush.getLeakType());
				obj.setProName(highPush.getLeakSource());
				obj.setStatus("1");
				leakPushMonitorManager.save(obj);
				// getResponse().getWriter().write("Success!");
			}
		}
	}
	// getResponse().getWriter().close();
	// }
	/**
	 * 通过学校获取分公司名
	 * 
	 * @param schName 学校名
	 * 
	 * @return corpName 分公司名
	 */
	public String getCorpBySchName(String schName) {
		String corpName = "";
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.add(Restrictions.like("nameC", "%" + schName + "%")); // 未修复
		List<WebLeakInfo> corpNamelist = webLeakInfoManager
				.getListByDetachedCriteria(d);
		if (corpNamelist.size() != 0) {
			corpName = corpNamelist.get(0).getCorpSubName();
		}
		return corpName;
	}
	private static String namespaceUri = "http://webservice.cnvd.com";
	private static String __jsluid = "";
	private static String __jsl_clearance = "";

	/*static{
		try{
			  HashMap<String,Object> map = CNVDJSEngine.getCookieMap();
			  __jsluid = map.get("__jsluid").toString();
		      __jsl_clearance = map.get("__jsl_clearance").toString();
//		      __jsluid = "f1907e2157d7643152ff9cbdbadd42d7";
//		      __jsl_clearance = "1503472009.437|0|Xz0DmL%2BowYos4VPjpCPJUpTaOkc%3D";
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
	private String getMsgByHttpUrl(String URL) {
		try{
			HashMap<String,Object> map = CNVDJSEngine.getCookieMap();
			  __jsluid = map.get("__jsluid").toString();
		      __jsl_clearance = map.get("__jsl_clearance").toString();
//		      __jsluid = "f1907e2157d7643152ff9cbdbadd42d7";
//		      __jsl_clearance = "1503472009.437|0|Xz0DmL%2BowYos4VPjpCPJUpTaOkc%3D";
		}catch(Exception e){
			e.printStackTrace();
		}
		// 发送HTTP请求调用CNVD接口
//		URL url = null;
//		HttpURLConnection httpurlconnection = null;
		
		/*// 配置信任库 服务器
		String path=getCnvdByKey("path");
		System.setProperty("javax.net.ssl.trustStore",
				path+"universalCA.truststore"); 
		System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.trustStorePassword", "cnvd@123#");

		// 配置密钥库
		System.setProperty("javax.net.ssl.keyStore",path+"universalCA.keystore");
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStorePassword", "cnvd@123#"); 
		
		Service service = new Service();
		Call call = null;
		try {
			call = (Call) service.createCall();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		call.setOperationName(new QName(namespaceUri, "getApiTask"));
		try {
			call.setTargetEndpointAddress(new URL(URL));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		date = dateFormat.format(new Date());
		System.out.println("date="+date);*/
		//以下两个参数对应时间，key值
		/*Object[] b = new Object[]{date,"d28aeddd855992fdd1d82b42169eb1ea1470708207215"};
		Object result = null;
		try {
			result = call.invoke(b);
			jsonStr=result.toString();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.toString());
		jsonStr=result.toString();*/
//		try {
//			System.out.println("url:" + URL);
//			url = new URL(URL);
//			// 以post方式请求
//			httpurlconnection = (HttpURLConnection) url.openConnection();
//			httpurlconnection.setDoOutput(true);
//			httpurlconnection.setConnectTimeout(4000);
//			httpurlconnection.setReadTimeout(4000);
//
//			httpurlconnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
////			httpurlconnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//			httpurlconnection.setRequestProperty("Accept-Encoding","gzip, deflate");
////			httpurlconnection.setRequestProperty("Cookie","__jsluid=f1907e2157d7643152ff9cbdbadd42d7;__jsl_clearance=1503472009.437|0|Xz0DmL%2BowYos4VPjpCPJUpTaOkc%3D");
////			httpurlconnection.setRequestProperty("Accept-Language","");
//			httpurlconnection.setRequestProperty("Host", "www.cnvd.org.cn");
//			httpurlconnection.setRequestProperty("connection", "Keep-Alive");
//			httpurlconnection.setRequestProperty("Cookie",__jsluid+";"+__jsl_clearance);
//			httpurlconnection.setRequestProperty("User-Agent",
//							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
//			httpurlconnection.setUseCaches(false);
//			httpurlconnection.setRequestMethod("GET");
//
//			// httpurlconnection.connect();
//			httpurlconnection.getOutputStream().flush();
//			httpurlconnection.getOutputStream().close();
//
//			// 获取响应代码
//			int code = httpurlconnection.getResponseCode();
//			System.out.println("code:" + code);
//			// 获取页面内容
//			InputStream in = httpurlconnection.getInputStream();
//			BufferedReader breader = new BufferedReader(new InputStreamReader(
//					in, "utf-8"));
//			String str = "";
//			while ((str = breader.readLine()) != null) {
//				jsonStr += str;
//			}
			String jsonStr = "";
			CloseableHttpClient client = HttpClients.createDefault();  
		    try {  
		      HttpGet httpGet = new HttpGet(URL); 
		      //设置headers
		      httpGet.setHeader("Accept", "*/*");  
		      httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");  
		      httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");  
		      httpGet.setHeader("Connection", "keep-alive");  
		      httpGet.setHeader("Cookie",__jsluid+";"+__jsl_clearance);
		      httpGet.setHeader("Host", "www.cnvd.org.cn");  
		      httpGet.setHeader("If-None-Match", "W/\"1724-1472435974000\"");  
		      httpGet.setHeader("Referer", URL);  
		      httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
		      
		      HttpResponse httpResponse1 = client.execute(httpGet);  
		      HttpEntity entity = httpResponse1.getEntity(); 
		      if(httpResponse1.getStatusLine().getStatusCode()==521){
		    	  getMsgByHttpUrl(URL);
		      }
		      jsonStr=EntityUtils.toString(entity);
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		} finally {
			/*if (httpurlconnection != null)
				httpurlconnection.disconnect();*/
			try {  
		        client.close();  
		      } catch (IOException e) {  
		        e.printStackTrace();  
		      } 
		}
		if (jsonStr != null && !"".equals(jsonStr)) {
			// JSONObject jsonUser = JSONObject.fromObject(jsonStr);
			System.out.println("CNVD数据如下：" + jsonStr);
			return jsonStr;
		} else {
			return null;
		}
	}

	/**
	 * TODO:测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GetCnvdMsgAction getCnvdMsgAction = new GetCnvdMsgAction();
		String url=getCnvdMsgAction.getCnvdByKey("uri");
		System.out.println("uri:>>>>>>>>>>>>"+url);
		getCnvdMsgAction.getLeakMsgByCnvd();
		//System.out.println(s);
	}

	public Date getStatetime(int num) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, num);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		try {
			System.out.println("获得时间：" + sdf.parse(preMonday));
			return sdf.parse(preMonday);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public WebLeakInfoDao getWebLeakInfoDao() {
		return webLeakInfoDao;
	}

	public void setWebLeakInfoDao(WebLeakInfoDao webLeakInfoDao) {
		this.webLeakInfoDao = webLeakInfoDao;
	}

	public WebLeakInfoManager getWebLeakInfoManager() {
		return webLeakInfoManager;
	}

	public void setWebLeakInfoManager(WebLeakInfoManager webLeakInfoManager) {
		this.webLeakInfoManager = webLeakInfoManager;
	}
}
