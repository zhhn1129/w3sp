package com.cernet.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.bcel.generic.NEW;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import com.cernet.dao.WebLeakInfoDao;
import com.cernet.model.LeakPushMonitor;
import com.cernet.model.WebLeakInfo;
import com.cernet.service.LeakPushMonitorManager;
import com.cernet.service.WebLeakInfoManager;
import com.cernet.util.Md5;
import com.cernet.wechat.WechatHelper;

public class SendLeakMsgAction extends BaseAction {
	   private static final long serialVersionUID = 1L;
	   private WebLeakInfo webLeakInfo;
	   private WebLeakInfoDao webLeakInfoDao;
	   private WebLeakInfoManager webLeakInfoManager;
       private String scoreStr;
       private String daysStr;
       private int days;
       private LeakPushMonitorManager leakPushMonitorManager;
       public LeakPushMonitorManager getLeakPushMonitorManager() {
		return leakPushMonitorManager;
	}

	public void setLeakPushMonitorManager(
			LeakPushMonitorManager leakPushMonitorManager) {
		this.leakPushMonitorManager = leakPushMonitorManager;
	}

	public WebLeakInfo getWebLeakInfo() {
		return webLeakInfo;
	}

	   public void setWebLeakInfo(WebLeakInfo webLeakInfo) {
		this.webLeakInfo = webLeakInfo;
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
		/**
		 * 读取leakMsgScore.properties数据
		 */
		public Properties getScoreProperties() {
			Properties prop = new Properties();// 读取属性文件leakMsgScore.properties
			InputStream in = SendLeakMsgAction.class.getClassLoader().getResourceAsStream("leakMsgScore.properties");
			try {
				prop.load(in);
		
			  } catch (IOException e) {
			}
			return prop;
		}
		/**
		 * 获取properties里key信息
		 */
		public String getScoreMsgByKey(String key) {
			String value = getScoreProperties().getProperty(key);
			
			return value;
		}

		/**
	    * 对漏洞等级为10的漏洞的推送6个月以上未修复的漏洞信息1天推送2次
	    */
           public void sendLeakMsgTwice(){
        	 scoreStr = getScoreMsgByKey("score");
        	 daysStr = getScoreMsgByKey("days");
        	 days=Integer.valueOf(daysStr).intValue();
			WechatHelper wcHelper = new WechatHelper();
			DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
			d.add(Restrictions.eq("status", "1"));	//未修复的 (0:已处理；1：未修复；2：链接打不开；3：网络不通)
			d.add(Restrictions.eq("score", scoreStr));//漏洞等级为10的漏洞
			d.add(Restrictions.eq("leakType", "STRUTS2漏洞"));//由于其他漏洞监测没实现自动化，现只推送STRUTS2漏洞
			try {
				d.add(Restrictions.lt("eventDate", getStatetime(days)));//最近六个月以上
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			d.setProjection(Projections.projectionList()  
			        .add(Projections.min("id"),"id")	//按nameC分组列出对应最小的ID
			        .add(Projections.groupProperty("nameC"))  
				    );  
			d.setResultTransformer(Transformers.aliasToBean(WebLeakInfo.class));
			
			List<WebLeakInfo> leakIds = webLeakInfoDao.getListByDetachedCriteria(d);
			for (WebLeakInfo leakId : leakIds) {
				WebLeakInfo leak = getWebLeakById(leakId.getId());		
				//关注用户备注信息筛选关键字
				String remarkKeyword = leak.getNameC();
				
				//欢迎信息
				String first = leak.getNameC()+" ，您好！";
				//重要程度
				String keyword1 = leak.getScore();
				//事件简介
				String keyword3 = "请注意防范"+leak.getLeakType();
				//时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String keyword2 ="";
				if (leak.getEventDate()==null) {
					continue;
				}
				else {
					keyword2 = sdf.format(leak.getEventDate());
				}
			    
				//备注
				String remark = "漏洞URL "+leak.getUrl();
				//消息详情地址，没有详情页时可以为空
//				String date=sdf.format(new Date());
				String namecStr=null;
				namecStr=leak.getNameC();
				if (namecStr==null){
					continue;
				}
                 else {
					// 对leak.getNameC()学校名进行MD5加密
					String url = "http://" + getIpAddrFormPro()+ "/w3sp/showScoLeakList.do?nameC="+ Md5.convertMD5(leak.getNameC());
					// 推送信息
					 if(!"".equals(leak.getNameC()))
					 {
					String sendNamecMsg = "";
					sendNamecMsg = wcHelper.sendTemplateMessage(remarkKeyword,first, keyword1, keyword2, keyword3, remark, url);
					String corpName = getCorpBySchName(remarkKeyword);
					 if(!"".equals(corpName)){
					// 推送信息至学校对应的分公司
					//if (!"".equals(corpName)) {
						String sendMsg = "";
						sendMsg=wcHelper.sendTemplateMessage(corpName, first, keyword1,keyword2, keyword3, remark, url);
					 if ("SUC".equals(sendMsg)) {
						savePushMonitor(leak);//将推送信息入库存WEBLEAK_PUSH_MONITOR表 增加分发监控
					  }
				   }
				 } 
			   }
			 }
		  }
           /**
            * 推送6月以上漏洞信息成功后入分发监控表
            * @param leak
            */
           public void savePushMonitor(WebLeakInfo leak){
        	   LeakPushMonitor obj = new LeakPushMonitor();
        	   String corpName = getCorpBySchName(leak.getNameC());
				obj.setCorpName(corpName);
				obj.setNameC(leak.getNameC());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date=sdf.format(new Date());
				try {
					obj.setLastTime(sdf.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				obj.setLeakDetail(leak.getUrl());
				obj.setLeakType(leak.getLeakType());
				obj.setProName("6个月以上高危漏洞推送");
				obj.setStatus("7");
				leakPushMonitorManager.save(obj);
           }

   		/**
   	    * 对漏洞等级为10的漏洞的推送6个月以内未修复的漏洞信息1天推送1次
   	    */
           public void sendLeakMsgOnce(){
        	scoreStr = getScoreMsgByKey("score");
          	daysStr = getScoreMsgByKey("days");
          	days=Integer.valueOf(daysStr).intValue();
   			WechatHelper wcHelper = new WechatHelper();
   			DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
   			d.add(Restrictions.eq("status", "1"));	//未修复的 (0:已处理；1：未修复；2：链接打不开；3：网络不通)
			d.add(Restrictions.eq("score", scoreStr));//漏洞等级为10的漏洞
			d.add(Restrictions.eq("leakType", "STRUTS2漏洞"));//由于其他漏洞监测没实现自动化，现只推送STRUTS2漏洞
   			try {
   				d.add(Restrictions.ge("eventDate", getStatetime(days)));//最近六个月以内
   			} catch (ParseException e) {
   				e.printStackTrace();
   			}	
   			d.setProjection(Projections.projectionList()  
   			        .add(Projections.min("id"),"id")	//按nameC分组列出对应最小的ID
   			        .add(Projections.groupProperty("nameC"))  
   				    );  
   			d.setResultTransformer(Transformers.aliasToBean(WebLeakInfo.class));
   			List<WebLeakInfo> leakIds = webLeakInfoDao.getListByDetachedCriteria(d);
   			for (WebLeakInfo leakId : leakIds) {
			WebLeakInfo leak = getWebLeakById(leakId.getId());
			// 关注用户备注信息筛选关键字
			String remarkKeyword = leak.getNameC();
			// 欢迎信息
			String first = leak.getNameC() + " ，您好！";
			// 重要程度
			String keyword1 = leak.getScore();
			// 事件简介
			String keyword3 = "请注意防范" + leak.getLeakType();
			// 时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String keyword2 ="";
			if (leak.getEventDate()==null) {
				continue;
			}
			else {
				keyword2 = sdf.format(leak.getEventDate());
			}
			// 备注
			String remark = "漏洞URL " + leak.getUrl();
			// 消息详情地址，没有详情页时可以为空
		   //String date = sdf.format(new Date());
			String namecStr=null;
			namecStr=leak.getNameC();
			if (namecStr==null){
				continue;
			}
			// 对leak.getNameC()学校名进行MD5加密
			else {
				String url = "http://" + getIpAddrFormPro()+ "/w3sp/showScoreLeakList.do?nameC="+ Md5.convertMD5(leak.getNameC());
				if (!"".equals(leak.getNameC())) {
					String sendNamecMsg = "";
					sendNamecMsg = wcHelper.sendTemplateMessage(remarkKeyword, first,keyword1, keyword2, keyword3, remark, url);
					if ("SUC".equals(sendNamecMsg)) {
						savePushMon(leak);
					}
					
					String corpName = getCorpBySchName(remarkKeyword);
					// 推送信息至学校对应的分公司
					 if(!"".equals(corpName)){
					// 推送信息
		   			//if (!"".equals(corpName)) {
		   				String sendMsg="";
		   				sendMsg=wcHelper.sendTemplateMessage(corpName, first, keyword1, keyword2,keyword3, remark, url);
								//}
		   	   			if ("SUC".equals(sendMsg)) {
		   	   		     savePushMon(leak);//将推送信息入库存WEBLEAK_PUSH_MONITOR表 增加分发监控
		   	   			   }
		   		       }
				    }
			
			     }
   	          }
          }    
           /**
            * 6月以内漏洞微信消息推送成功后将存入分发监控表
            * @param leak
            */
           public void savePushMon(WebLeakInfo leak){
        		LeakPushMonitor obj=new LeakPushMonitor();
        		String corpName = getCorpBySchName(leak.getNameC());
   	   		 	obj.setCorpName(corpName);
   	   		 	obj.setNameC(leak.getNameC());
   	   		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   	   		    String date = sdf.format(new Date());
   	   			try {
					obj.setLastTime(sdf.parse(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
   	   			obj.setLeakDetail(leak.getUrl());
   	   			obj.setLeakType(leak.getLeakType());
   	   			obj.setProName("6个月以内高危漏洞推送");
   	   			obj.setStatus("7");
   	   			leakPushMonitorManager.save(obj);
           }
   			
           public WebLeakInfo getWebLeakById(Long leakId){
       		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
       		dc.add(Restrictions.eq("id", leakId));
       		List leak = webLeakInfoDao.getListByDetachedCriteria(dc);
       		
       		if(leak!=null){
       			return (WebLeakInfo) leak.get(0);
       		}
       		return null;
       	}
           public String getIpAddrFormPro(){
       		String DEFAULT_FILENAME = "wechat.properties";
       		Properties prop = new Properties();
       		try {
       			InputStream is = ArticleAction.class.getClassLoader().getResourceAsStream(DEFAULT_FILENAME);
       			if (is == null) {
       				throw new IOException();
       			}
       			prop.load(is);
       		} catch (IOException e) {
       			e.printStackTrace();
       		}
       		 String ipAddr = prop.getProperty("ipAddr");
       		 return ipAddr;
       	}
       		
           /**
            * 通过学校获取分公司名
            * @param schName //学校名
            * @return corpName //分公司名
            * */
           	public String getCorpBySchName(String schName){
           		String corpName = "";
           		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
           		d.add(Restrictions.like("nameC", "%"+schName+"%"));	//未修复
           		List<WebLeakInfo> corpNamelist = webLeakInfoManager.getListByDetachedCriteria(d);
           		if(corpNamelist.size()!=0){
           			corpName = corpNamelist.get(0).getCorpSubName();
           		}
           		return corpName;
           	}
       	/**
    	 * @param args
    	 */
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
	/**
	 * 展示最近六个月以上的漏洞信息
	 * 
	 * 
	 */
	
	public String showScoLeakList(){
		 scoreStr = getScoreMsgByKey("score");
    	 daysStr = getScoreMsgByKey("days");
    	 days=Integer.valueOf(daysStr).intValue();
		String nameC = getRequest().getParameter("nameC");
		nameC = Md5.convertMD5(nameC);//解密
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.like("nameC", "%"+nameC+"%"));
		dc.add(Restrictions.eq("status", "1"));	//未修复的 (0:已处理；1：未修复；2：链接打不开；3：网络不通)
		dc.add(Restrictions.eq("score", scoreStr));//漏洞等级为10的漏洞
		dc.add(Restrictions.eq("leakType", "STRUTS2漏洞"));//由于其他漏洞监测没实现自动化，现只推送STRUTS2漏洞
		try {
			dc.add(Restrictions.lt("eventDate", getStatetime(days)));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}	//最近六个月以上
		List<WebLeakInfo> leak = webLeakInfoDao.getListByDetachedCriteria(dc);
		
		getRequest().setAttribute("leaks", leak);
		
		return SUCCESS;
		
	}
	/**
	 * 展示最近六个月以内的漏洞信息
	 * 
	 * 
	 */
	
	public String showScoreLeakList(){
		scoreStr = getScoreMsgByKey("score");
		daysStr = getScoreMsgByKey("days");
		days=Integer.valueOf(daysStr).intValue();
		String nameC = getRequest().getParameter("nameC");
		nameC = Md5.convertMD5(nameC);//解密
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.like("nameC", "%"+nameC+"%"));
		dc.add(Restrictions.eq("status", "1"));	//未修复的 (0:已处理；1：未修复；2：链接打不开；3：网络不通)
		dc.add(Restrictions.eq("score", scoreStr));//漏洞等级为10的漏洞
		dc.add(Restrictions.eq("leakType", "STRUTS2漏洞"));//由于其他漏洞监测没实现自动化，现只推送STRUTS2漏洞
		try {
			dc.add(Restrictions.ge("eventDate", getStatetime(days)));
		} catch (ParseException e) {
			e.printStackTrace();
		}	//最近六个月以内
		List<WebLeakInfo> leak = webLeakInfoDao.getListByDetachedCriteria(dc);
		
		getRequest().setAttribute("leaks", leak);
		return SUCCESS;
	}
	
}
