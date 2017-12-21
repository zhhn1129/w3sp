package com.cernet.dao.impl;

import java.util.Date;
import java.util.List;

import com.cernet.dao.HandleLastDao;
import com.cernet.model.webleakHandleLast1View;


public class HandleLastDaoDaoHibernate extends GenericDaoHibernate<webleakHandleLast1View, String> implements
HandleLastDao {

	public HandleLastDaoDaoHibernate() {
		super(webleakHandleLast1View.class);
	}

	
	public Date getLastFriday() {
		 String hql = "select friday from webleakHandleLast1View where ROWNUM <= 1";
	     List fridayList = getHibernateTemplate().find(hql);
	     if (fridayList != null && fridayList.size() > 0) {
			return (Date) fridayList.get(0);
		}
	     return null;

	}

}
