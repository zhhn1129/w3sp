package com.cernet.service.impl;

import com.cernet.dao.WebLeakNViewDao;
import com.cernet.model.WebLeakNView;
import com.cernet.service.WebLeakNViewManager;

public class WebLeakNViewManagerImpl extends GenericManagerImpl<WebLeakNView, Long>
		implements WebLeakNViewManager {
	private WebLeakNViewDao webLeakNViewDao;
	public WebLeakNViewManagerImpl(WebLeakNViewDao webLeakNViewDao) {
		super(webLeakNViewDao);
		this.webLeakNViewDao = webLeakNViewDao;
	}

	
}
