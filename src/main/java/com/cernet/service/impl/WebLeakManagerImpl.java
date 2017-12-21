package com.cernet.service.impl;

import java.util.List;
import java.util.Map;

import com.cernet.dao.WebLeakDao;
import com.cernet.model.WebLeak;
import com.cernet.service.WebLeakManager;

public class WebLeakManagerImpl extends GenericManagerImpl<WebLeak, Long>
		implements WebLeakManager {

	
	private WebLeakDao webLeakDao;
	
	public WebLeakManagerImpl(WebLeakDao webLeakDao) {
		super(webLeakDao);
		this.webLeakDao = webLeakDao;
	}

//	public List getWebLeakModelList(Map<String, Object> paraMap) {
//		
//		return webLeakDao.getWebLeakModelList(paraMap);
//	}

//	public List getWebLeakModelListByCorpName(String cormName) {
//	
//		return webLeakDao.getWebLeakModelListByCorpName(cormName);
//	}

	public List getWebLeakModelListByNameC(String nameC) {
		
		return webLeakDao.getWebLeakModelListByNameC(nameC);
	}

}
