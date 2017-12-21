package com.cernet.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.dao.WebLeakRealTimeViewDao;
import com.cernet.model.WebLeakRealTimeView;
import com.cernet.service.WebLeakRealTimeViewManager;

public class WebLeakRealTimeViewManagerImpl extends GenericManagerImpl<WebLeakRealTimeView, Long>
		implements WebLeakRealTimeViewManager {
	private WebLeakRealTimeViewDao webLeakRealTimeViewDao;
	public WebLeakRealTimeViewManagerImpl(WebLeakRealTimeViewDao webLeakRealTimeViewDao) {
		super(webLeakRealTimeViewDao);
		this.webLeakRealTimeViewDao = webLeakRealTimeViewDao;
	}
	
	public PaginatedListHibernateImpl getListByDetachedCriteriaPage(Map<String, String> fieldsMap,int page,
			int pagesize, String sort, String dir) {
		DetachedCriteria queryCriteria = DetachedCriteria.forClass(WebLeakRealTimeView.class);

		if (dir == null) {
			queryCriteria.addOrder(Order.desc("eventDate"));
				}
		if(fieldsMap.get("url")!=null){
			queryCriteria.add(Restrictions.like("url", "%"+fieldsMap.get("url").trim()+"%"));
		}
		if(fieldsMap.get("nameC")!=null){
			queryCriteria.add(Restrictions.like("nameC", "%"+fieldsMap.get("nameC").trim()+"%"));
		}
		if(fieldsMap.get("corpSubName")!=null){
			queryCriteria.add(Restrictions.like("corpSubName", "%"+fieldsMap.get("corpSubName").trim()+"%"));
		}
		if(fieldsMap.get("leakType")!=null){
			queryCriteria.add(Restrictions.like("leakType", "%"+fieldsMap.get("leakType").trim()+"%"));
		}
		if(fieldsMap.get("status")!=null){
			queryCriteria.add(Restrictions.like("status", "%"+fieldsMap.get("status").trim()+"%"));
		}
		if(fieldsMap.get("prodName")!=null){
			queryCriteria.add(Restrictions.like("prodName", "%"+fieldsMap.get("prodName").trim()+"%"));
		}
		if(fieldsMap.get("softName")!=null){
			queryCriteria.add(Restrictions.like("softName", "%"+fieldsMap.get("softName").trim()+"%"));
		}
		if(fieldsMap.get("score")!=null){
			queryCriteria.add(Restrictions.eq("score", fieldsMap.get("score")));
		}
		try {
			if(fieldsMap.get("eventDate")!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				java.util.Date eventDate = sdf.parse(fieldsMap.get("eventDate"));  
				queryCriteria.add(Restrictions.eq("eventDate",eventDate));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int fullSize = webLeakRealTimeViewDao.getCountByDetachedCriteria(queryCriteria);
		PaginatedListHibernateImpl plist = new PaginatedListHibernateImpl(page,
				pagesize, fullSize, sort, dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(queryCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = webLeakRealTimeViewDao.getListByDetachedCriteriaPage(queryCriteria,
				(page - 1) * pagesize, pagesize);
		plist.setList(infoList);
		return plist;

	}
	
}
