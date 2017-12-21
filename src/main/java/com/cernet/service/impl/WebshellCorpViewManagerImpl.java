package com.cernet.service.impl;

import com.cernet.dao.WebshellCorpViewDao;
import com.cernet.model.WebshellCorpView;
import com.cernet.service.WebshellCorpViewManager;

public class WebshellCorpViewManagerImpl extends GenericManagerImpl<WebshellCorpView, Long>
		implements WebshellCorpViewManager {

	private WebshellCorpViewDao webshellCorpViewDao;
	
	public WebshellCorpViewManagerImpl(WebshellCorpViewDao webshellCorpViewDao) {
		super(webshellCorpViewDao);
		this.webshellCorpViewDao = webshellCorpViewDao;
	}
	
	
}
