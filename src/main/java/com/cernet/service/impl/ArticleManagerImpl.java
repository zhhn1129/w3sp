package com.cernet.service.impl;


import com.cernet.dao.ArticleDao;
import com.cernet.model.Article;
import com.cernet.service.ArticleManager;


public class ArticleManagerImpl extends GenericManagerImpl<Article, String> implements
ArticleManager {

	private ArticleDao articleDao;
	public ArticleManagerImpl(ArticleDao articleDao) {
		super(articleDao);
		this.articleDao = articleDao;
	}

}
