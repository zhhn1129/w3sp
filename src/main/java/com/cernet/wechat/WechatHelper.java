/**
 * 
 */
package com.cernet.wechat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cernet.utils.log.Actionlog;
import com.cernet.utils.log.ActionlogManager;
import com.cernet.utils.log.Log;
import com.google.gson.Gson;

/**
 * @author apple
 *
 */
public class WechatHelper {
	//gson工具
	private static Gson gson = new Gson();
	
	//赛尔网络大数据安全微信服务号的属性
	static String appid = "wx6454af7ddd920d96";
	static String appsecret = "ffe08f165994c079d3fa923b89775940";
	static String access_token = "";
	static ActionlogManager actionlogManager;

	static HttpHelper httpHelper = new HttpHelper();
	
    public ActionlogManager getActionlogManager() {
		return actionlogManager;
	}

	public void setActionlogManager(ActionlogManager actionlogManager) {
		this.actionlogManager = actionlogManager;
	}

	/**
     * 获取微信接口的access_token
     * @param appid
     * @param appsecret
     * @return access_token
     * 			微信的全局唯一票据access_token,有效期目前为2个小时
     */
    public void getAccessToken(String appid, String appsecret) {
    	String url = "https://api.weixin.qq.com/cgi-bin/token";
    	String urlSuf = "?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
    	String paramStr = "";
    	String result = httpHelper.sendPost(url+urlSuf, paramStr);
    	TokenMessage tMessage = gson.fromJson(result, TokenMessage.class);
    	access_token = tMessage.getToken();
	}
    
    /**
     * 判断token是否已失效，如失效则返回true，否则返回false
     * 
     * @return
     * 			true	token已失效
     * 			false	token未失效
     */
    public boolean isTokenInvalid() {
    	boolean rt = true;
    	if (access_token != "" && access_token != null) {
        	String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
        	String urlSuf = "?access_token="+access_token;
        	String paramStr = "";
        	String result = httpHelper.sendPost(url+urlSuf, paramStr);

        	if (!result.contains("errcode")) {
        		rt = false;
    		} else {
    			// Convert JSON string back to Map.
    	    	JSONObject jsonObject = JSONObject.fromObject(result);
    	    	String errcode = jsonObject.getString("errcode");
    			String errmsg = jsonObject.getString("errmsg");
    			//返回信息包含errcode，打印出错信息
            	System.err.println("isTokenInvalidError:"+errcode+","+errmsg);
            	System.out.println("token已失效");
    			rt = true;
    		}
		}
    	return rt;
	}

    
    /**
     * 迭代获取关注用户的openids
     * @return
     * 			所有用户的openids数组
     */
    public List<String> getUserOpenids() {
    	String userListData = getUserList("");
    	//userListData like {"total":70,"count":70,"data":{"openid":["odS53xPdn7FN4QR7FIG1YvcrIy2M",...]},"next_openid":"odS53xEuodDQXUbkrsdLeyt8ubRU"}
    	if (userListData != "") {
            // Convert JSON string back to Map.
        	JSONObject jsonObject = JSONObject.fromObject(userListData);
            //UserListMessage userListMessage = gson.fromJson(userListData, UserListMessage.class);
            int total = jsonObject.getInt("total");
            int count = jsonObject.getInt("count");
            String next_openid = jsonObject.getString("next_openid");
    		List<String> openids = jsonObject.getJSONObject("data").getJSONArray("openid");
            int n = count;
            //递归判断是否还有用户没有取完，如有则再取一次并将返回值追加到openids里
            while (n < total) {
            	userListData = getUserList(next_openid);
                // Convert JSON string back to Map.
            	JSONObject jsonObject1 = JSONObject.fromObject(userListData);
                //UserListMessage userListMessage1 = gson.fromJson(userListData, UserListMessage.class);
                count = jsonObject1.getInt("count");
                next_openid = jsonObject1.getString("next_openid");
//              20170509 增加判断，防止next_openid为空时报错
                if(next_openid==null||"".equals(next_openid)){
                	break;
                }
        		List<String> openids1 = jsonObject1.getJSONObject("data").getJSONArray("openid");
        		openids.addAll(openids1);
            	n += count; 
    		}
            //System.out.println("openids="+openids);
            //openids like ["odS53xPdn7FN4QR7FIG1YvcrIy2M","odS53xH8EMQpfnegBeT1yRhXNczY",...]
    		return openids;
		} else {
			return null;
		}
	}
    /**
     * 获取关注用户列表，并返回data数据
     * @param token
     * 			微信的token
     * @param next_openid
     * 			下一页开始的openid，如果是第一页，可以为空
     * @return
     * 			用户列表信息
     * 			示例：{"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
     */
    public String getUserList(String next_openid) {
    	if (null == next_openid) {
    		next_openid = "";
    	}
    	String url = "https://api.weixin.qq.com/cgi-bin/user/get";
    	String urlSuf = "?access_token="+access_token+"&next_openid="+next_openid;
    	String paramStr = "";
    	String result = httpHelper.sendPost(url+urlSuf, paramStr);
    	//System.out.println(result);
    	//{"total":70,"count":70,"data":{"openid":["odS53xPdn7FN4QR7FIG1YvcrIy2M",...]},"next_openid":"odS53xEuodDQXUbkrsdLeyt8ubRU"}

    	if (!result.contains("errcode")) {
			return result;
		} else {
			// Convert JSON string back to Map.
	    	JSONObject jsonObject = JSONObject.fromObject(result);
	    	String errcode = jsonObject.getString("errcode");
			String errmsg = jsonObject.getString("errmsg");
			//返回信息包含errcode，打印出错信息
        	System.err.println("getUserListError:"+errcode+","+errmsg);
        	return "";
		}
	}
    
    /**
     * 批量获取指定列表用户的基本信息
     * @param token
     * @param user_list
     * 			需要返回基本信息的用户列表
     * 			{
     * 			    "user_list": [
     * 			        {
     * 			            "openid": "otvxTs4dckWG7imySrJd6jSi0CWE", 
     * 			            "lang": "zh-CN"
     * 			        }, 
     * 			        {
     * 			            "openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg", 
     * 			            "lang": "zh-CN"
     * 			        }
     * 			    ]
     * 			}
     * @return
     * 			官方返回的用户基本信息列表，最多100条
     */
    public String batchGetUserDetails(String token, String user_list) {
    	String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
    	String urlSuf = "?access_token="+token;
    	String paramStr = user_list;
    	String result = httpHelper.sendPost(url+urlSuf, paramStr);
    	System.out.println(result);
    	
    	if (!result.contains("errcode")) {
			return result;
		} else {
			// Convert JSON string back to Map.
	    	JSONObject jsonObject = JSONObject.fromObject(result);
	    	String errcode = jsonObject.getString("errcode");
			String errmsg = jsonObject.getString("errmsg");
			//返回信息包含errcode，打印出错信息
        	System.err.println("batchGetUserDetailsError:"+errcode+","+errmsg);
        	return "";
		}
	}
    /**
     * 转换用户列表数据，为批量获取用户基本信息准备post参数数据
     * @param userListData
     * 			关注用户的openid列表
     * @return
     * 			用户列表
     * 			{
     * 			    "user_list": [
     * 			        {
     * 			            "openid": "otvxTs4dckWG7imySrJd6jSi0CWE", 
     * 			            "lang": "zh-CN"
     * 			        }, 
     * 			        {
     * 			            "openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg", 
     * 			            "lang": "zh-CN"
     * 			        }
     * 			    ]
     * 			}
     */
    public String transformOpenidsToUserListData(List<String> openids) {
		//组合数据，准备批量获取用户基本信息的post数据
        Map<String, List<HashMap<String, String>>> postData = new HashMap(); 
        List<HashMap<String, String>> user_list = new ArrayList<HashMap<String, String>>();
    	for (String openid : openids) {
			HashMap<String, String> u = new HashMap();
			u.put("openid", openid);
			u.put("lang", "zh-CN");
			user_list.add(u);
		}
    	postData.put("user_list", user_list);
    	String data = gson.toJson(postData);
    	//System.out.println("data="+data);
    	// data like {"user_list":[{"openid":"odS53xH8EMQpfnegBeT1yRhXNczY","lang":"zh-CN"},...]}
    	
		return data;
	}
    /**
     * 获取微信公众号的关注用户并增量更新至本地数据库
     * @return
     * 		true	更新成功
     * 		false	更新失败
     */
    public boolean updateWXUser() {
    	boolean rt = false;
		//获取微信token
		if (isTokenInvalid()) {
			//再次获取token
			getAccessToken(appid,appsecret);
		}
		//获取用户列表数据
		List<String> openids = getUserOpenids();
		List<String> paraOpenids = new ArrayList();
		DBHelper dbHelper = new DBHelper();
		int count = openids.size();
		if (count != 0) {
			int n = 0;
			for (int i = 0; i < count; i++) {
				if (n % 100 == 0) {
					if (paraOpenids.size() != 0) {
						System.out.println(paraOpenids.size());
						System.out.println(paraOpenids);
						//以100条数据为单位，获取用户基本信息并更新到数据库
						//转换用户列表数据为下一步提交的参数
						String postPara = transformOpenidsToUserListData(paraOpenids);
						//批量获取用户的基本信息
				    	String userInfoList = batchGetUserDetails(access_token, postPara);
						//更新用户基本信息至数据库中
						dbHelper.updateWXChatUserTable(userInfoList);
						
						//清空临时用户数组
						paraOpenids.clear();
						//System.out.println(paraOpenids);
					}
				} else {
					//给临时数组添加数据
					paraOpenids.add(openids.get(i));
				}
				n++;
			}
			if(paraOpenids.size()!=0){
			//将剩余不足100条的数据，获取用户基本信息并更新到数据库
			//转换用户列表数据为下一步提交的参数
			String postPara = transformOpenidsToUserListData(paraOpenids);
			//批量获取用户的基本信息
	    	String userInfoList = batchGetUserDetails(access_token, postPara);
	    	System.out.println(userInfoList);
			//更新用户基本信息至数据库中
	    	rt = dbHelper.updateWXChatUserTable(userInfoList);
			}
	    	else{
	    		rt=true;
	    	}
		}else {
			rt = false;
		}
		return rt;
	}

    /**
     * 给模板“紧急工作消息提醒”添加数据
     * @param color
     * 			默认：#173177
     * @param first
     * @param keyword1
     * @param keyword2
     * @param keyword3
     * @param remark
     * @return
     */
    public HashMap<String, Object> parseTMData(String first, 
    		String keyword1, String keyword2, String keyword3, String remark) {
    	HashMap<String, Object> data = new HashMap();
    	String color = "#173177";
    	String color1 = "#000000";
    	HashMap<String, Object> d1 = new HashMap();
    	d1.put("value", first);
    	d1.put("color", color1);
    	data.put("first", d1);
    	HashMap<String, Object> d2 = new HashMap();
    	d2.put("value", keyword1);
    	d2.put("color", color);
    	data.put("keyword1", d2);
    	HashMap<String, Object> d3 = new HashMap();
    	d3.put("value", keyword2);
    	d3.put("color", color1);
    	data.put("keyword2", d3);
    	HashMap<String, Object> d4 = new HashMap();
    	d4.put("value", keyword3);
    	d4.put("color", color1);
    	data.put("keyword3", d4);
    	HashMap<String, Object> d5 = new HashMap();
    	d5.put("value", "　　"+remark);
    	d5.put("color", color1);
    	data.put("remark", d5);
		return data;
	}
    /**
     * 
     * @param remarkKeyword
     * @param first		//你好，近期流行Locky病毒，请注意防范。
     * @param keyword1	//重要
     * @param keyword3	//近期出现多次网络勒索相关的安全事件：黑客通过电子邮件附件等方式诱骗用户运行恶意程序将用户计算机上数据加密，
     * 						引发重要数据不可访问，并借此勒索用户。
     * @param remark	//建议大家在日常网络访问和收发电子邮件过程中注意防范，
     * 						不要随意打开不明来源或内容可疑的邮件附件（Word/PDF等）和点击邮件正文中的可疑网址链接。
     * @param url		//
     * @return
     */
    public String sendTemplateMessage(String remarkKeyword, String first, String keyword1, 
    		String keyword2,String keyword3, String remark, String url) {
		
    	/*// 配置信任库 服务器
		System.setProperty("javax.net.ssl.trustStore",
			"E:\\ssl\\universalCA.truststore"); 
		System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.trustStorePassword", "cnvd@123#");

		// 配置密钥库
		System.setProperty("javax.net.ssl.keyStore","E:\\ssl\\universalCA.keystore");
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStorePassword", "cnvd@123#");
    	*/
    	//获取微信token
		if (isTokenInvalid()) {
			//再次获取token
			getAccessToken(appid,appsecret);
		}
		//获取指定remark的用户列表
    	DBHelper dbHelper = new DBHelper();
    	Log aLog = new Log();
    	System.out.println("remarkKeyword:"+remarkKeyword);
		List<String> openids = dbHelper.selectOpenidsFromWXUserTable(remarkKeyword);
//    	System.out.println(openids.size());
		if(openids.size()==0){
    		
    		logForTask("admin", "postTemplateMessage","备注不存在："+remarkKeyword+"，详情："+keyword3+"，"+remark);
    		return "remarkEmpty";
    	}
		
		//给指定用户发送指定模板id的模板消息
		HashMap<String, Object> data = parseTMData(first, keyword1, keyword2, keyword3, remark);
		System.out.println("data1:"+data);
		String sendMsg = "";
		String sendStatus = "";
		for (int i = 0; i < openids.size(); i++) {
			String openid = openids.get(i);
			sendMsg = postTemplateMessage(openid, data, url);
			if("sendError".equals(sendMsg)){
				//推送失败
				logForTask("admin", "postTemplateMessage",openid+"信息推送失败："+remarkKeyword+"，详情："+keyword3+"，"+remark);
			}else {
				//推送成功
				logForTask("admin", "postTemplateMessage",openid+"信息推送成功："+remarkKeyword+"，详情："+keyword3+"，"+remark);
				sendStatus = "SUC";
			}
		}
		return sendStatus;
	}

    /**
     * 
     * @param openids
     * @param data
     */
    public String postTemplateMessage(List<String> openids, HashMap<String, Object> data, String sourceUrl) {
    	String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    	String urlSuf = "?access_token="+access_token;
    	String templateId = "xX3Sd0n1jizujw4wUXIXI5DdA2nxj5Xd_E55VUSLQvA";
    	System.out.println("for循环之前"+openids.size()+"----"+openids);
    	for (int i = 0; i < openids.size(); i++) {
    		HashMap<String, Object> para = new HashMap<String, Object>();
    		para.put("touser", openids.get(i));
    		para.put("template_id", templateId);
    		para.put("url", sourceUrl);
    		para.put("data", data);
    		String paraStr = gson.toJson(para);
    		System.out.println("paraStr22="+paraStr);
        	String result = httpHelper.sendPost(url+urlSuf, paraStr);
        	System.out.println("result22="+result);	
        	JSONObject jSONObject = JSONObject.fromObject(result);
        	if(!"ok".equals(jSONObject.get("errmsg"))){
        		return "sendError";
        	}
		}
    	
    	return "SUC";
	}

    /**
     * 
     * @param openids
     * @param data
     */
    public String postTemplateMessage(String openid, HashMap<String, Object> data, String sourceUrl) {
    	String url = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    	String urlSuf = "?access_token="+access_token;
    	String templateId = "xX3Sd0n1jizujw4wUXIXI5DdA2nxj5Xd_E55VUSLQvA";
		HashMap<String, Object> para = new HashMap<String, Object>();
		para.put("touser", openid);
		para.put("template_id", templateId);
		para.put("url", sourceUrl);
		para.put("data", data);
		String paraStr = gson.toJson(para);
		System.out.println("paraStr22="+paraStr);
    	String result = httpHelper.sendPost(url+urlSuf, paraStr);
    	System.out.println("result22="+result);	
    	JSONObject jSONObject = JSONObject.fromObject(result);
    	if(!"ok".equals(jSONObject.get("errmsg"))){
    		return "sendError";
    	}
    	return "SUC";
	}
    public static void main(String[] args) {
    	WechatHelper wcHelper = new WechatHelper();
		//获取微信关注用户的基本信息
		//wcHelper.updateWXUser();
		
		//关注用户备注信息筛选关键字
		String remarkKeyword = "测试分公司1";
		
		//欢迎信息
		String first = "你好，近期流行Locky病毒，请注意防范。";
		//重要程度
		String keyword1 = "重要";
		//事件简介
		String keyword3 = "近期出现多次网络勒索相关的安全事件：黑客通过电子邮件附件等方式诱骗用户运行恶意程序将用户计算机上数据加密，" +
							"引发重要数据不可访问，并借此勒索用户。";
		String keyword2="";
		//备注
		String remark = "建议大家在日常网络访问和收发电子邮件过程中注意防范，" +
						"不要随意打开不明来源或内容可疑的邮件附件（Word/PDF等）和点击邮件正文中的可疑网址链接。";
		//消息详情地址，没有详情页时可以为空
		String url = "";
		wcHelper.sendTemplateMessage(remarkKeyword, first, keyword1, keyword2, keyword3, remark, url);
	}
    
	public static void logForTask(String username, String action, String desc){
		Actionlog aLog = new Actionlog();

		aLog.setUsername(username);

		aLog.setAction(action);
		aLog.setDescription(desc);
		aLog.setUserIp("本机");
		aLog.setLocation("本机");
		aLog.setTime(new Date());
//		actionlogManager.save(aLog);
		
	}
    
}
