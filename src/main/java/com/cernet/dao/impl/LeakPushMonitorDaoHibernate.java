package com.cernet.dao.impl;

import com.cernet.dao.LeakPushMonitorDao;
import com.cernet.model.LeakPushMonitor;

public class LeakPushMonitorDaoHibernate extends GenericDaoHibernate<LeakPushMonitor, String> implements LeakPushMonitorDao{
  public LeakPushMonitorDaoHibernate(){
	  super(LeakPushMonitor.class);
  }


}
