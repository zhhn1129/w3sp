package com.cernet.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.dao.LeakColMonitorDao;
import com.cernet.dao.LeakPushMonitorDao;
import com.cernet.model.LeakCmList;
import com.cernet.model.LeakColMonitor;
import com.cernet.model.LeakPmList;
import com.cernet.model.LeakPushMonitor;
import com.cernet.service.LeakColMonitorManager;
import com.cernet.service.LeakPushMonitorManager;
import com.google.gson.Gson;

public class LeakMonitorAction extends BaseAction {
	private LeakColMonitor leakColMonitor;
	private LeakColMonitorDao leakColMonitorDao;
	private LeakColMonitorManager leakColMonitorManager;
	private LeakPushMonitor leakPushMonitor;
	private LeakPushMonitorDao leakPushMonitorDao;
	private LeakPushMonitorManager leakPushMonitorManager;

	public LeakColMonitor getLeakColMonitor() {
		return leakColMonitor;
	}

	public void setLeakColMonitor(LeakColMonitor leakColMonitor) {
		this.leakColMonitor = leakColMonitor;
	}

	public LeakColMonitorDao getLeakColMonitorDao() {
		return leakColMonitorDao;
	}

	public void setLeakColMonitorDao(LeakColMonitorDao leakColMonitorDao) {
		this.leakColMonitorDao = leakColMonitorDao;
	}

	public LeakColMonitorManager getLeakColMonitorManager() {
		return leakColMonitorManager;
	}

	public void setLeakColMonitorManager(
			LeakColMonitorManager leakColMonitorManager) {
		this.leakColMonitorManager = leakColMonitorManager;
	}

	public LeakPushMonitor getLeakPushMonitor() {
		return leakPushMonitor;
	}

	public void setLeakPushMonitor(LeakPushMonitor leakPushMonitor) {
		this.leakPushMonitor = leakPushMonitor;
	}

	public LeakPushMonitorDao getLeakPushMonitorDao() {
		return leakPushMonitorDao;
	}

	public void setLeakPushMonitorDao(LeakPushMonitorDao leakPushMonitorDao) {
		this.leakPushMonitorDao = leakPushMonitorDao;
	}

	public LeakPushMonitorManager getLeakPushMonitorManager() {
		return leakPushMonitorManager;
	}

	public void setLeakPushMonitorManager(
			LeakPushMonitorManager leakPushMonitorManager) {
		this.leakPushMonitorManager = leakPushMonitorManager;
	}
/**
 * 显示数据采集监控
 * @return
 */
	public String leakColMonitorInfo() {

		DetachedCriteria d = DetachedCriteria.forClass(LeakColMonitor.class);
		DetachedCriteria dc = DetachedCriteria.forClass(LeakColMonitor.class);

		dc.setProjection(Projections.distinct(Projections.property("proName")));
		int fullSize = 0;
		List leakColMonList = leakColMonitorManager.getListByDetachedCriteria(dc);
		if (leakColMonList != null) {
			fullSize = leakColMonList.size();
		}
		d.setProjection(Projections.projectionList().add(
				Projections.max("proName"), "proName").add(
				Projections.count("proName"), "totalNum").add(
				Projections.max("lastTime"), "lastTime").add(
				Projections.groupProperty("proName")));
		d.setResultTransformer(Transformers.aliasToBean(LeakCmList.class));
		plist = new PaginatedListHibernateImpl(page, pagesize, fullSize, sort,dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List infoList = leakColMonitorManager.getListByDetachedCriteria(d);
		List<LeakCmList> leakList = new ArrayList<LeakCmList>();
		for (int i = 0; i < infoList.size(); i++) {// 循环遍历proName查出LastTotalCount
			LeakCmList leakCmList = (LeakCmList) infoList.get(i);
			String proName = leakCmList.getProName();
			Date lasttime = leakCmList.getLastTime();
			leakCmList.setLastTotalCount(getCmLastTotalCount(proName, lasttime));// 将查到的总数放入显示model对象中
			if (getCmStatus(proName, lasttime) != null) {
				leakCmList.setStatus(getCmStatus(proName, lasttime));
			}
			if (getCmRunHours(proName, lasttime) != null) {
				leakCmList.setRunHours((getCmRunHours(proName, lasttime)));
			}
			leakList.add(leakCmList);
		}

		plist.setList(leakList);

		return SUCCESS;
	}

	public String getCmStatus(String proName, Date lasttime) {
		DetachedCriteria dc = DetachedCriteria.forClass(LeakColMonitor.class);
		dc.add(Restrictions.eq("proName", proName));
		dc.add(Restrictions.eq("lastTime", lasttime));
		List<LeakColMonitor> resList = leakColMonitorManager.getListByDetachedCriteria(dc);

		if (resList != null) {
			String runCyc = resList.get(0).getStatus();// 运行周期

			long day = getDaySub(new Date(), lasttime);
			System.out.println("当前时间："+day);
			System.out.println("运行周期"+runCyc);
			if (day > Long.parseLong(runCyc)) {// 如果当前时间减去最近更新时间超过运行周期表示系统运行异常
				return "异常";
			} else {
				return "正常";
			}
		}

		return null;
	}

	public String getCmRunHours(String proName, Date lasttime) {
		DetachedCriteria dc = DetachedCriteria.forClass(LeakColMonitor.class);
		dc.add(Restrictions.eq("proName", proName));
		dc.add(Restrictions.eq("lastTime", lasttime));
		List<LeakColMonitor> resList = leakColMonitorManager.getListByDetachedCriteria(dc);

		if (resList != null) {
			String runCyc = resList.get(0).getStatus();// 运行周期
			long day = getDaySub(new Date(), lasttime);
			long hours = getDayHours(new Date(), lasttime);
			String hour = null;
			if (day > Long.parseLong(runCyc)) {// 如果当前时间减去最近更新时间超过运行周期表示系统运行异常
				return hour;
			} else {
				hour = String.valueOf(hours);
				return hour;
			}
		}

		return null;
	}

	public int getCmLastTotalCount(String proName, Date lasttime) {
		DetachedCriteria dtc = DetachedCriteria.forClass(LeakColMonitor.class);
		dtc.add(Restrictions.eq("proName", proName));
		dtc.add(Restrictions.eq("lastTime", lasttime));
		int lasttotalcount = leakColMonitorManager.getCountByDetachedCriteria(dtc);
		return lasttotalcount;
	}

	public int getPmLastTotalCount(String proName, Date lasttime) {
		DetachedCriteria dtc = DetachedCriteria.forClass(LeakPushMonitor.class);
		dtc.add(Restrictions.eq("proName", proName));
		dtc.add(Restrictions.eq("lastTime", lasttime));
		int lasttotalcount = leakPushMonitorManager.getCountByDetachedCriteria(dtc);
		return lasttotalcount;
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(Date beginDate, Date endDate) {
		long day = 0;
		day = (beginDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	public static long getDayHours(Date beginDate, Date endDate) {
		long hours = 0;
		hours = (beginDate.getTime() - endDate.getTime()) / (60 * 60 * 1000);
		return hours;
	}
	/**
	 * 显示漏洞分发监控
	 * @return
	 */
	public String leakPushMonitorInfo() {
		DetachedCriteria d = DetachedCriteria.forClass(LeakPushMonitor.class);
		DetachedCriteria dc = DetachedCriteria.forClass(LeakPushMonitor.class);
		dc.setProjection(Projections.distinct(Projections.property("proName")));
		int fullSize = 0;
		List leakPushMonList = leakPushMonitorManager.getListByDetachedCriteria(dc);
		if (leakPushMonList != null) {
			fullSize = leakPushMonList.size();
		}
		d.setProjection(Projections.projectionList()
				.add(Projections.max("proName"), "proName")
				.add(Projections.count("proName"), "totalNum")
				.add(Projections.max("lastTime"), "lastTime")
				.add(Projections.groupProperty("proName"))
				);
		d.setResultTransformer(Transformers.aliasToBean(LeakPmList.class));
		plist = new PaginatedListHibernateImpl(page, pagesize, fullSize, sort,
				dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = leakPushMonitorManager.getListByDetachedCriteria(d);
		List<LeakPmList> leakList = new ArrayList<LeakPmList>();
		for (int i = 0; i < infoList.size(); i++) {// 循环遍历proName查出LastTotalCount
			LeakPmList leakPmList = (LeakPmList) infoList.get(i);
			String proName = leakPmList.getProName();
			Date lasttime = leakPmList.getLastTime();
			leakPmList.setLastTotalCount(getPmLastTotalCount(proName, lasttime));// 将查到的总数放入显示model对象中
			if (getPmStatus(proName, lasttime) != null) {
				leakPmList.setStatus(getPmStatus(proName, lasttime));
			}
			if (getPmRunHours(proName, lasttime) != null) {
				leakPmList.setRunHours((getPmRunHours(proName, lasttime)));
			}
			leakList.add(leakPmList);
		}

		plist.setList(leakList);

		return SUCCESS;
	}

	public String getPmStatus(String proName, Date lasttime) {
		DetachedCriteria dc = DetachedCriteria.forClass(LeakPushMonitor.class);
		dc.add(Restrictions.eq("proName", proName));
		dc.add(Restrictions.eq("lastTime", lasttime));
		List<LeakPushMonitor> resList = leakPushMonitorManager.getListByDetachedCriteria(dc);

		if (resList != null) {
			String runCyc = resList.get(0).getStatus();// 运行周期

			long day = getDaySub(new Date(), lasttime);
			System.out.println("当前时间："+day);
			System.out.println("运行周期"+lasttime);
			if (day > Long.parseLong(runCyc)) {// 如果当前时间减去最近更新时间超过运行周期表示系统运行异常
				return "异常";
			} else {
				return "正常";
			}
		}

		return null;
	}

	public String getPmRunHours(String proName, Date lasttime) {
		DetachedCriteria dc = DetachedCriteria.forClass(LeakPushMonitor.class);
		dc.add(Restrictions.eq("proName", proName));
		dc.add(Restrictions.eq("lastTime", lasttime));
		List<LeakPushMonitor> resList = leakPushMonitorManager.getListByDetachedCriteria(dc);

		if (resList != null) {
			String runCyc = resList.get(0).getStatus();// 运行周期
			long day = getDaySub(new Date(), lasttime);
			long hours = getDayHours(new Date(), lasttime);
			String hour = null;
			if (day > Long.parseLong(runCyc)) {// 如果当前时间减去最近更新时间超过运行周期表示系统运行异常
				return hour;
			} else {
				hour = String.valueOf(hours);
				return hour;
			}
		}

		return null;
	}
	
	
	String json="";
	

	public String ajaxGetCNVD() throws Exception{
		//String proName = ServletActionContext.getRequest().getParameter("proName");
		//System.out.println(proName);
		DetachedCriteria d = DetachedCriteria.forClass(LeakColMonitor.class);
		d.setProjection(Projections.projectionList()
				.add(Projections.count("proName"), "totalNum"));
		
		d.add(Restrictions.like("proName", "CNVD采集程序"));
		d.setResultTransformer(Transformers.aliasToBean(LeakPmList.class));
		List infoList = leakColMonitorManager.getListByDetachedCriteria(d);
		/*int s = infoList.size();
		System.out.println(s);*/
		Gson gson = new Gson();

		for (Object object : infoList) {
			this.json += gson.toJson(object);
		}
		//System.out.println("json:" + json);
		ServletActionContext.getResponse().getWriter().print(json);
		return null;
	}
	public String ajaxGetSH() throws Exception{
		//String proName=ServletActionContext.getRequest().getParameter("proName");
		//System.out.println(proName);
		DetachedCriteria d = DetachedCriteria.forClass(LeakColMonitor.class);
		d.setProjection(Projections.projectionList()
		.add(Projections.count("proName"), "totalNum"));
		d.add(Restrictions.like("proName", "上海交大"));
		d.setResultTransformer(Transformers.aliasToBean(LeakPmList.class));
		List infoList = leakColMonitorManager.getListByDetachedCriteria(d);
//		int s=infoList.size();
		//System.out.println(s);
		Gson gson=new Gson();
		
		for (Object object : infoList) {
			this.json+=gson.toJson(object);
		}
		//System.out.println("json:"+json);
		ServletActionContext.getResponse().getWriter().print(json);
		
		return null;
	}
	
	
	public String ajaxGetPushCNVD() throws Exception{
		//String proName=ServletActionContext.getRequest().getParameter("proName");
		//System.out.println(proName);
		DetachedCriteria d = DetachedCriteria.forClass(LeakPushMonitor.class);
		d.setProjection(Projections.projectionList()
		.add(Projections.count("proName"), "totalNum"));
		d.add(Restrictions.like("proName", "CNVD采集程序"));
		d.setResultTransformer(Transformers.aliasToBean(LeakPmList.class));
		List infoList = leakColMonitorManager.getListByDetachedCriteria(d);
//		int s=infoList.size();
		//System.out.println(s);
		Gson gson=new Gson();
		
		for (Object object : infoList) {
			this.json+=gson.toJson(object);
		}
		//System.out.println("json:"+json);
		ServletActionContext.getResponse().getWriter().print(json);
		
		return null;
	}
	
}
