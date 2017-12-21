package com.cernet.service;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.model.WebLeakInfo;

public interface WebLeakInfoManager extends GenericManager<WebLeakInfo, Long> {
	/*
	 * 中间件
	 */
	PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir);
}
