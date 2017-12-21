package com.cernet.service.impl;

import com.cernet.dao.BotnetCorpViewDao;
import com.cernet.model.BotnetCorpView;
import com.cernet.service.BotnetCorpViewManager;

public class BotnetCorpViewManagerImpl extends GenericManagerImpl<BotnetCorpView, Long>
		implements BotnetCorpViewManager {

	private BotnetCorpViewDao botnetCorpViewDao;
	public BotnetCorpViewManagerImpl(BotnetCorpViewDao botnetCorpViewDao) {
		super(botnetCorpViewDao);
		this.botnetCorpViewDao =  botnetCorpViewDao;
	}
}
