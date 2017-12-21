package com.cernet.service.impl;


import com.cernet.dao.AxisViewDao;
import com.cernet.model.WebleakAxisView;
import com.cernet.service.AxisViewManager;


public class AxisViewManagerImpl extends GenericManagerImpl<WebleakAxisView, String> implements
AxisViewManager {
	private AxisViewDao axisViewDao;
	public AxisViewManagerImpl(AxisViewDao axisViewDao) {
		super(axisViewDao);
		this.axisViewDao = axisViewDao;
	}


}
