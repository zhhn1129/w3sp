package com.cernet.dao.impl;

import com.cernet.dao.WebshellDao;
import com.cernet.model.Webshell;

public class WebshellDaoHibernate extends GenericDaoHibernate<Webshell, Long> 
	implements WebshellDao{
	public WebshellDaoHibernate() {
		super(Webshell.class);
	}

}
