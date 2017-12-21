package com.cernet.dao.impl;

import com.cernet.dao.WebLeakCViewDao;
import com.cernet.model.WebLeakCView;

public class WebLeakCViewDaoHibernate extends GenericDaoHibernate<WebLeakCView, Long>
		implements WebLeakCViewDao {

	public WebLeakCViewDaoHibernate() {
		super(WebLeakCView.class);
	}
}
