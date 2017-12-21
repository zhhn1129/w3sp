package com.cernet.dao.impl;

import com.cernet.dao.BotnetNodeViewDao;
import com.cernet.model.BotnetNodeView;

public class BotnetNodeViewDaoHibernate extends GenericDaoHibernate<BotnetNodeView, Long>
		implements BotnetNodeViewDao {
	public BotnetNodeViewDaoHibernate() {
		super(BotnetNodeView.class);
	}
}
