package com.cernet.service.impl;

import com.cernet.dao.WebshellHandleDao;
import com.cernet.model.WebshellHandle;
import com.cernet.service.WebshellHandleManager;

public class WebshellHandleManagerImpl extends GenericManagerImpl<WebshellHandle, Long>
		implements WebshellHandleManager {
	private WebshellHandleDao webshellHandleDao;
	public WebshellHandleManagerImpl(WebshellHandleDao webshellHandleDao) {
		super(webshellHandleDao);
		this.webshellHandleDao = webshellHandleDao;
	}
}
