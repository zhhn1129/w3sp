package com.cernet.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cernet.dao.GenericDao;
import com.cernet.service.GenericManager;

public class GenericManagerImpl<T, PK extends Serializable> implements
		GenericManager<T, PK> {
	public GenericManagerImpl(GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	protected GenericDao<T, PK> genericDao;

	public void setGenericDao(GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	public List<T> getListByHql(String hql) {
		return genericDao.getListByHql(hql);
	}

	public List<T> getListByDetachedCriteriaPage(DetachedCriteria d,
			int startindex, int numresults) {
		return genericDao.getListByDetachedCriteriaPage(d, startindex,
				numresults);
	}

	public List<T> getListByDetachedCriteria(DetachedCriteria d) {
		return genericDao.getListByDetachedCriteria(d);
	}

	public int getCountByDetachedCriteria(DetachedCriteria d) {
		return genericDao.getCountByDetachedCriteria(d);
	}

	public T get(PK id) {
		return genericDao.get(id);
	}

	public void remove(PK id) {
		genericDao.remove(id);
	}

	public T save(T object) {
		return genericDao.save(object);
	}

}
