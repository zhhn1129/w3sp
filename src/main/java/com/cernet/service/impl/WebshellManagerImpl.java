package com.cernet.service.impl;

import com.cernet.dao.WebshellDao;
import com.cernet.model.Webshell;
import com.cernet.service.WebshellManager;

public class WebshellManagerImpl extends GenericManagerImpl<Webshell, Long> implements
		WebshellManager {
	private WebshellDao webshellDao;
	public WebshellManagerImpl(WebshellDao webshellDao) {
		super(webshellDao);
		this.webshellDao = webshellDao;
	}
	

}
