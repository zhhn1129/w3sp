package com.cernet.service;

import java.util.List;
import java.util.Map;

import com.cernet.model.WebLeak;

public interface WebLeakManager extends GenericManager<WebLeak, Long> {

//	public List getWebLeakModelList(Map<String, Object> paraMap);
	
//	public List getWebLeakModelListByCorpName(final String cormName);
	
	public List getWebLeakModelListByNameC(final String nameC);
}
