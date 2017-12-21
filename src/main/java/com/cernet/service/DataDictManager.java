package com.cernet.service;

import java.util.List;

import com.cernet.model.DataDict;

public interface DataDictManager extends GenericManager<DataDict, Long> {

	
	public List getDataDictByParams(String tableName,String columnname);
	
}
