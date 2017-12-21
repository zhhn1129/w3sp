package com.cernet.dao.impl;

import com.cernet.dao.HighPushDao;
import com.cernet.model.HighPush;

public class HighPushDaoHibernate extends GenericDaoHibernate<HighPush, String> implements HighPushDao{
  public HighPushDaoHibernate(){
	  super(HighPush.class);
  }


}
