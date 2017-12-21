package com.cernet.dao.impl;



import com.cernet.dao.LoginDao;
import com.cernet.model.UserInfo;


public class LoginDaoHibernate extends GenericDaoHibernate<UserInfo, String> implements
		LoginDao {

	public LoginDaoHibernate() {
		super(UserInfo.class);
	}


}
