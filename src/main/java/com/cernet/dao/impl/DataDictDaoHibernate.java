package com.cernet.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cernet.dao.DataDictDao;
import com.cernet.model.DataDict;

public class DataDictDaoHibernate extends GenericDaoHibernate<DataDict, Long>
		implements DataDictDao {

	

	public List getDataDictByParams(String tablename, String columnname) {
		 String hql = "from DataDict t where t.tableName like ? and t.columnName like ?";
	     Object[] params = {"%"+tablename+"%","%"+columnname+"%"};
	     List dataDictList = getHibernateTemplate().find(hql,params);
	     return dataDictList;

	}

}
