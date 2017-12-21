package com.cernet.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cernet.dao.GenericDao;

public class GenericDaoHibernate<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDao<T, PK> {

	private Class<T> persistentClass;

	protected final Log log = LogFactory.getLog(GenericDaoHibernate.class);

	public GenericDaoHibernate() {
	}

	/**
	 * Constructor that takes in a class to see which type of entity to persist
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericDaoHibernate(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public List<T> getListByHql(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 查询
	 * 
	 * @param d
	 *            查询条件
	 * @param startindex
	 *            记录开始
	 * @param numresults
	 *            总条数
	 * @return
	 */
	public List<T> getListByDetachedCriteriaPage(DetachedCriteria d,
			int startindex, int numresults) {
		return super.getHibernateTemplate().findByCriteria(d, startindex,
				numresults);
	}

	/**
	 * 查询，不分页
	 * 
	 * @param d
	 * @return
	 */
	public List<T> getListByDetachedCriteria(DetachedCriteria d) {
		return getHibernateTemplate().findByCriteria(d);
	}

	public int getCountByDetachedCriteria(DetachedCriteria d) {
		DetachedCriteria dCopy = (DetachedCriteria) SerializationUtils.clone(d);
		return Integer.parseInt(super.getHibernateTemplate().findByCriteria(
				dCopy.setProjection(Projections.projectionList().add(
						Projections.rowCount()))).get(0).toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		T entity = (T) getHibernateTemplate().get(this.persistentClass, id);

		if (entity == null) {
			log.warn("Uh oh, '" + this.persistentClass + "' object with id '"
					+ id + "' not found...");
			throw new ObjectRetrievalFailureException(this.persistentClass, id);
		}

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id) {
		getHibernateTemplate().delete(this.get(id));
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T save(T object) {
		return (T) getHibernateTemplate().merge(object);
	}
}
