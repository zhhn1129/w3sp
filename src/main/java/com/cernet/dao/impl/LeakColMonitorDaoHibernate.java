package com.cernet.dao.impl;

import com.cernet.dao.LeakColMonitorDao;
import com.cernet.model.LeakColMonitor;

public class LeakColMonitorDaoHibernate extends GenericDaoHibernate<LeakColMonitor, String> implements LeakColMonitorDao{
  public LeakColMonitorDaoHibernate(){
	  super(LeakColMonitor.class);
  }


}
