package com.cernet.utils.log;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.service.GenericManager;

public interface ActionlogManager extends GenericManager<Actionlog, Long> {

	PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir);

}
