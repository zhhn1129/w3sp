package com.cernet.service;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.model.LeakPushMonitor;

public interface LeakPushMonitorManager extends GenericManager<LeakPushMonitor, String>{
	/*
	 * 中间件
	 */
	PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir);
}
