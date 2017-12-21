package com.cernet.service;

import java.util.Date;

import com.cernet.model.webleakHandleLast1View;


public interface HandleLastManager extends GenericManager<webleakHandleLast1View, String> {

	public Date getLastFriday();
}
