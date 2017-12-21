package com.cernet.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.dao.GenericDao;
import com.cernet.dao.HighPushDao;
import com.cernet.model.HighPush;
import com.cernet.service.HighPushManager;
import com.cernet.utils.log.Actionlog;

public class HighPushManagerImpl extends GenericManagerImpl<HighPush, String> implements HighPushManager {
    private HighPushDao highPushDao;
	public HighPushManagerImpl(HighPushDao highPushDao) {
		super(highPushDao);
		this.highPushDao=highPushDao;
	}
	/**
	 * 中间件cwui
	 */
	@Override
	public PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir) {
		
DetachedCriteria queryCriteria = DetachedCriteria.forClass(HighPush.class);

		
		queryCriteria.addOrder(Order.desc("createTime"));
		int fullSize = highPushDao.getCountByDetachedCriteria(queryCriteria);
		PaginatedListHibernateImpl plist = new PaginatedListHibernateImpl(page,
				pagesize, fullSize, sort, dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = highPushDao.getListByDetachedCriteriaPage(queryCriteria,
				(page - 1) * pagesize, pagesize);
		plist.setList(infoList);
		return plist;
	}

}
