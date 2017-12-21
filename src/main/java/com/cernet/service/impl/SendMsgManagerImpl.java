package com.cernet.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.expression.ParseException;
import com.cernet.dao.LeakPushMonitorDao;
import com.cernet.dao.WebLeakInfoDao;
import com.cernet.model.LeakPushMonitor;
import com.cernet.model.WebLeakInfo;
import com.cernet.service.SendMsgManager;
import com.cernet.util.Md5;
import com.cernet.web.action.ArticleAction;
import com.cernet.wechat.WechatHelper;


public class SendMsgManagerImpl implements SendMsgManager {


	private WebLeakInfoDao webLeakInfoDao;
	private LeakPushMonitorDao leakPushMonitorDao;
	
	public LeakPushMonitorDao getLeakPushMonitorDao() {
		return leakPushMonitorDao;
	}

	public void setLeakPushMonitorDao(LeakPushMonitorDao leakPushMonitorDao) {
		this.leakPushMonitorDao = leakPushMonitorDao;
	}

	public WebLeakInfoDao getWebLeakInfoDao() {
		return webLeakInfoDao;
	}

	public void setWebLeakInfoDao(WebLeakInfoDao webLeakInfoDao) {
		this.webLeakInfoDao = webLeakInfoDao;
	}

	@Override
	public void sendMsg() {
		WechatHelper wcHelper = new WechatHelper();
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.add(Restrictions.eq("status", "1"));	//未修复
		d.add(Restrictions.ge("modTime", getStatetime(-7)));	//最近一周
		d.setProjection(Projections.projectionList()  
		        .add(Projections.min("id"),"id")
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
			String keyword2 = "";
			if (leak.getEventDate()==null) {
				continue;
			}
			else {
				keyword2=sdf.format(leak.getEventDate());
			}
			//备注
			String remark = "漏洞URL "+leak.getUrl();
			//消息详情地址，没有详情页时可以为空
			ArticleAction articleAction = new ArticleAction();
			String namecStr=null;
			namecStr=leak.getNameC();
			if (namecStr==null){
				continue;
			}else {
			    String url = "http://"+articleAction.getIpAddrFormPro()+"/w3sp/showLeakList.do?nameC="+Md5.convertMD5(leak.getNameC());
			  //推送信息
				if (!"".equals(remarkKeyword)) {
					String sendNamecMsg="";
					sendNamecMsg=wcHelper.sendTemplateMessage(remarkKeyword, first, keyword1, keyword2,keyword3, remark, url);
				  if ("SUC".equals(sendNamecMsg)) {
					  savePushMonitor(leak);
				}
			}
				//推送信息至学校对应的分公司
				String corpName = getCorpBySchName(remarkKeyword);
				if(!"".equals(corpName)){
					String sendMsg = "";
					//推送信息
					sendMsg = wcHelper.sendTemplateMessage(corpName, first, keyword1, keyword2,keyword3, remark, url);
						if("SUC".equals(sendMsg)){
						savePushMonitor(leak);
					}
				}
				System.out.println("定时任务每周一次推送信息成功！");
			}
		}
	}
	/**
	 * 推送成功将入库推送监控表
	 */
	public void savePushMonitor(WebLeakInfo leak){
		LeakPushMonitor obj = new LeakPushMonitor();
		obj.setNameC(leak.getNameC());
		String corpName = getCorpBySchName(leak.getNameC());
		obj.setCorpName(corpName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String dateStr=sdf.format(date);
		try {
			obj.setLastTime(sdf.parse(dateStr));
		} catch (java.text.ParseException e) {
			
			e.printStackTrace();
		}
		obj.setLeakDetail(leak.getUrl());
		obj.setLeakType(leak.getLeakType());
		obj.setProName("每周推送全部漏洞定时任务");
		obj.setStatus("7");
	    leakPushMonitorDao.save(obj);
	}
	/*
	 * 通过学校获取分公司名
	 * @param schName //学校名
	 * @return corpName //分公司名
	 * */
		public String getCorpBySchName(String schName){
			
			String corpName = "";
			DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
			d.add(Restrictions.like("nameC", "%"+schName+"%"));	//未修复
			List<WebLeakInfo> corpNamelist = webLeakInfoDao.getListByDetachedCriteria(d);
			if(corpNamelist.size()!=0){
				corpName = corpNamelist.get(0).getCorpSubName();
			}
			return corpName;
		}
	
	
	public Date getStatetime(int num) throws ParseException{
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		       
		        Calendar c = Calendar.getInstance();  
		        c.add(Calendar.DATE, num);  
		        Date monday = c.getTime();
		        String preMonday = sdf.format(monday);
		        
		        try {
		        	System.out.println("获得时间："+sdf.parse(preMonday));
					return sdf.parse(preMonday);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
					return null;
				}
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
	
}
