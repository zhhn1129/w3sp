package com.cernet.dao.impl;

import com.cernet.dao.WebshellHandleDao;
import com.cernet.model.WebshellHandle;

public class WebshellHandleDaoHibernate extends GenericDaoHibernate<WebshellHandle, Long>
		implements WebshellHandleDao {
	public WebshellHandleDaoHibernate() {
		super(WebshellHandle.class);
	}

	
}
