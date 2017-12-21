package com.cernet.service.impl;

import com.cernet.dao.BotnetNodeViewDao;
import com.cernet.model.BotnetNodeView;
import com.cernet.service.BotnetNodeViewManager;

public class BotnetNodeViewManagerImpl extends GenericManagerImpl<BotnetNodeView, Long>
		implements BotnetNodeViewManager {
	private BotnetNodeViewDao botnetNodeViewDao;
	public BotnetNodeViewManagerImpl(BotnetNodeViewDao botnetNodeViewDao) {
		super(botnetNodeViewDao);
		this.botnetNodeViewDao = botnetNodeViewDao;
		
	}
}
