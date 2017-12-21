package com.cernet.dao;

import java.util.List;

import com.cernet.model.DataDict;

public interface DataDictDao extends GenericDao<DataDict, Long> {

	public List getDataDictByParams(String tablename,String columnname);
	
	
	
}
