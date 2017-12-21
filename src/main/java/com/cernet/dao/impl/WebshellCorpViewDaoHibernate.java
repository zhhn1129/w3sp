package com.cernet.dao.impl;

import com.cernet.dao.WebshellCorpViewDao;
import com.cernet.model.WebshellCorpView;

public class WebshellCorpViewDaoHibernate extends GenericDaoHibernate<WebshellCorpView, Long>
		implements WebshellCorpViewDao {
	public WebshellCorpViewDaoHibernate() {
		super(WebshellCorpView.class);
	}
}
