package com.cernet.dao.impl;


import com.cernet.dao.WebLeakNViewDao;
import com.cernet.model.WebLeakNView;

public class WebLeakNViewDaoHibernate extends GenericDaoHibernate<WebLeakNView, Long>
		implements WebLeakNViewDao {
	public WebLeakNViewDaoHibernate() {
		super(WebLeakNView.class);
	}

}
