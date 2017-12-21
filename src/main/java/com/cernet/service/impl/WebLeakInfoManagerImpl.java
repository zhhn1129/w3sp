package com.cernet.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.dao.WebLeakInfoDao;
import com.cernet.model.LeakPushMonitor;
import com.cernet.model.WebLeakInfo;
import com.cernet.service.WebLeakInfoManager;

public class WebLeakInfoManagerImpl extends
		GenericManagerImpl<WebLeakInfo, Long> implements WebLeakInfoManager {

	private WebLeakInfoDao webLeakInfoDao;

	public WebLeakInfoManagerImpl(WebLeakInfoDao webLeakInfoDao) {
		super(webLeakInfoDao);
		this.webLeakInfoDao = webLeakInfoDao;
	}

	/**
	 * 中间件cwui
	 */
	@Override
	public PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir) {

		DetachedCriteria queryCriteria = DetachedCriteria
				.forClass(WebLeakInfo.class);

		queryCriteria.addOrder(Order.desc("modTime"));
		int fullSize = webLeakInfoDao.getCountByDetachedCriteria(queryCriteria);
		PaginatedListHibernateImpl plist = new PaginatedListHibernateImpl(page,
				pagesize, fullSize, sort, dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = webLeakInfoDao.getListByDetachedCriteriaPage(
				queryCriteria, (page - 1) * pagesize, pagesize);
		plist.setList(infoList);
		return plist;
	}
}
