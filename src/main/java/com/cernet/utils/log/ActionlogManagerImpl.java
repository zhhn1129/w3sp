package com.cernet.utils.log;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.service.impl.GenericManagerImpl;

public class ActionlogManagerImpl extends GenericManagerImpl<Actionlog, Long> implements
		ActionlogManager {
	private ActionlogDao actionlogDao;
	public ActionlogManagerImpl(ActionlogDao actionlogDao) {
		super(actionlogDao);
		this.actionlogDao = actionlogDao;
	}
	@Override 
	public PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir) {
		
		DetachedCriteria queryCriteria = DetachedCriteria.forClass(Actionlog.class);

		//queryCriteria.add(Restrictions.eq("username", "定时任务"));
		
		//queryCriteria.addOrder(Order.desc("time"));
		int fullSize = actionlogDao.getCountByDetachedCriteria(queryCriteria);
		PaginatedListHibernateImpl plist = new PaginatedListHibernateImpl(page,
				pagesize, fullSize, sort, dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = actionlogDao.getListByDetachedCriteriaPage(queryCriteria,
				(page - 1) * pagesize, pagesize);
		plist.setList(infoList);
		return plist;
		
	}
	

}
