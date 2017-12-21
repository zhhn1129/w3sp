/**
 * 
 */
package com.cernet.spider.rule;

/**
 * Created by Eclipse.
 * 
 * @author Gump
 * @date 2013-10-31
 */
public class ArticleListRule extends Rule {
	private String includedStrOfURL;
	private String excludedStrOfURL;

	public String getIncludedStrOfURL() {
		return includedStrOfURL;
	}

	public void setIncludedStrOfURL(String includedStrOfURL) {
		this.includedStrOfURL = includedStrOfURL;
	}

	public String getExcludedStrOfURL() {
		return excludedStrOfURL;
	}

	public void setExcludedStrOfURL(String excludedStrOfURL) {
		this.excludedStrOfURL = excludedStrOfURL;
	}

}
