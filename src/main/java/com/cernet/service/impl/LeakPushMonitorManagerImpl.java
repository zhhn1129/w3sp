package com.cernet.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.dao.LeakPushMonitorDao;
import com.cernet.model.LeakPushMonitor;
import com.cernet.service.LeakPushMonitorManager;

public class LeakPushMonitorManagerImpl extends GenericManagerImpl<LeakPushMonitor, String> implements LeakPushMonitorManager {
    private LeakPushMonitorDao leakPushMonitorDao;
	public LeakPushMonitorManagerImpl(LeakPushMonitorDao leakPushMonitorDao) {
		super(leakPushMonitorDao);
		this.leakPushMonitorDao=leakPushMonitorDao;
	}
	/**
	 * 中间件cwui
	 */
	@Override
	public PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir) {
		
DetachedCriteria queryCriteria = DetachedCriteria.forClass(LeakPushMonitor.class);

		
		queryCriteria.addOrder(Order.desc("lastTime"));
		int fullSize = leakPushMonitorDao.getCountByDetachedCriteria(queryCriteria);
		PaginatedListHibernateImpl plist = new PaginatedListHibernateImpl(page,
				pagesize, fullSize, sort, dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = leakPushMonitorDao.getListByDetachedCriteriaPage(queryCriteria,
				(page - 1) * pagesize, pagesize);
		plist.setList(infoList);
		return plist;
	}

}
