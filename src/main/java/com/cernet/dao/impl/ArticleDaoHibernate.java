package com.cernet.dao.impl;

import com.cernet.dao.ArticleDao;
import com.cernet.model.Article;


public class ArticleDaoHibernate extends GenericDaoHibernate<Article, String> implements
ArticleDao {

	public ArticleDaoHibernate() {
		super(Article.class);
	}

}
