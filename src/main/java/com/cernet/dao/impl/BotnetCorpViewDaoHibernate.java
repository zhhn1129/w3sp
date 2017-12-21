package com.cernet.dao.impl;

import com.cernet.dao.BotnetCorpViewDao;
import com.cernet.model.BotnetCorpView;

public class BotnetCorpViewDaoHibernate extends GenericDaoHibernate<BotnetCorpView, Long>
		implements BotnetCorpViewDao {

	public BotnetCorpViewDaoHibernate() {
		super(BotnetCorpView.class);
	}
}
