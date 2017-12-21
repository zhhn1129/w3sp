package com.cernet.dao.impl;

import com.cernet.dao.WebLeakRealTimeViewDao;
import com.cernet.model.WebLeakRealTimeView;

public class WebLeakRealTimeViewDaoHibernate extends GenericDaoHibernate<WebLeakRealTimeView, Long>
		implements WebLeakRealTimeViewDao {

	public WebLeakRealTimeViewDaoHibernate() {
		super(WebLeakRealTimeView.class);
	}

}
