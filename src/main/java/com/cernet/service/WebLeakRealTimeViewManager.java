package com.cernet.service;

import java.util.Map;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.model.WebLeakRealTimeView;

public interface WebLeakRealTimeViewManager extends GenericManager<WebLeakRealTimeView, Long> {

	PaginatedListHibernateImpl getListByDetachedCriteriaPage(Map<String, String> map,int page,
			int pagesize, String sort, String dir);
}
