/**
 * 
 */
package com.cernet.spider;

import java.util.List;

import com.cernet.spider.rule.ArticleListRule;
import com.cernet.spider.rule.ArticleViewRule;

/**
 * Created by Eclipse.
 * 
 * @author Gump
 * @date 2013-10-31
 */
public class Task {
	/**
	 * columnId：可对应数据库已定义的栏目Id columnURL：栏目的URL desc: 栏目简要描述 encoding:
	 * 页面编码（字符串处理时需要字符编码） delayTime:延时采集时间（如果>0,则该栏目下每采集一篇文章后做延时操作）
	 */
	private int columnId;
	private String columnURL;
	private String currPageURL;
	private String desc;
	private String encoding;
	private int delayTime;
	private int startPageIndex;
	private int endPageIndex;
	private String pageModel;
	private List<String[]> articleUrls;
	private ArticleListRule articleListRule;
	private ArticleViewRule articleViewRule;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public String getColumnURL() {
		return columnURL;
	}

	public void setColumnURL(String columnURL) {
		this.columnURL = columnURL;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

 
	public List<String[]> getArticleUrls() {
		return articleUrls;
	}

	public void setArticleUrls(List<String[]> articleUrls) {
		this.articleUrls = articleUrls;
	}

	public ArticleListRule getArticleListRule() {
		return articleListRule;
	}

	public void setArticleListRule(ArticleListRule articleListRule) {
		this.articleListRule = articleListRule;
	}

	public ArticleViewRule getArticleViewRule() {
		return articleViewRule;
	}

	public void setArticleViewRule(ArticleViewRule articleViewRule) {
		this.articleViewRule = articleViewRule;
	}

	
	public String getCurrPageURL() {
		return currPageURL;
	}

	public void setCurrPageURL(String currPageURL) {
		this.currPageURL = currPageURL;
	}

	public int getStartPageIndex() {
		return startPageIndex;
	}

	public void setStartPageIndex(int startPageIndex) {
		this.startPageIndex = startPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public String getPageModel() {
		return pageModel;
	}

	public void setPageModel(String pageModel) {
		this.pageModel = pageModel;
	}

}
