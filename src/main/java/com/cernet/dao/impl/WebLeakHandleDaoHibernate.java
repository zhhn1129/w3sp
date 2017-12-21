package com.cernet.dao.impl;

import com.cernet.dao.WebLeakHandleDao;
import com.cernet.model.WebLeakHandle;

public class WebLeakHandleDaoHibernate extends GenericDaoHibernate<WebLeakHandle, Long>
		implements WebLeakHandleDao {
	
	public WebLeakHandleDaoHibernate() {
		super(WebLeakHandle.class);
	}


}
