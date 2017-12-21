package com.cernet.dao;

import java.util.List;
import java.util.Map;

import com.cernet.model.WebLeak;
import com.opensymphony.xwork2.inject.util.FinalizablePhantomReference;

public interface WebLeakDao extends GenericDao<WebLeak, Long> {

	
//	public List getWebLeakModelList(Map<String, Object> paraMap);
	
	
//	public List getWebLeakModelListByCorpName( final String cormName);
	
	
	public List getWebLeakModelListByNameC(final String nameC);
}
