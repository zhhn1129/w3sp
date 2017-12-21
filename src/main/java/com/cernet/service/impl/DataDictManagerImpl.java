package com.cernet.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cernet.dao.DataDictDao;
import com.cernet.dao.WebLeakDao;
import com.cernet.model.DataDict;
import com.cernet.service.DataDictManager;

public class DataDictManagerImpl extends GenericManagerImpl<DataDict, Long>
		implements DataDictManager {

    private DataDictDao dataDictDao;
	
	public DataDictManagerImpl(DataDictDao dataDictDao) {
		super(dataDictDao);
		this.dataDictDao = dataDictDao;
	}

	public List getDataDictByParams(String tableName, String columnname
			) {
		return dataDictDao.getDataDictByParams(tableName, columnname);
		 
	}

}
