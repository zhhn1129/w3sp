package com.cernet.service.impl;

import com.cernet.dao.WebshellViewDao;
import com.cernet.model.WebshellView;
import com.cernet.service.WebshellViewManager;

public class WebshellViewManagerImpl extends GenericManagerImpl<WebshellView, Long>
		implements WebshellViewManager {
	private WebshellViewDao webshellViewDao;
	
	public WebshellViewManagerImpl(WebshellViewDao webshellViewDao) {
		super(webshellViewDao);
		this.webshellViewDao = webshellViewDao;
		
	}

	
}
