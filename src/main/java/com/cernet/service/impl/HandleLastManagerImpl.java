package com.cernet.service.impl;


import java.util.Date;
import java.util.List;

import com.cernet.dao.HandleLastDao;
import com.cernet.model.webleakHandleLast1View;
import com.cernet.service.HandleLastManager;


public class HandleLastManagerImpl extends GenericManagerImpl<webleakHandleLast1View, String> implements
HandleLastManager {
	private HandleLastDao handleLastDao;
	public HandleLastManagerImpl(HandleLastDao handleLastDao) {
		super(handleLastDao);
		this.handleLastDao = handleLastDao;
	}
	public Date getLastFriday() {
		
		return handleLastDao.getLastFriday();
	}


}
