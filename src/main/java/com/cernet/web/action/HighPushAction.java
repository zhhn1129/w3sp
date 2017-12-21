package com.cernet.web.action;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cernet.dao.HighPushDao;
import com.cernet.dao.LeakColMonitorDao;
import com.cernet.model.HighPush;
import com.cernet.model.LeakColMonitor;
import com.cernet.service.HighPushManager;
import com.cernet.service.LeakColMonitorManager;

public class HighPushAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HighPush highPush;
	private HighPushDao highPushDao;
	private HighPushManager highPushManager;
	private LeakColMonitor leakColMonitor;
	private LeakColMonitorDao leakColMonitorDao;
	private LeakColMonitorManager leakColMonitorManager;

	public HighPush getHighPush() {
		return highPush;
	}

	public void setHighPush(HighPush highPush) {
		this.highPush = highPush;
	}

	public HighPushDao getHighPushDao() {
		return highPushDao;
	}

	public void setHighPushDao(HighPushDao highPushDao) {
		this.highPushDao = highPushDao;
	}

	public HighPushManager getHighPushManager() {
		return highPushManager;
	}

	public void setHighPushManager(HighPushManager highPushManager) {
		this.highPushManager = highPushManager;
	}

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

	/*
	 * 条件查询插入单条数据 入W3SP_WEBLEAK_HIGHTPUSH表
	 */
	public void saveLeakMsg(HighPush highPush) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(HighPush.class);
		dCriteria.add(Restrictions.eq("createTime", highPush.getCreateTime()))
				.add(Restrictions.eq("url", highPush.getUrl()));
		System.out.println(highPush.getCreateTime());
		List<HighPush> result = highPushManager
				.getListByDetachedCriteria(dCriteria);
		if (result.size() == 0) {
			highPushManager.save(highPush);
		}

	}

	/*
	 * 批量存数据入W3SP_WEBLEAK_HIGHTPUSH表，WEBLEAK_COLLECT_MONITOR表
	 */
	public void saveHighPush() throws IOException {
		GetCnvdMsgAction cnvdMsgAction = new GetCnvdMsgAction();
		List<HighPush> highPushList = cnvdMsgAction.getLeakMsgByCnvd();
		HighPush highPush = new HighPush();
		LeakColMonitor leakColMonitor = new LeakColMonitor();
		if (highPushList.size() != 0) {
			String[] leakTypes = cnvdMsgAction.getCnvdByKey("leakType").split(
					",");
			for (int i = 0; i < highPushList.size(); i++) {
				highPush = highPushList.get(i);
				for (int j = 0; j < leakTypes.length; j++) {
					if (highPush.getLeakType().indexOf(leakTypes[j]) != -1) {
						String leakSource = highPush.getLeakSource();
						leakColMonitor.setProName(leakSource);
						String url = highPush.getUrl();
						leakColMonitor.setLeakDetail(url);
						Date createTime = highPush.getCreateTime();
						leakColMonitor.setLastTime(createTime);
						leakColMonitor.setStatus("1");
						String leakType = highPush.getLeakType();
						leakColMonitor.setLeakType(leakType);
						saveLeakMsg(highPush);
						saveLeakCmMsg(leakColMonitor);
					}
				}
			}
			// getResponse().getWriter().write("Success!");
			// getResponse().getWriter().close();
		} else {
			leakColMonitor.setProName("CNVD采集程序");
			leakColMonitor.setLeakDetail("空");
			try {

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String StrDate = dateFormat.format(date);
				leakColMonitor.setLastTime(dateFormat.parse(StrDate));
				System.out.println("时间是:" + leakColMonitor.getLastTime());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			leakColMonitor.setStatus("1");
			leakColMonitor.setLeakType("空");
			saveErrorLeakCmMsg(leakColMonitor);
			// getResponse().getWriter().write("Success!");
			// getResponse().getWriter().close();
		}

	}

	/*
	 * 条件查询插入单条数据 入WEBLEAK_COLLECT_MONITOR表
	 */
	public void saveLeakCmMsg(LeakColMonitor leakColMonitor) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(LeakColMonitor.class);
		dCriteria
				.add(Restrictions.eq("lastTime", leakColMonitor.getLastTime()))
				.add(Restrictions.eq("leakDetail", leakColMonitor
								.getLeakDetail()));
		System.out.println(leakColMonitor.getLastTime());
		List<LeakColMonitor> result = leakColMonitorManager
				.getListByDetachedCriteria(dCriteria);
		if (result.size() == 0) {
			leakColMonitorManager.save(leakColMonitor);
		}

	}

	/*
	  * 
	  */
	public void saveErrorLeakCmMsg(LeakColMonitor leakColMonitor) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(LeakColMonitor.class);
		dCriteria
				.add(Restrictions.eq("lastTime", leakColMonitor.getLastTime()))
				.add(Restrictions.eq("leakDetail", "空"));
		System.out.println(leakColMonitor.getLastTime());
		List<LeakColMonitor> result = leakColMonitorManager
				.getListByDetachedCriteria(dCriteria);
		if (result.size() == 0) {
			leakColMonitorManager.save(leakColMonitor);
		}

	}
}