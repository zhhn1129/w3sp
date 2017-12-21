package com.cernet.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.dao.LeakColMonitorDao;
import com.cernet.model.LeakCmList;
import com.cernet.model.LeakColMonitor;
import com.cernet.service.LeakColMonitorManager;

public class LeakColMonitorManagerImpl extends GenericManagerImpl<LeakColMonitor, String> implements LeakColMonitorManager {
    private LeakColMonitorDao leakColMonitorDao;
	public LeakColMonitorManagerImpl(LeakColMonitorDao leakColMonitorDao) {
		super(leakColMonitorDao);
		this.leakColMonitorDao=leakColMonitorDao;
	}
	/**
	 * 中间件cwui
	 */
	@Override
	public PaginatedListHibernateImpl getListByDetachedCriteriaPage(int page,
			int pagesize, String sort, String dir) {
		
		DetachedCriteria d = DetachedCriteria.forClass(LeakColMonitor.class);
		DetachedCriteria dc = DetachedCriteria.forClass(LeakColMonitor.class);

		dc.setProjection(Projections.distinct(Projections.property("proName")));  
		int fullSize = 0;
		List leakColMonList = leakColMonitorDao.getListByDetachedCriteria(dc);
		if(leakColMonList!=null){
			fullSize = leakColMonList.size();
		}
		       d.setProjection(Projections.projectionList()
				.add(Projections.max("proName"),"proName")
				.add(Projections.count("proName"),"totalNum")
		        .add(Projections.max("lastTime"),"lastTime")
		        .add(Projections.groupProperty("proName"))
		        ); 
	    d.setResultTransformer(Transformers.aliasToBean(LeakCmList.class));
		PaginatedListHibernateImpl plist = new PaginatedListHibernateImpl(page,pagesize, fullSize, sort, dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		List infoList = leakColMonitorDao.getListByDetachedCriteria(d);
		List<LeakCmList> leakList = new ArrayList<LeakCmList>();
	    for (int i = 0; i < infoList.size(); i++) {//循环遍历proName查出LastTotalCount
	    	LeakCmList leakCmList= (LeakCmList)infoList.get(i);
	    	String proName = leakCmList.getProName();
	    	Date lasttime = leakCmList.getLastTime();
	    	DetachedCriteria dtc = DetachedCriteria.forClass(LeakColMonitor.class);
	    		dtc.add(Restrictions.eq("proName", proName));
	    		dtc.add(Restrictions.eq("lastTime", lasttime));
	    	int lasttotalcount = leakColMonitorDao.getCountByDetachedCriteria(dtc);
	    	
	    	leakCmList.setLastTotalCount(lasttotalcount);//将查到的总数放入显示model对象中
	    	leakList.add(leakCmList);
		}
			
		plist.setList(leakList);
		return plist;
	}

}
