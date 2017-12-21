package com.cernet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface GenericDao<T, PK extends Serializable> {
	public List<T> getListByHql(String hql);

	public List<T> getListByDetachedCriteriaPage(DetachedCriteria d,
			int startindex, int numresults);

	public List<T> getListByDetachedCriteria(DetachedCriteria d);

	public int getCountByDetachedCriteria(DetachedCriteria d);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to get
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	public T get(PK id);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param object
	 *            the object to save
	 * @return the updated object
	 */
	public T save(T object);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param id
	 *            the identifier (primary key) of the object to remove
	 */
	public void remove(PK id);
}
