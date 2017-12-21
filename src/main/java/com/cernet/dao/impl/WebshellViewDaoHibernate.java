package com.cernet.dao.impl;

import com.cernet.dao.WebshellViewDao;
import com.cernet.model.WebshellView;

public class WebshellViewDaoHibernate extends GenericDaoHibernate<WebshellView, Long>
		implements WebshellViewDao {

	public WebshellViewDaoHibernate() {
		super(WebshellView.class);
	}

}
