package com.cernet.service.impl;

import com.cernet.dao.WebLeakCViewDao;
import com.cernet.model.WebLeakCView;
import com.cernet.service.WebLeakCViewManager;

public class WebLeakCViewManagerImpl extends GenericManagerImpl<WebLeakCView, Long>
		implements WebLeakCViewManager {
	private WebLeakCViewDao webLeakCViewDao;
	
	public WebLeakCViewManagerImpl(WebLeakCViewDao webLeakCViewDao) {
		super(webLeakCViewDao);
		this.webLeakCViewDao = webLeakCViewDao;
	}

	
}
