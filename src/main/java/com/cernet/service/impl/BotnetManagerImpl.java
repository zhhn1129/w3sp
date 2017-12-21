package com.cernet.service.impl;

import com.cernet.dao.BotnetDao;
import com.cernet.model.Botnet;
import com.cernet.service.BotnetManager;

public class BotnetManagerImpl extends GenericManagerImpl<Botnet, Long> implements
		BotnetManager {
	
	private BotnetDao botnetDao;
	public BotnetManagerImpl(BotnetDao botnetDao) {
		super(botnetDao);
		this.botnetDao = botnetDao;
		
	}
}
