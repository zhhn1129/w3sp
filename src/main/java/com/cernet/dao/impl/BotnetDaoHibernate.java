package com.cernet.dao.impl;

import com.cernet.dao.BotnetDao;
import com.cernet.model.Botnet;

public class BotnetDaoHibernate extends GenericDaoHibernate<Botnet, Long> implements
		BotnetDao {
	public BotnetDaoHibernate() {
		super(Botnet.class);
	}

	
}
