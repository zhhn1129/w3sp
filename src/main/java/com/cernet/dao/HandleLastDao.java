package com.cernet.dao;

import java.util.Date;

import com.cernet.model.webleakHandleLast1View;


public interface HandleLastDao extends GenericDao<webleakHandleLast1View, String> {

	
	public Date getLastFriday();

}
