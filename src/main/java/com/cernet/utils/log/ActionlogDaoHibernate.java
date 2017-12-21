package com.cernet.utils.log;

import com.cernet.dao.impl.GenericDaoHibernate;

public class ActionlogDaoHibernate extends GenericDaoHibernate<Actionlog, Long>
	implements ActionlogDao{
	public ActionlogDaoHibernate() {
		super(Actionlog.class);
	}

}
