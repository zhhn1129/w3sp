package com.cernet.service.impl;


import com.cernet.dao.LoginDao;
import com.cernet.model.UserInfo;
import com.cernet.service.LoginManager;


public class LoginManagerImpl extends GenericManagerImpl<UserInfo, String> implements
		LoginManager {
	private LoginDao loginDao;
	public LoginManagerImpl(LoginDao loginDao) {
		super(loginDao);
		this.loginDao = loginDao;
	}


}
