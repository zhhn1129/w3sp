package com.cernet.dao.impl;

import com.cernet.dao.AxisViewDao;
import com.cernet.model.WebleakAxisView;


public class AxisViewDaoHibernate extends GenericDaoHibernate<WebleakAxisView, String> implements
AxisViewDao {

	public AxisViewDaoHibernate() {
		super(WebleakAxisView.class);
	}

}
