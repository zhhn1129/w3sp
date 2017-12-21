package com.cernet.dao.impl;

import com.cernet.dao.WebshellNodeViewDao;
import com.cernet.model.WebshellNodeView;

public class WebshellNodeViewDaoHibernate extends GenericDaoHibernate<WebshellNodeView, Long>
		implements WebshellNodeViewDao {

	public WebshellNodeViewDaoHibernate() {
		super(WebshellNodeView.class);
	}
}
