package com.cernet.service.impl;

import com.cernet.dao.WebshellNodeViewDao;
import com.cernet.model.WebshellNodeView;
import com.cernet.service.WebshellNodeViewManager;

public class WebshellNodeViewManagerImpl extends GenericManagerImpl<WebshellNodeView, Long>
		implements WebshellNodeViewManager {

	private WebshellNodeViewDao webshellNodeViewDao;
	
	public WebshellNodeViewManagerImpl(WebshellNodeViewDao webshellNodeViewDao) {
		super(webshellNodeViewDao);
		this.webshellNodeViewDao = webshellNodeViewDao;
	}
}
