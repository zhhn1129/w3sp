package com.cernet.service.impl;

import com.cernet.dao.WebLeakHandleDao;
import com.cernet.model.WebLeakHandle;
import com.cernet.service.WebLeakHandleManager;

public class WebLeakHandleManagerImpl extends GenericManagerImpl<WebLeakHandle, Long>
		implements WebLeakHandleManager {
	
	private WebLeakHandleDao webLeakHandleDao;
	public WebLeakHandleManagerImpl(WebLeakHandleDao webLeakHandleDao) {
		super(webLeakHandleDao);
		this.webLeakHandleDao = webLeakHandleDao;
	}
	
}
